package com.example.mark_m.pechhulprsr.mapsActivity;
/**
 * definieert het contract tussen the view{@link MapsActivity} and the
 * Presenter {@link MapsActivityPresenter}.
 */
public interface MapsActivityContract {

    interface MapsActivityView{
        void displayMainIntent();
    }
    interface Presenter{
        void clickedBackArrow();

    }
}
