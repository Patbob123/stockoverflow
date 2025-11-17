package interface_adapter.singlestock;

import use_case.singlestock.AnalyzeSingleStockInputBoundary;
import use_case.singlestock.AnalyzeSingleStockInputData;

public class SingleStockController {

    private final AnalyzeSingleStockInputBoundary interactor;

    public SingleStockController(AnalyzeSingleStockInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void analyze(String ticker, double riskFreeAnnual) {
        AnalyzeSingleStockInputData input =
                new AnalyzeSingleStockInputData(ticker, riskFreeAnnual);
        interactor.execute(input);
    }
}
