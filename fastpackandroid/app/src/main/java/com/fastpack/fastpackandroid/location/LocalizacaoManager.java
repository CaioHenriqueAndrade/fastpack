package com.fastpack.fastpackandroid.location;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.fastpack.fastpackandroid.factory.IntentFactory;
import com.fastpack.fastpackandroid.permission.Permicao;
import com.fastpack.fastpackandroid.utils.UtilsDialog;

import static android.content.Context.LOCATION_SERVICE;

public class LocalizacaoManager implements LocationListener, DialogInterface.OnClickListener {

    private final ListenerLocation listenerLocation;

    private LocationManager locationManager;
    private int alertDialogOpenned;
    private UtilsDialog utilsDialog;
    public static final int ALERT_DIALOG_PERMISSION = 1;
    public static final int ALERT_DIALOG_LOCATION = 2;

    private volatile boolean isRequisitandoNow = false;

    public LocalizacaoManager(@NonNull ListenerLocation listenerLocation) {
        this.listenerLocation = listenerLocation;
    }


    public void searchLocation() {

        try {
            //a primeira execucao deve ser conferir se o gps dele esta ligado...

            /*
             * Ativando o local se está desligado...
             * Só funcionará em algumas apis antigas...
             */
            try {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Intent intent = new Intent();
                    intent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                    intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
                    intent.setData(Uri.parse("3"));
                    getActivity().sendBroadcast(intent);
                }
            } catch (Exception e) {

            }

            if (isPossibleBuscarLocalizacao()) {
                notifyStartRequisicao();

                listenerLocation.onStartSearchLocation();

                locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);


                String locationProvider = LocationManager.NETWORK_PROVIDER;

                Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

                if (lastKnownLocation == null) {
                    try {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    } catch (Exception e) {
                    }
                } else {

                    onLocationChanged(lastKnownLocation);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean isPossibleBuscarLocalizacao() {
        //primeiro de tudo veremos se ele ja nao exista requisitando
        if (!isPossibleRequisitar()) return false;


        //se o gps não esta ativado ou ele não aceitou a localização:
        //Verificamos se o gps está desativado:

        LocationManager service = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            //se desativado, abrimos as configurações:

            String messageDialog = "Precisamos que ative sua localização.";
            getUtilsDialog().dialogBasic(messageDialog, "Ativar", "Cancelar", this);
            setAlertDialogOpenned(ALERT_DIALOG_LOCATION);
            return false;
        } else {
            //se não, não temos a permissão... Abrimos a configuração para ele
            if (Build.VERSION.SDK_INT >= 23 && !Permicao.isPermitedLocation(getActivity())) {
                String message = "Precisamos da sua permissão para recuperar a localização, ative-a.";
                getUtilsDialog().dialogBasic(message, "Ok", "Voltar", this);
                setAlertDialogOpenned(ALERT_DIALOG_PERMISSION);
                return false;
            }
        }
        return true;
    }


    public Activity getActivity() {
        return listenerLocation.getActivity();
    }

    @Override
    public void onLocationChanged(Location location) {
        //quando recebeu a localizacao

        listenerLocation.onLocationReceiv(location);
        notifyFinishRequisicao();
    }

    private void closeSearch() {
        try {

            if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Intent intent = new Intent();
                intent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
                intent.setData(Uri.parse("3"));
                getActivity().sendBroadcast(intent);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        //quando clicado no dialog
        dialog.dismiss();

        if (clickFoiEmLocation()) {
            IntentFactory.startActivityLocationSettings(getActivity());
        } else if (clickFoiEmPermissions()) {
            IntentFactory.startActivityPermissionsConfig(getActivity());
        } else
            throw new IllegalArgumentException("IllegalArgument not implemented");
    }

    private boolean clickFoiEmPermissions() {
        return getAlertDialogOpenned() == ALERT_DIALOG_PERMISSION;
    }

    private boolean clickFoiEmLocation() {
        return getAlertDialogOpenned() == ALERT_DIALOG_LOCATION;
    }


    public int getAlertDialogOpenned() {
        return alertDialogOpenned;
    }

    public void setAlertDialogOpenned(int alertDialogOpenned) {
        this.alertDialogOpenned = alertDialogOpenned;
    }

    public UtilsDialog getUtilsDialog() {
        return utilsDialog == null ? utilsDialog = new UtilsDialog(getActivity()) : utilsDialog;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void dismissDialogs() {
        if (utilsDialog != null)
            getUtilsDialog().dismiss();
    }

    public interface ListenerLocation {
        void onLocationReceiv(Location location);

        Activity getActivity();

        void onStartSearchLocation();
    }

    public boolean isPossibleRequisitar() {
        return !isRequisitandoNow;
    }

    public synchronized void notifyFinishRequisicao() {
        isRequisitandoNow = false;
        closeSearch();
    }

    public synchronized void notifyStartRequisicao() {
        isRequisitandoNow = true;
    }
}


