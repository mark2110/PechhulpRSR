package com.example.mark_m.pechhulprsr.infoActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mark_m.pechhulprsr.R;


public class InfoActivity extends AppCompatActivity implements InfoActivityContract.InfoActivityView {
    private InfoActivityPresenter mPresenter;
    Button mBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mPresenter = new InfoActivityPresenter(this);
        mBackArrow = findViewById(R.id.activity_maps_backarrow);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clickedBackArrow();
            }
        });
    }

    @Override
    public void displayMainIntent() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
