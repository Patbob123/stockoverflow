package use_case.mainmenu;

import entities.User;

public class MainMenuInteractor implements MainMenuInputBoundary {
    //private final NoteDataAccessInterface noteDataAccessInterface;
    private final MainMenuOutputBoundary mainMenuOutputBoundary;

    private final User user = new User("123", "guy");

    public MainMenuInteractor(MainMenuOutputBoundary mainMenuOutputBoundary) {
        this.mainMenuOutputBoundary = mainMenuOutputBoundary;
    }

    @Override
    public void executeLogout() {
        mainMenuOutputBoundary.prepareLoginView();
    }

    @Override
    public void executeExit() {
        mainMenuOutputBoundary.prepareSuccessView("we went to different view");
        System.exit(0);
    }



}
