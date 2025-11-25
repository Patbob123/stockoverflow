package interface_adapter.show_graph;

import use_case.show_graph.ShowGraphInputBoundary;
import use_case.show_graph.ShowGraphInputData;
import java.util.List;

public class ShowGraphController {
    final ShowGraphInputBoundary showGraphInteractor;
    final ShowGraphViewModel showGraphViewModel;

    public ShowGraphController(ShowGraphInputBoundary showGraphInteractor, ShowGraphViewModel showGraphViewModel) {
        this.showGraphInteractor = showGraphInteractor;
        this.showGraphViewModel = showGraphViewModel;
    }

    public void execute(List<String> tickers, String fromViewName) {
        // Update the state with the view name we are coming from
        ShowGraphState state = showGraphViewModel.getState();
        state.setPreviousViewName(fromViewName);
        showGraphViewModel.setState(state);

        // Execute use case
        ShowGraphInputData inputData = new ShowGraphInputData(tickers);
        showGraphInteractor.execute(inputData);
    }
}