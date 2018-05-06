package com.fastpack.fastpackandroid.layout;

import android.content.DialogInterface;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.fastpack.fastpackandroid.R;
import com.fastpack.fastpackandroid.dialog.OnDialogAddressZipCod;
import com.fastpack.fastpackandroid.dialog.OnDialogMap;
import com.fastpack.fastpackandroid.geocouding.ListenerGeocoding;
import com.fastpack.fastpackandroid.geocouding.PresenterGeoCouding;
import com.fastpack.fastpackandroid.interfaces.Interfaces;
import com.fastpack.fastpackandroid.interfaces.VivaCepListener;
import com.fastpack.fastpackandroid.location.LocalizacaoManager;
import com.fastpack.fastpackandroid.objetos.Address;
import com.fastpack.fastpackandroid.objetos.Local;
import com.fastpack.fastpackandroid.utils.UtilsAction;
import com.fastpack.fastpackandroid.utils.UtilsDialog;
import com.fastpack.fastpackandroid.utils.UtilsProgressDialog;
import com.google.maps.model.LatLng;

import java.util.List;

public class LayoutAddress extends LayoutBasic implements View.OnClickListener, UtilsDialog.OnClickDialog, ListenerGeocoding, LocalizacaoManager.ListenerLocation, Interfaces.MethodsOnDialogMap, VivaCepListener {
    TextView txtEstado;
    EditText edtRua, edtCity, edtBairro, edtNumero, edtCep, edtComplementary;
    ViewGroup containerLocalizacao, containerProxAddress;
    Toolbar toolbar;

    private UtilsDialog utilsDialog;
    private ArrayAdapter<String> arrayAdapterCategorias;
    private String[] listaEstados;
    private LocalizacaoManager location;
    private PresenterGeoCouding geoCouding;
    private Address address;
    private UtilsProgressDialog utilsProgressDialog;
    private OnDialogMap onDialogMap;
    private Local local;
    private boolean isNeedLocal = false;

    private boolean isRequieriedLatLng = false;

    private boolean isRequieriedDialogMaps = false;

    public LayoutAddress(Interfaces.ActivityGetter ac ) {
        super( ac );
    }


    @Override
    public void recuperarReferencias(View v) {

        toolbar = v.findViewById(R.id.toolbar_address);
        edtRua = v.findViewById(R.id.layout_address_rua);
        edtCity = v.findViewById(R.id.layout_address_cidade);
        edtBairro = v.findViewById(R.id.layout_address_bairro);
        edtNumero = v.findViewById(R.id.layout_address_numero);
        edtCep = v.findViewById(R.id.layout_address_cep);
        txtEstado = v.findViewById(R.id.layout_address_estado);
        containerLocalizacao = v.findViewById(R.id.containerLocalizacao);
        containerProxAddress = v.findViewById(R.id.containerNextAddress);
        edtComplementary = v.findViewById(R.id.layout_address_complemento);
    }

    @Override
    public void onClick(View v) {

        //se o layout nao esta enablado para modifica-lo
        if (!edtRua.isEnabled()) return;


        if (v.getId() == txtEstado.getId()) {
            getUtilsDialog().dialogListBasic( getString(R.string.selecioneseuestado), getArrayAdapterCategorias(), LayoutAddress.this);
        } else if (v.getId() == containerLocalizacao.getId()) {
            initSearchLocalizacao();
        } else if (v.getId() == containerProxAddress.getId()) {
            searchAddressByZipCode();
        }
    }

    private void searchAddressByZipCode() {
        new OnDialogAddressZipCod( getActivity() , this  ).execute();
    }



    public void setEnabled(boolean enabled) {
        edtRua.setEnabled(enabled);
        edtCity.setEnabled(enabled);
        edtBairro.setEnabled(enabled);
        edtNumero.setEnabled(enabled);
        edtCep.setEnabled(enabled);
        edtComplementary.setEnabled(enabled);

    }

    private void limparCampos() {
        edtRua.setText(null);
        edtCity.setText(null);
        edtBairro.setText(null);
        edtNumero.setText(null);
        edtCep.setText(null);
        txtEstado.setText(R.string.toqueparaalterar);
        edtComplementary.setText(null);
    }

    private Runnable runnableTimeExceptionRequisicaoLocal = new Runnable() {
        @Override
        public void run() {
            try {
                int contador = 0;
                while (isNeedLocal || isRequieriedDialogMaps) {
                    contador++;
                    Thread.sleep(500);
                    if (contador == 8) {
                        onPostRequieriedLocal();
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };

    private void onPostRequieriedLocal() {
        if (getActivity() == null) return;


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (getUtilsProgressDialog().isShow()) {
                    getUtilsProgressDialog().dismiss();
                    makeText("Não conseguimos obter seu local");
                }
            }
        };

        getActivity().runOnUiThread(runnable);
    }

    private void initSearchLocalizacao() {
        isNeedLocal = true;
        getLocationManager().searchLocation();
    }

    @Override
    public void bindViewHolder() {
        if( address == null  ) {
            return;
        }
        edtRua.setText(address.getStreet());
        edtCity.setText(address.getCity());
        edtBairro.setText(address.getNeighborhood());
        edtNumero.setText(address.getStreetNumber());
        edtCep.setText(address.getZipcode());

        if (address.getState() != null && !address.getState().equals(""))
            txtEstado.setText(address.getState());

        edtComplementary.setText(address.getComplementary());

    }

    public void layoutTooObject(Address address) {
        address.setStreet(edtRua.getText().toString());
        address.setCity(edtCity.getText().toString());
        address.setNeighborhood(edtBairro.getText().toString());
        address.setStreetNumber(edtNumero.getText().toString());
        address.setZipcode(edtCep.getText().toString());
        address.setState(txtEstado.getText().toString());
        address.setComplementary(edtComplementary.getText().toString());
        address.setCountry("br");
        if (local != null)
            address.setLocal( getLocal() );
    }

    @Override
    public void setOnClick() {
        containerLocalizacao.setOnClickListener(this);
        containerProxAddress.setOnClickListener(this);

        txtEstado.setOnClickListener(this);
    }


    public UtilsDialog getUtilsDialog() {
        return utilsDialog == null ? utilsDialog = new UtilsDialog(this.getActivity()) : utilsDialog;
    }

    public ArrayAdapter<String> getArrayAdapterCategorias() {

        if (arrayAdapterCategorias != null) return arrayAdapterCategorias;

        arrayAdapterCategorias = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1);

        for (String listaCategoria : getListaEstados()) {
            arrayAdapterCategorias.add(listaCategoria);
        }

        return arrayAdapterCategorias;
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id, AlertDialog alertDialog) {
        txtEstado.setText(getListaEstados()[position]);
        alertDialog.dismiss();
    }

    public String[] getListaEstados() {
        if (listaEstados == null)
            listaEstados = getResources().getStringArray(R.array.estados);

        return listaEstados;
    }

    @Override
    @WorkerThread
    public void onFinishProcess(@Nullable final Address addressCoding) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isRequieriedLatLng) {
                    //esta querendo a latlng apenas
                    isRequieriedLatLng = false;
                    getUtilsProgressDialog().dismiss();
                    if (addressCoding == null) {
                        openDialogWhenNotExistsLatLngConfirmed();
                    } else
                        openOnDialogMap(addressCoding);

                } else {
                    //esta requerendo endereco

                    if (addressCoding == null) {
                        makeText("Não encontramos seu endereço.");
                    } else {
                        openDialogPostAddressEncontrado( addressCoding );
                    }


                    if (!UtilsProgressDialogIsNull())
                        getUtilsProgressDialog().dismiss();
                }
            }
        };
        getActivity().runOnUiThread(runnable);
    }

    private void openDialogPostAddressEncontrado(final Address addressCoding) {
        getUtilsDialog().dialogBasic("Sua localidade é: " + addressCoding.getNeighborhood() + ", " + addressCoding.getCity() + " ?", "Confirmar", "Negar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setAddress( addressCoding );
                bindViewHolder(  );
                edtRua.setText( null );
                edtCep.setText( null );
                dialog.dismiss();

                getUtilsDialog().dialogBasic("Sua rua é " + addressCoding.getStreet() + " ?", "Confirmar", "Negar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        edtRua.setText( addressCoding.getStreet()  );
                        edtCep.setText( addressCoding.getZipcode() );
                        UtilsAction.showKeyboard(edtNumero, getActivity());
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        UtilsAction.showKeyboard( edtRua , getActivity() );
                    }
                });
            }
        });
    }

    private boolean UtilsProgressDialogIsNull() {
        return utilsProgressDialog == null;
    }

    public void onPermissionResultGranted() {
        getLocationManager().dismissDialogs();
        getLocationManager().searchLocation();
    }

    public LocalizacaoManager getLocationManager() {
        return location == null ? location = new LocalizacaoManager(this) : location;
    }

    //callback de quando a localizacao e recebida, vamos procura na google maps pelo endereco
    @Override
    public void onLocationReceiv(Location location) {
        //quando a localizacao e recebida

        //trocando para executar antes das threads e elas saberem da mudanca
        //se estava true antes...

        //notifica que recebmos uma nova localizacao...
        notifyLatLngLocationReceiv(location);

        if (isRequieriedDialogMaps) {
            //se a requisicao esta vindo do dialog apos dialogmaps
            //fechamos o dialogprogress
            getUtilsProgressDialog().dismiss();
            isRequieriedDialogMaps = false;
        } else if (isNeedLocal) {
            //se nao do gps mesmo quando clicou na tela...
            isNeedLocal = false;
            getGeoCouding().initSearch(new LatLng(location.getLatitude(), location.getLongitude()));
        } else {
            //se nao,  so desativar o local...
            getLocationManager().notifyFinishRequisicao();
        }


    }

    //quando recebemos de alguma forma o local dele...
    private void notifyLatLngLocationReceiv(Location location) {
        //quando ele precisa so da latitude e longitude e a gente a recebe...
        getLocal().setLocation( location );
    }

    //chamado quando todas as dependencias
    // para procurar uma localizacoa foram cumpridas e ele vai comecar a procurar
    //pelo endereco geocoding
    @Override
    public void onStartSearchLocation() {
        getUtilsProgressDialog().show();
        new Thread(runnableTimeExceptionRequisicaoLocal).start();
    }


    public PresenterGeoCouding getGeoCouding() {
        return geoCouding == null ? geoCouding = new PresenterGeoCouding(this) : geoCouding;
    }

    public UtilsProgressDialog getUtilsProgressDialog() {
        return utilsProgressDialog == null ? utilsProgressDialog = new UtilsProgressDialog(getActivity(), "Carregando Localização") : utilsProgressDialog;
    }

    public Address getAddress() {
        return address == null ? address = new Address() : address;
    }

    //so pode ser chamado quando o address ja estiver validado...
    private void searchLatLngByAddress(Address address) {
        isRequieriedLatLng = true;
        getUtilsProgressDialog().show();
        getGeoCouding().initSearch(address);
    }

    OnDialogMap getOnDialogMap(Address address) {
        if (onDialogMap == null) {
            onDialogMap = new OnDialogMap(getActivity(), this, address);
        }
        return onDialogMap;
    }


    //quando clicado no dialog positive no dialog do mapa
    @Override
    public void onPositiveButton(Address addressSelected) {
        isRequieriedDialogMaps = false;
        //vamos pegar a localizacoa confirmada pelo dialogmapa
        getAddress().setLocal(addressSelected.getLocal());
    }

    //quando clicado em cancelar no Dialog requieried gps location
    public void onRequieriedGpsLocation() {
        getLocationManager().searchLocation();
    }

    //quando ele fehcar o dialog
    @Override
    public void onCloseDialog() {
        if (isRequieriedDialogMaps) {
            openDialogWhenNotExistsLatLngConfirmed();
        }
    }

    //valida tudo inclusive latitude e longitude
    //return true if tiver tudo validado...
    //se nao, aconteceu algum processamento aqui mesmo
    public boolean validate(Address address) {
        if (address == null)
            address = new Address();


        layoutTooObject(address);

        String msg = address.validate(getResources());

        if (msg != null) {
            makeText(msg);
            return false;
        }

        if (!address.existsLocal()) {
            //vamos procurar pela localizacao na geocoding e se acharmos vamos abrir o mapa
            //se nao acharmos abriremos o dialog para ele tentar via gps...
            searchLatLngByAddress(address);
            return false;
        }

        return true;
    }

    public void setAddress(Address ad) {
        this.address = ad;
    }

    private void openOnDialogMap(Address address) {
        isRequieriedDialogMaps = true;
        local = address.getLocal();
        getOnDialogMap(address).execute();
    }

    //chamado toda vez que nao existe latlng
    //depoois de sair do dialogMaps de confirmacao
    private void openDialogWhenNotExistsLatLngConfirmed() {
        getUtilsDialog().dialogBasic("Deseja que procuremos sua localização pelo GPS ?", "Procurar", "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //quando clicado em positive button
                onRequieriedGpsLocation();

            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public Local getLocal() {
        return local == null ? local = new Local() : local;
    }

    //quando a procura pelo cep e finalizada
    @Override
    public void whenSearchFinished(@org.jetbrains.annotations.Nullable Address address) {
        if( address == null ) {
            makeText("Não conseguimos obter seu endereço.");
            UtilsAction.showKeyboard( edtRua , getActivity() );
        } else {
            makeText("Endereço obtido com sucesso!");
            setAddress( address );
            bindViewHolder();
            local = null;
            UtilsAction.showKeyboard(edtNumero, getActivity());
        }
    }
}

