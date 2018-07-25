package com.example.mark_m.pechhulprsr.mapsActivity;

public class MapsActivityPresenter implements MapsActivityContract.Presenter {

    private final MapsActivityContract.MapsActivityView view;

    public MapsActivityPresenter(MapsActivityContract.MapsActivityView view) {
        this.view = view;
    }
    @Override
    public void clickedBackArrow(){
        view.displayMainIntent();
    }
}
