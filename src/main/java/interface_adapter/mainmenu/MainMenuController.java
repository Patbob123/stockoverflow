package interface_adapter.mainmenu;

import interface_adapter.AbsController;
import use_case.mainmenu.MainMenuInputBoundary;

public class MainMenuController extends AbsController {
    private final MainMenuInputBoundary mainMenuInteractor;
    public MainMenuController(MainMenuInputBoundary mainMenuInteractor) {
        this.mainMenuInteractor = mainMenuInteractor;
    }

    /**
     * Executes the Note related Use Cases.
     * @param note the note to be recorded
     */
    public void execute(String command) {
        switch(command) {
            case "exit":
                mainMenuInteractor.executeExit();
                break;
        }

    }

    public void executeLogout() {
        mainMenuInteractor.executeLogout();
    }
}
