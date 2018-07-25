package com.example.mark_m.pechhulprsr.mapsActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.mark_m.pechhulprsr.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * return een custom infowindow
 */
public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context){

        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void setTextInfoWindow(Marker marker, View view){
        String title = marker.getTitle();
        TextView tvTitle = view.findViewById(R.id.title);

        if(!title.equals("")){
            tvTitle.setText(title);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        setTextInfoWindow(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return mWindow;
    }
}
