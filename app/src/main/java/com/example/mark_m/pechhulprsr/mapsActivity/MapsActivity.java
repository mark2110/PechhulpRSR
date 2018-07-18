package com.example.mark_m.pechhulprsr.mapsActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.example.mark_m.pechhulprsr.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker mMarker;
    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        }
        // controleert of de network provider is ingeschakeld
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // get the latitude
                    double latitude = location.getLatitude();
                    // get the longitude
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String currentAddress = addressList.get(0).getAddressLine(0);
                        mMarker = mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(currentAddress)
                                .icon(BitmapDescriptorFactory
                                        .fromResource(R.drawable.map_marker_mini)));


                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.2f));
                        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
                        mMarker.showInfoWindow();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
        } else if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1000, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // get the latitude
                    double latitude = location.getLatitude();
                    // get the longitude
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String currentAddress = addressList.get(0).getAddressLine(0);
                        mMarker = mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(currentAddress)
                                .icon(BitmapDescriptorFactory
                                        .fromResource(R.drawable.map_marker_mini)));


                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.2f));
                        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
                        mMarker.showInfoWindow();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
            });
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//      zet default locatie op Nederland
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.092876, 5.104480), 7.0f));


    }

}
