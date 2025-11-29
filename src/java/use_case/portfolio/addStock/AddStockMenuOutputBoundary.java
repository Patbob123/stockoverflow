package use_case.portfolio.addStock;

public interface AddStockMenuOutputBoundary {

    void prepareSuccessView(String portfolioName, String stockticker);

    void prepareGoBackView();

    void prepareFailureView(String message);
}
