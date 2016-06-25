package com.bradleybossard.androidprogramabdemo.Actividades;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.bradleybossard.androidprogramabdemo.AlgoritmoGenetico.Algorithm;
import com.bradleybossard.androidprogramabdemo.AlgoritmoGenetico.FitnessCalc;
import com.bradleybossard.androidprogramabdemo.AlgoritmoGenetico.Individual;
import com.bradleybossard.androidprogramabdemo.AlgoritmoGenetico.Population;
import com.bradleybossard.androidprogramabdemo.Modelo.DirectionFinder;
import com.bradleybossard.androidprogramabdemo.Modelo.DirectionFinderListener;
import com.bradleybossard.androidprogramabdemo.Modelo.GPSTracker;
import com.bradleybossard.androidprogramabdemo.Modelo.Route;
import com.bradleybossard.androidprogramabdemo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
    private LatLng c1 = new LatLng(-26.814533, -65.200552);
    private LatLng c2 = new LatLng(-26.816189, -65.190617);
    private LatLng c3 = new LatLng(-26.827220, -65.202093);
  /*  private LatLng c4 = new LatLng(-26.8260748, -65.20046969999999);
    private LatLng c5 = new LatLng(-26.8178911, -65.20537480000002);
    private LatLng c6 = new LatLng(-26.817766, -65.20524390000003);
    private LatLng c7 = new LatLng(-26.820211, -65.20849989999999);
    private LatLng c8 = new LatLng(-26.8155761, -65.19632230000002);
    private LatLng c9 = new LatLng(-26.8114052, -65.19524769999998);
    private LatLng c10 = new LatLng(-26.8158918, -65.176105);
    */
    private LatLng miPos;
    private String area;
    private FloatingActionButton btnBuscar;

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
        btnBuscar = (FloatingActionButton) findViewById(R.id.btnMapa);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String origen = miPos.toString().replace("lat/lng: ","").replace("(","").replace(")","");
                obtenerRutaconAG(origen);
            }
        });
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
/*
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
        */
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
    private void obtenerRutaconAG(String origen) {
        LatLng destino = obtenerPuntoMasCercano();
        if (destino.equals(c1)) {
            FitnessCalc.setSolution("00000000000111110000000001000000000100000000010000000001");
            //Creamos una población
            Individual mejor = generarPoblacion();
            obtenerRuta(origen,destino.toString().replace("lat/lng: ","").replace("(","").replace(")",""));
        } else if (destino.equals(c2)) {
            FitnessCalc.setSolution("00000000000000011111000001000000000100000000010000000001");
            //Creamos una población
            Individual mejor = generarPoblacion();
            obtenerRuta(origen,destino.toString().replace("lat/lng: ","").replace("(","").replace(")",""));
        } else if (destino.equals(c3)) {
            FitnessCalc.setSolution("00000000000000000000000000000000000000000000000000000010000000001000000000100000000010000000111");
            //Creamos una población
            Individual mejor = generarPoblacion();
            obtenerRuta(origen,destino.toString().replace("lat/lng: ","").replace("(","").replace(")",""));
        }
    }

    private LatLng obtenerPuntoMasCercano() {
        LatLng destino = null;
        float distancias[] = new float[3];
        Location origen = new Location("origen");
        origen.setLatitude(miPos.latitude);
        origen.setLongitude(miPos.longitude);
        Location locationA = new Location("puntocarga1");
        locationA.setLatitude(c1.latitude);
        locationA.setLongitude(c2.longitude);
        Location locationB = new Location("puntocarga2");
        locationB.setLatitude(c1.latitude);
        locationB.setLongitude(c2.longitude);
        Location locationC = new Location("puntocarga3");
        locationC.setLatitude(c1.latitude);
        locationC.setLongitude(c2.longitude);
        float distanceOA = origen.distanceTo(locationA);
        distancias[0]=distanceOA;
        float distanceOB = origen.distanceTo(locationB);
        distancias[1]=distanceOB;
        float distanceOC = origen.distanceTo(locationC);
        distancias[2]=distanceOC;
        float mayor = 0;
        int pos=0;
        for (int i=0;i<distancias.length;i++){
            if(distancias[i]>mayor){
                mayor=distancias[i];
                pos=i;
            }
        }
        switch (pos) {
            case 0:
                destino = new LatLng(c1.latitude,c1.longitude);
                break;
            case 1:
                destino = new LatLng(c1.latitude,c1.longitude);
                break;
            case 2:
                destino = new LatLng(c1.latitude,c1.longitude);
                break;
        }
        return destino;
    }

    private Individual generarPoblacion() {
        Population myPop = new Population(4, true);
        //Generamos y mutamos de acuerdo a la población inicial
        int generationCount = 0;
        System.out.println("Generando población..");
        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
            generationCount++;
            System.out.println("Generación n°: " + generationCount);
            myPop = Algorithm.evolvePopulation(myPop);
        }
        System.out.println("Solucion encontrada!!");
        System.out.println("El mejor individuo es de la generación n°: " + generationCount);
        System.out.println("Genes:");
        System.out.println(myPop.getFittest());
        return myPop.getFittest();
    }
}


