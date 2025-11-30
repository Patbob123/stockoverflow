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

import javax.swing.*;

public class ShowGraphUseCaseFactory {

    private ShowGraphUseCaseFactory() {}

    // Factory method to create the Controller specifically
    public static ShowGraphController createShowGraphUseCase(
            ViewManagerModel viewManagerModel,
            ShowGraphViewModel showGraphViewModel,
            APIDataAccessInterface apiDataAccessObject) {

        try {
            ShowGraphOutputBoundary showGraphOutputBoundary = new ShowGraphPresenter(viewManagerModel, showGraphViewModel);

            ShowGraphInputBoundary showGraphInteractor = new ShowGraphInteractor(
                    apiDataAccessObject,
                    showGraphOutputBoundary
            );

            return new ShowGraphController(showGraphInteractor);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating Graph Use Case.");
            return null;
        }
    }

    // Optional: Factory method to create the View if needed directly
    public static ShowGraphView create(
            ViewManagerModel viewManagerModel,
            ShowGraphViewModel showGraphViewModel,
            APIDataAccessInterface apiDataAccessObject) {

        ShowGraphController controller = createShowGraphUseCase(viewManagerModel, showGraphViewModel, apiDataAccessObject);

        return new ShowGraphView(showGraphViewModel, controller, viewManagerModel);
    }
}