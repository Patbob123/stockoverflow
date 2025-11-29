package use_case.add_stock;

public interface AddStockOutputBoundary {
    void prepareSuccessView(AddStockOutputData outputData);
    void prepareFailView(String error);
}
