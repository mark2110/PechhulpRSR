package com.example.mark_m.pechhulprsr.mainActivity;


/**
 * defines the contract between the view{@link MainActivity} and the
 * Presenter {@link MainActivityPresenter}.
 */
public interface MainActivityContract {
    interface MainActivityView {
        void displayInfoIntent();
        void displayPechhulpBtn();

}

    interface Presenter {
        void clickedInfoBtn();
        void clickedPechhulpBtn();
    }
}
