package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.show_graph.ShowGraphController;
import interface_adapter.show_graph.ShowGraphPresenter;
import interface_adapter.show_graph.ShowGraphViewModel;
import use_case.APIDataAccessInterface;
import use_case.show_graph.ShowGraphInputBoundary;
import use_case.show_graph.ShowGraphInteractor;
import use_case.show_graph.ShowGraphOutputBoundary;
import view.ShowGraphView;

public class ShowGraphUseCaseFactory {
    private ShowGraphUseCaseFactory() {}

    public static ShowGraphView create(
            ViewManagerModel viewManagerModel,
            ShowGraphViewModel showGraphViewModel,
            APIDataAccessInterface apiDataAccessObject) {


        return new ShowGraphView(showGraphViewModel, viewManagerModel);
    }

    public static ShowGraphController createShowGraphUseCase(
            ViewManagerModel viewManagerModel,
            ShowGraphViewModel showGraphViewModel,
            APIDataAccessInterface apiDataAccessObject) {

        ShowGraphOutputBoundary outputBoundary = new ShowGraphPresenter(showGraphViewModel, viewManagerModel);
        ShowGraphInputBoundary interactor = new ShowGraphInteractor(apiDataAccessObject, outputBoundary);

        return new ShowGraphController(interactor, showGraphViewModel);
    }
}