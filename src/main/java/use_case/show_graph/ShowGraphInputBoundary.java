package use_case.show_graph;

import use_case.InputBoundary;

public interface ShowGraphInputBoundary extends InputBoundary {
    void execute(ShowGraphInputData showGraphInputData);
}