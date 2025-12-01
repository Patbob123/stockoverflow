package interface_adapter.show_graph;

import interface_adapter.ViewManagerModel;
import use_case.show_graph.ShowGraphOutputBoundary;
import use_case.show_graph.ShowGraphOutputData;

public class ShowGraphPresenter implements ShowGraphOutputBoundary {

    private final ShowGraphViewModel showGraphViewModel;
    private final ViewManagerModel viewManagerModel;

    public ShowGraphPresenter(ViewManagerModel viewManagerModel, ShowGraphViewModel showGraphViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.showGraphViewModel = showGraphViewModel;
    }

    @Override
    public void prepareSuccessView(ShowGraphOutputData outputData) {
        ShowGraphState state = showGraphViewModel.getState();
        state.setStockData(outputData.getStockData());

        state.setPreviousViewName(outputData.getPreviousViewName());

        if (outputData.getErrorMessage() != null) {
            state.setErrorMessage(outputData.getErrorMessage());
        } else {
            state.setErrorMessage(null);
        }

        showGraphViewModel.setState(state);
        showGraphViewModel.firePropertyChange();

//        viewManagerModel.setActiveView(showGraphViewModel.getViewName());
//        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        ShowGraphState state = showGraphViewModel.getState();
        state.setErrorMessage(error);
        showGraphViewModel.firePropertyChange();
    }
}