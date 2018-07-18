package com.example.mark_m.pechhulprsr.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.example.mark_m.pechhulprsr.R;
import com.example.mark_m.pechhulprsr.mainActivity.MainActivity;

/**
 * Laat de rsr splashscreen zien.
 */
public class SplashScreenActivity extends AppCompatActivity   {
    private static int SPLASH_TIME_OUT = 2000;

   //private SplashScreenPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
       // mPresenter = new SplashScreenPresenter(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
