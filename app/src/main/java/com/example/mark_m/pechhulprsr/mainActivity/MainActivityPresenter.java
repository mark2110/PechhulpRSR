package com.example.mark_m.pechhulprsr.mainActivity;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private final MainActivityContract.MainActivityView view;

    public MainActivityPresenter(MainActivityContract.MainActivityView view) {
        this.view = view;
    }

    @Override
    public void clickedInfoBtn() {
        view.displayInfoIntent();
    }
    @Override
    public void clickedPechhulpBtn(){
        view.displayPechhulpBtn();
    }
}


