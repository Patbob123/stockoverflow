package interface_adapter.show_graph;

import interface_adapter.AbsController;
import use_case.show_graph.ShowGraphInputBoundary;
import use_case.show_graph.ShowGraphInputData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShowGraphController extends AbsController{
    private final ShowGraphInputBoundary showGraphInteractor;

    public ShowGraphController(ShowGraphInputBoundary showGraphInteractor) {
        this.showGraphInteractor = showGraphInteractor;
    }

    public void execute(String tickers, String previousViewName) {
        List<String> tickerList = Arrays.stream(tickers.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        ShowGraphInputData inputData = new ShowGraphInputData(tickerList, null, null, previousViewName);
        showGraphInteractor.execute(inputData);
    }


    public void execute(String tickers) {
        execute(tickers, "main menu");
    }
}