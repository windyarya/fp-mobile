package com.itmobile.vabw.maps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.itmobile.vabw.R;
import com.itmobile.vabw.databinding.ActivityMapsBinding;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_code = 101;
    private double lat, lng;
    ImageButton hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hospital=findViewById(R.id.hospital);

        fusedLocationProviderClient=
                LocationServices.getFusedLocationProviderClient(this.getApplicationContext());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder stringBuilder= new StringBuilder
                        ("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location=" + lat + "," + lng);
                stringBuilder.append("&radius=3000");
                stringBuilder.append("&type=hospital");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=" +getResources().getString(R.string.google_maps_key));

                String url= stringBuilder.toString();
                Object dataFetch[]= new Object[2];
                dataFetch[0]=mMap;
                dataFetch[1]=url;

                FetchData fetchData=new FetchData();
                fetchData.execute(dataFetch);

                addMarkerRS();
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        getCurrentLocation();
//        LatLng medcen = new LatLng(-7.2904334, 112.79282309999999);
//        mMap.addMarker(new MarkerOptions().position(medcen).title("ITS Medical Center"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(medcen));
//
//        LatLng nala = new LatLng(-7.291169900000001, 112.79526059999999);
//        mMap.addMarker(new MarkerOptions().position(nala).title("RSGM Nala Husada"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(nala));
    }

    private void addMarkerRS() {
        LatLng medcen = new LatLng(-7.2904334, 112.79282309999999);
        mMap.addMarker(new MarkerOptions().position(medcen).title("ITS Medical Center"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(medcen));

        LatLng nala = new LatLng(-7.291169900000001, 112.79526059999999);
        mMap.addMarker(new MarkerOptions().position(nala).title("RSGM Nala Husada"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nala));

        LatLng unair = new LatLng(-7.2698779, 112.7848318);
        mMap.addMarker(new MarkerOptions().position(unair).title("RS Universitas Airlangga (Unair)"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(unair));

        LatLng haji = new LatLng(-7.2833905, 112.7795594);
        mMap.addMarker(new MarkerOptions().position(haji).title("RS Haji Surabaya"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(haji));

        LatLng manyar = new LatLng(-7.289895, 112.7633821);
        mMap.addMarker(new MarkerOptions().position(manyar).title("RS Manyar"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(manyar));

        LatLng drsutomo = new LatLng(-7.2682228, 112.7580061);
        mMap.addMarker(new MarkerOptions().position(drsutomo).title("RS Dr. SUtomo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(drsutomo));
    }

    private void getCurrentLocation()
    {
        if(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions
                    (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_code);
            return;

        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);
        LocationCallback locationCallback= new LocationCallback() {

            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {

                if(locationResult== null){
                    Toast.makeText(getApplicationContext(),"Current Location is null"
                            ,Toast.LENGTH_LONG).show();

                    return;
                }

                for(Location location:locationResult.getLocations()){

                    if(location!=null){
                        Toast.makeText(getApplicationContext(),"Current Location is="+ location.getLongitude()
                                ,Toast.LENGTH_LONG).show();
                    }
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates
                (locationRequest, locationCallback, null);

        Task<Location> task=fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location !=null){

                lat= location.getLatitude();
                lng= location.getLongitude();

                LatLng latLng=new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (Request_code){

            case Request_code:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }
        }
    }
}