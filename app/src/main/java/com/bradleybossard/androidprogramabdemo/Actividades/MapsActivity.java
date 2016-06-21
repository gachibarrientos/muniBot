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
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

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
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route1.endLocation, 14));

          /*      originMarkers.add(mMap.addMarker(new MarkerOptions()
                        .title(route1.startAddress)
                        .position(route1.startLocation)));
                destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                        .title(route1.endAddress)
                        .position(route1.endLocation)));
*/
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


