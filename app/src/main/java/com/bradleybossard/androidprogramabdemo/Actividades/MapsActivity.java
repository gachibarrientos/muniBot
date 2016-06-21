package com.bradleybossard.androidprogramabdemo.Actividades;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.bradleybossard.androidprogramabdemo.Modelo.DirectionFinder;
import com.bradleybossard.androidprogramabdemo.Modelo.DirectionFinderListener;
import com.bradleybossard.androidprogramabdemo.Modelo.GPSTracker;
import com.bradleybossard.androidprogramabdemo.Modelo.Route;
import com.bradleybossard.androidprogramabdemo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements DirectionFinderListener, GoogleMap.OnMarkerClickListener{

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    private GoogleMap mMap;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private LatLng c1 = new LatLng(-26.819435, -65.196931);
    private LatLng c2 = new LatLng(-26.822154, -65.198367);
    private LatLng c3 = new LatLng(-26.8214819, -65.1971434);
    private LatLng c4 = new LatLng(-26.8260748, -65.20046969999999);
    private LatLng c5 = new LatLng(-26.8178911, -65.20537480000002);
    private LatLng c6 = new LatLng(-26.817766, -65.20524390000003);
    private LatLng c7 = new LatLng(-26.820211, -65.20849989999999);
    private LatLng c8 = new LatLng(-26.8155761, -65.19632230000002);
    private LatLng c9 = new LatLng(-26.8114052, -65.19524769999998);
    private LatLng c10 = new LatLng(-26.8158918, -65.176105);
    private LatLng miPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_maps);
        mMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(this);
        cargarMarcadores();


        GPSTracker gpsTracker = new GPSTracker(MapsActivity.this);

        if (gpsTracker.canGetLocation()) {
            miPos = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            Log.i("Debug", "Mi posicion on create:" + miPos);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miPos, 15));
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    private void cargarMarcadores() {
        Marker m1 = mMap.addMarker(new MarkerOptions()
                .position(c1)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ciudadana)));
        m1.hideInfoWindow();

        Marker m2 =  mMap.addMarker(new MarkerOptions()
                .position(c2)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ciudadana)));
        m2.hideInfoWindow();
        Marker m3= mMap.addMarker(new MarkerOptions()
                .position(c3)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ciudadana)));
        m3.hideInfoWindow();

        Marker m4 =  mMap.addMarker(new MarkerOptions()
                .position(c4)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ciudadana)));
        m4.hideInfoWindow();
        Marker m5 = mMap.addMarker(new MarkerOptions()
                .position(c5)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ciudadana)));
        m5.hideInfoWindow();

        Marker m6 =  mMap.addMarker(new MarkerOptions()
                .position(c6)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ciudadana)));
        m6.hideInfoWindow();
        Marker m7 = mMap.addMarker(new MarkerOptions()
                .position(c7)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ciudadana)));
        m7.hideInfoWindow();

        Marker m8 =  mMap.addMarker(new MarkerOptions()
                .position(c8)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ciudadana)));
        m8.hideInfoWindow();
        Marker m9 =  mMap.addMarker(new MarkerOptions()
                .position(c9)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ciudadana)));
        m9.hideInfoWindow();
        Marker m10 =  mMap.addMarker(new MarkerOptions()
                .position(c10)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ciudadana)));
        m10.hideInfoWindow();
    }

    public void obtenerRuta(String origen, String destino){
    try {
    new DirectionFinder(this, origen, destino).execute();
                                                }
    catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Espera por favor..",
                "Buscando ruta mas corta..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }
        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> route) {
            progressDialog.dismiss();
            polylinePaths = new ArrayList<>();
            originMarkers = new ArrayList<>();
            destinationMarkers = new ArrayList<>();


        for (Route route1  : route) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route1.endLocation, 16));
                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color(Color.BLUE).
                        width(10);

                for (int i = 0; i < route1.points.size(); i++)
                    polylineOptions.add(route1.points.get(i));

                polylinePaths.add(mMap.addPolyline(polylineOptions));
            }
        }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String origen = miPos.toString().replace("lat/lng: ","").replace("(","").replace(")","");
        String destino = marker.getPosition().toString().replace("lat/lng: ","").replace("(","").replace(")","");
        System.out.println("Las coordenadas de origen:" + origen + " Las coordenadas de destino:"+destino);
        obtenerRuta(origen,destino);
        return false;
    }
}


