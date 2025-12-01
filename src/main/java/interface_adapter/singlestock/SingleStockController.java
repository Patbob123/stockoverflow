package interface_adapter.singlestock;

import interface_adapter.AbsController;
import use_case.singlestock.AnalyzeSingleStockInputBoundary;
import use_case.singlestock.AnalyzeSingleStockInputData;
import use_case.singlestock.CompareTwoStocksInputBoundary;
import use_case.singlestock.CompareTwoStocksInputData;

public class SingleStockController extends AbsController {

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

    public void showGraph(String ticker){
        System.out.println("Graphed requested for:" + ticker);
    }

    //TODO do these if you worked on Monte carlo or scenario its for you to change
    public void runScenario(String ticker, double rfAnnual) {
        System.out.println("Scenario for " + ticker + " (rf=" + rfAnnual + ")");//just check for now
        //scenarioInputBoundary.execute(new ScenarioInputData(ticker, rfAnnual)); change it to something like this
    }

    public void runMonteCarlo(String ticker, double rfAnnual) {
        System.out.println("Monte Carlo for " + ticker + " (rf=" + rfAnnual + ")");//just check, delete it
        //monteCarloInputBoundary.execute(new MonteCarloInputData(ticker, rfAnnual)); change it to somethimg loike this , anything you need
    }

}
