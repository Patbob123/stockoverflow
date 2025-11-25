package interface_adapter.remove_stock;

import use_case.remove_stock.RemoveStockInputBoundary;
import use_case.remove_stock.RemoveStockInputData;

public class RemoveStockController {
    final RemoveStockInputBoundary interactor;

    public RemoveStockController(RemoveStockInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username, String portfolioName, String ticker) {
        RemoveStockInputData inputData = new RemoveStockInputData(username, portfolioName, ticker);
        interactor.execute(inputData);
    }
}