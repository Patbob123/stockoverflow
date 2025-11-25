package use_case.refresh;

public interface RefreshDataOutputBoundary {
    void prepareSuccessView(String message, boolean hasNewData);
    void prepareFailureView(String errorMessage);
}
