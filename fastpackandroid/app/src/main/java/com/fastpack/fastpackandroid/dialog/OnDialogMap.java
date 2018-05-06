package com.fastpack.fastpackandroid.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.fastpack.fastpackandroid.R;
import com.fastpack.fastpackandroid.interfaces.Interfaces;
import com.fastpack.fastpackandroid.maphelper.MapaBasic;
import com.fastpack.fastpackandroid.objetos.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class OnDialogMap extends OnDialogBasic implements Interfaces.CallBackMapa, View.OnClickListener {
    private MapaBasic mapaBasic;
    private MapView mapView;
    private View buttonConfirmar, buttonNegar;
    private Interfaces.MethodsOnDialogMap onDialogMap;
    private Address address;

    public OnDialogMap(Activity ac, @NonNull Interfaces.MethodsOnDialogMap onDialogMap, Address address) {
        super(ac);
        this.onDialogMap = onDialogMap;
        this.address = address;
    }

    @Override
    public void recuperarReferencias(View view) {
        super.recuperarReferencias(view);
        mapView = view.findViewById(R.id.mapView);
        buttonConfirmar = view.findViewById(R.id.positivebutton);
        buttonNegar = view.findViewById(R.id.negativebutton);
    }

    @Override
    public void setOnClickListeners() {
        buttonConfirmar.setOnClickListener( this );
        buttonNegar.setOnClickListener( this );
    }

    @Override
    public void setDadosInLayout() {
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        getMapaBasic().onCreateView( getActivity() , mapView , null );
    }

    @Override
    public View onCreateView() {
        return getLayoutInflater().inflate(R.layout.layout_mapa_fragment , null);
    }

    public MapaBasic getMapaBasic() {
        return mapaBasic == null ? mapaBasic = new MapaBasic(this) : mapaBasic;
    }

    @Override
    public void onMapaCompleted(GoogleMap googleMap, MapView mapView) {
        LatLng latLng = new LatLng( address.getLocal().getLatitude() , address.getLocal().getLongitude() );
        googleMap.addMarker( new MarkerOptions().position( latLng ) );
        CameraPosition cameraPosition = new CameraPosition.Builder().target( latLng ).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        googleMap.setTrafficEnabled( false );
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        onDialogMap.onCloseDialog();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == buttonNegar.getId()) {
            //disse que nao era ali que era sua localizacao
        } else if(v.getId() == buttonConfirmar.getId()){
            onDialogMap.onPositiveButton( address );
        }
        dismiss();

    }


}
