package com.example.mark_m.pechhulprsr.mainActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.example.mark_m.pechhulprsr.R;
import com.example.mark_m.pechhulprsr.infoActivity.InfoActivity;
import com.example.mark_m.pechhulprsr.mapsActivity.MapsActivity;

public class MainActivity extends AppCompatActivity implements MainActivityContract.MainActivityView {
    private MainActivityPresenter mPresenter;
    private int STORAGE_PERMISSION_CODE = 1;
    Toolbar mToolbar;
    ImageView mInfoImageBtn;
    Button mPechhulpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainActivityPresenter(this);
        mToolbar = findViewById(R.id.toolbar);
        mPechhulpBtn = findViewById(R.id.pechhulp_btn);
        mPechhulpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clickedPechhulpBtn();
            }
        });
        mInfoImageBtn = findViewById(R.id.info_btn);
        mInfoImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clickedInfoBtn();
            }
        });


    }

    @Override
    public void displayInfoIntent() {
        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * vraag eerst om toestemming voor locatie voordat hij naar {@link MapsActivity} navigeert
     */

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void displayPechhulpBtn() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, STORAGE_PERMISSION_CODE);
            return;
        }
        //check if GPS is turned on
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        assert locationManager != null;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        //show prompt for user to enable gps
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Wilt u uw Locatie aanzetten?").setPositiveButton("Ja", dialogClickListener)
                    .setNegativeButton("Nee", dialogClickListener).show();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                displayPechhulpBtn();
        }
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent enableGPSIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(enableGPSIntent);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //popup met informatie ofzo
                    finish();
                    break;
            }
        }
    };

}
