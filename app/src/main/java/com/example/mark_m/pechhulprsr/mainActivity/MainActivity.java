package com.example.mark_m.pechhulprsr.mainActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.mark_m.pechhulprsr.R;
import com.example.mark_m.pechhulprsr.infoActivity.InfoActivity;
import com.example.mark_m.pechhulprsr.mapsActivity.MapsActivity;

public class MainActivity extends AppCompatActivity implements MainActivityContract.MainActivityView {
    private MainActivityPresenter mPresenter;
    private int STORAGE_PERMISSION_CODE = 1;
    Toolbar mToolbar;
    ImageView mImageView;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainActivityPresenter(this);
        mToolbar = findViewById(R.id.toolbar);
        mButton = findViewById(R.id.pechhulp_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clickedPechhulpBtn();
            }
        });
        mImageView = findViewById(R.id.info_btn);
        mImageView.setOnClickListener(new View.OnClickListener() {
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
    @Override
    public void displayPechhulpBtn() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
            }, STORAGE_PERMISSION_CODE);
            return;
        }
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                displayPechhulpBtn();
            return;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
