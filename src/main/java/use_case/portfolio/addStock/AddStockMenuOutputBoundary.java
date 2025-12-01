package use_case.portfolio.addStock;

import use_case.OutputBoundary;

public interface AddStockMenuOutputBoundary extends OutputBoundary {

    void prepareSuccessView(String portfolioName, String stockticker);

    void prepareGoBackView();

    void prepareFailureView(String message);
}
