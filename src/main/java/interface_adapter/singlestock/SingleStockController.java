package interface_adapter.singlestock;

import use_case.singlestock.AnalyzeSingleStockInputBoundary;
import use_case.singlestock.AnalyzeSingleStockInputData;
import use_case.singlestock.CompareTwoStocksInputBoundary;
import use_case.singlestock.CompareTwoStocksInputData;

public class SingleStockController {

    private final AnalyzeSingleStockInputBoundary analyzeInteractor;
    private final CompareTwoStocksInputBoundary compareInteractor;

    public SingleStockController(AnalyzeSingleStockInputBoundary analyzeInteractor,
                                 CompareTwoStocksInputBoundary compareInteractor) {
        this.analyzeInteractor = analyzeInteractor;
        this.compareInteractor = compareInteractor;
    }

    public void analyze(String ticker, double rfAnnual) {
        AnalyzeSingleStockInputData input =
                new AnalyzeSingleStockInputData(ticker, rfAnnual);
        analyzeInteractor.execute(input);
    }

    public void compare(String ticker1, String ticker2, double rfAnnual) {
        CompareTwoStocksInputData input =
                new CompareTwoStocksInputData(ticker1, ticker2, rfAnnual);
        compareInteractor.execute(input);
    }
}
