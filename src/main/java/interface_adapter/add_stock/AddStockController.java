package interface_adapter.add_stock;

import use_case.add_stock.AddStockInputBoundary;
import use_case.add_stock.AddStockInputData;

import java.util.List;

public class AddStockController {
    final AddStockInputBoundary interactor;

    public AddStockController(AddStockInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username, String portfolioName, List<String> tickers) {
        AddStockInputData inputData = new AddStockInputData(username, portfolioName, tickers);
        interactor.execute(inputData);
    }
}