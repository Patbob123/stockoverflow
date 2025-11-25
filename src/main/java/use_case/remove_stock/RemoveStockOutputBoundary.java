package use_case.remove_stock;

public interface RemoveStockOutputBoundary {
    void prepareSuccessView(RemoveStockOutputData outputData);
    void prepareFailView(String error);
}
