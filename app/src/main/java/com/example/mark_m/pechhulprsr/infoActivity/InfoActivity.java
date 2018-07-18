package com.example.mark_m.pechhulprsr.infoActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mark_m.pechhulprsr.R;
import com.example.mark_m.pechhulprsr.mainActivity.MainActivity;

public class InfoActivity extends AppCompatActivity implements InfoActivityContract.InfoActivityView {
    private InfoActivityPresenter mPresenter;
    LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mPresenter = new InfoActivityPresenter(this);

        mLinearLayout = findViewById(R.id.back_arrow);

        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clickedBackArrow();
            }
        });
    }

    @Override
    public void displayMainIntent() {
        Intent intent = new Intent(InfoActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
