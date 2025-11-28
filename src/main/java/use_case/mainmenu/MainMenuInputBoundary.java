package use_case.mainmenu;

import use_case.InputBoundary;

public interface MainMenuInputBoundary extends InputBoundary {

    void executeAnaylzePortfolio();

    void executeAnaylzeStock();

    void executeLoadStock();

    void executeExit();

}
