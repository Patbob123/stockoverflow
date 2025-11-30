package use_case.search;

public interface SearchOutputBoundary {
    void prepareSuccessView(SearchOutputData searchOutputData);
    void prepareFailView(String error);
}