package interface_adapter.show_graph;

import interface_adapter.ViewManagerModel;
import use_case.show_graph.ShowGraphOutputBoundary;
import use_case.show_graph.ShowGraphOutputData;

public class ShowGraphPresenter implements ShowGraphOutputBoundary {
    private final ShowGraphViewModel showGraphViewModel;
    private final ViewManagerModel viewManagerModel;

    public ShowGraphPresenter(ShowGraphViewModel showGraphViewModel, ViewManagerModel viewManagerModel) {
        this.showGraphViewModel = showGraphViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ShowGraphOutputData outputData) {
        ShowGraphState state = showGraphViewModel.getState();
        state.setStockData(outputData.getStockData());
        state.setErrorMessage(null);

        showGraphViewModel.setState(state);
        showGraphViewModel.firePropertyChanged();

        // Switch to the Graph View
        viewManagerModel.setActiveView(showGraphViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        ShowGraphState state = showGraphViewModel.getState();
        state.setErrorMessage(error);
        showGraphViewModel.firePropertyChanged();
    }
}
