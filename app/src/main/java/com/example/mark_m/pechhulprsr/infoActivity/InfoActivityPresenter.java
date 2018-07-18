package com.example.mark_m.pechhulprsr.infoActivity;

public class InfoActivityPresenter implements InfoActivityContract.Presenter {

    private final InfoActivityContract.InfoActivityView view;

    public InfoActivityPresenter(InfoActivityContract.InfoActivityView view){
        this.view = view;
    }

    @Override
    public void clickedBackArrow() {
        view.displayMainIntent();
    }
}
