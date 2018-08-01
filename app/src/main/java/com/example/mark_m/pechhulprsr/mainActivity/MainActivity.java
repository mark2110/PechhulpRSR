package com.example.mark_m.pechhulprsr.mainActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


import com.example.mark_m.pechhulprsr.R;
import com.example.mark_m.pechhulprsr.infoActivity.InfoActivity;
import com.example.mark_m.pechhulprsr.mapsActivity.MapsActivity;

/**
 * This activity is used for different loading layout resources and for handle gps permissions
 *
 * @author mark mooibroek
 */
public class MainActivity extends AppCompatActivity implements MainActivityContract.MainActivityView {
    private MainActivityPresenter mPresenter;
    Toolbar mToolbar;
    Button mPechhulpBtn, mInstellingen, mAnnuleren, mInfoBtn;
    Dialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainActivityPresenter(this);
        mToolbar = findViewById(R.id.activity_main_tb);
        mPechhulpBtn = findViewById(R.id.activity_main_btn_pechhulp);
        mPechhulpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clickedPechhulpBtn();
            }
        });
        mInfoBtn = findViewById(R.id.info_btn);
        mInfoBtn.setOnClickListener(new View.OnClickListener() {
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


    @Override
    public void displayPechhulpBtn() {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void showCustomMainDialog() {
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.dialog_main);
        mInstellingen = mDialog.findViewById(R.id.activity_main_btn_instellingen);
        mAnnuleren = mDialog.findViewById(R.id.activity_main_btn_annuleren);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.show();
        mInstellingen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enableGPSIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(enableGPSIntent);
                mDialog.dismiss();
            }
        });
        mAnnuleren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //check if gps is disabled and handle user permission
    @Override
    protected void onStart() {
        super.onStart();
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            showCustomMainDialog();
        }
    }
}
