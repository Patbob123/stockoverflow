package use_case.mainmenu;

import use_case.OutputBoundary;

public interface MainMenuOutputBoundary extends OutputBoundary {
    void prepareLoginView();
    void prepareSuccessView(String message);

    //void prepareFailView(String errorMessage);
}
