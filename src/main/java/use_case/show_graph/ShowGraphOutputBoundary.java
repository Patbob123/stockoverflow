package use_case.show_graph;

import use_case.OutputBoundary;

public interface ShowGraphOutputBoundary extends OutputBoundary {
    void prepareSuccessView(ShowGraphOutputData outputData);
    void prepareFailView(String error);
}