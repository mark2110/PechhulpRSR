package com.example.mark_m.pechhulprsr.infoActivity;

/**
 * defines the contract between the view{@link InfoActivity} and the
 * Presenter {@link InfoActivityPresenter}.
 */
public interface InfoActivityContract {

    interface InfoActivityView{
        void displayMainIntent();
    }

    interface Presenter{
        void clickedBackArrow();
    }
}
