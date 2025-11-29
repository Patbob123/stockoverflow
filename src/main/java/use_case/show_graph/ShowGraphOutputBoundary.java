package use_case.show_graph;

public interface ShowGraphOutputBoundary {
    void prepareSuccessView(ShowGraphOutputData outputData);
    void prepareFailView(String error);
}