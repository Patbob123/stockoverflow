package use_case.mainmenu;

import use_case.OutputBoundary;

public interface MainMenuOutputBoundary extends OutputBoundary {
    void prepareSuccessView(String message);

    //void prepareFailView(String errorMessage);
}
