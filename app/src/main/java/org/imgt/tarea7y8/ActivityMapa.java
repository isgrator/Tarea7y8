package org.imgt.tarea7y8;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityMapa extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final int SOLICITUD_PERMISO_LOCALIZACION = 0;
    private LocationManager manejador;
    private Location mejorLocaliz;

    private GoogleMap mapa;



    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        manejador= (LocationManager) getSystemService(LOCATION_SERVICE);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(true);
            mapa.getUiSettings().setCompassEnabled(true);
            dameUltimaLocalizacion();
        }else{
            solicitarPermiso(Manifest.permission.ACCESS_FINE_LOCATION, "Se necesita permiso localización para mostrar su posición"
                    ,SOLICITUD_PERMISO_LOCALIZACION,this);
        }

    }

    private void dameUltimaLocalizacion() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            if(manejador.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {

                actualizaMejorLocaliz(manejador.getLastKnownLocation(LocationManager.GPS_PROVIDER));
            }
            if(manejador.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            {

                actualizaMejorLocaliz(manejador.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
            }
        }
        /*else
        {
            solicitarPermiso(Manifest.permission.ACCESS_FINE_LOCATION, "Se necesita permiso localización para mostrar su posición"
                    ,SOLICITUD_PERMISO_LOCALIZACION,this);
        }*/
    }

    private void actualizaMejorLocaliz(Location localiz) {

        if (localiz != null &&
                (mejorLocaliz == null
                        || localiz.getAccuracy() < 2 * mejorLocaliz.getAccuracy()
                )//|| localiz.getTime() - mejorLocaliz.getTime() > DOS_MINUTOS)
                ) {

            mejorLocaliz = localiz;
            centrarEnMiPos();
        }

    }

    public void centrarEnMiPos() {

        if(mapa!=null)
            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mejorLocaliz.getLatitude(), mejorLocaliz.getLongitude()), 15));
    }

    public static void solicitarPermiso(final String permiso, String
            justificacion, final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad,
                permiso)){
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }})
                    .show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);
        }
    }

    @Override public void onMapClick(LatLng puntoPulsado) {
        mapa.addMarker(new MarkerOptions().position(puntoPulsado)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
    }
}

