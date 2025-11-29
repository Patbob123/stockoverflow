package interface_adapter.change_view;

import interface_adapter.ViewManagerModel;
import use_case.change_view.ChangeViewOutputBoundary;
import use_case.change_view.ChangeViewOutputData;

public class ChangeViewPresenter implements ChangeViewOutputBoundary {

    private final ViewManagerModel viewManagerModel;

    public ChangeViewPresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareView(ChangeViewOutputData data) {
        viewManagerModel.setActiveView(data.getViewName());
        System.out.println("VIEW MODEL SET VIEW TO: " + data.getViewName());
    }
}
