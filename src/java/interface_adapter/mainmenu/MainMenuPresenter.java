package interface_adapter.mainmenu;

import use_case.mainmenu.MainMenuOutputBoundary;

public class MainMenuPresenter implements MainMenuOutputBoundary {

    private final MainMenuViewModel mainMenuViewModel;

    public MainMenuPresenter(MainMenuViewModel mainMenuViewModel) {
        this.mainMenuViewModel = mainMenuViewModel;
    }

    @Override
    public void prepareSuccessView(String message) {
        System.out.println("interface_adapter.mainmenu.Main menu did something idk what dont ask me");
        mainMenuViewModel.firePropertyChange();
    }
}
