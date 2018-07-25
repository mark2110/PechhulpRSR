package com.example.mark_m.pechhulprsr.mapsActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mark_m.pechhulprsr.R;
import com.example.mark_m.pechhulprsr.mainActivity.MainActivity;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsActivityContract.MapsActivityView {
    private MapsActivityPresenter mPresenter;
    private int STORAGE_PERMISSION_CODE = 1;
    private String RSR_PHONE_NUMBER = "tel:+319007788990";
    private GoogleMap mMap;
    private Marker mMarker;
    private Button mBelRSRBtn;
    LinearLayout mCloseDialog, mBackArrow;
    LocationManager mLocationManager;
    Dialog mDialog;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mPresenter = new MapsActivityPresenter(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mDialog = new Dialog(this);
        mBackArrow = findViewById(R.id.back_arrow);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clickedBackArrow();
            }
        });
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        /**
         * controleert of de gps provider is ingeschakeld
         */
        assert mLocationManager != null;
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
                        mMap.getUiSettings().setMapToolbarEnabled(false);
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
        }
        /**
         * als gps niet is ingeschakeld, gaat hij over om de network provider.
         */
        if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && !mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
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
                        mMap.getUiSettings().setMapToolbarEnabled(false);
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
        // zet default locatie op Nederland
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.092876, 5.104480), 7.0f));


    }

    /**
     * show custom popup dialog when user clicked on the button "Bel RSR nu".
     * @param v
     */

    public void showPopup(View v) {

        mDialog.setContentView(R.layout.custom_popup);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().setGravity(Gravity.BOTTOM);
        mDialog.show();
        mDialog.setCanceledOnTouchOutside(false);
        mBelRSRBtn = findViewById(R.id.show_popup);
        mBelRSRBtn.setVisibility(View.GONE);
        mCloseDialog = mDialog.findViewById(R.id.close_dialog);
        mCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mBelRSRBtn.setVisibility(View.VISIBLE);
            }
        });

        // onCancelListener word afgevuurd als de user op de back button drukt.
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mBelRSRBtn.setVisibility(View.VISIBLE);
            }
        });

        Button bel_btn = mDialog.findViewById(R.id.call_btn);
        bel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();

            }

        });
    }

    /**
     * vraagt de gebruiker om toestemming voor het gebruik van de telefoon.
     * na toestemming word RSR gebeld.
     */
    private void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    STORAGE_PERMISSION_CODE);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse(RSR_PHONE_NUMBER)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                onCall();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void displayMainIntent() {
        onBackPressed();
    }
}






