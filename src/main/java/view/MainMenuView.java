package view;

import interface_adapter.mainmenu.MainMenuController;
import interface_adapter.mainmenu.MainMenuState;
import interface_adapter.mainmenu.MainMenuViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The Main Menu View.
 * Displays the dashboard with options to navigate to Portfolio, Search, or Logout.
 */
public class MainMenuView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "main menu";

    private final MainMenuViewModel mainMenuViewModel;
    private final MainMenuController mainMenuController;

    private final JLabel username;
    private final JButton portfolio;
    private final JButton search;
    private final JButton logout;

    public MainMenuView(MainMenuViewModel mainMenuViewModel, MainMenuController controller) {
        this.mainMenuViewModel = mainMenuViewModel;
        this.mainMenuController = controller;

        // Listen for changes in the ViewModel state (e.g., username updates)
        this.mainMenuViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(MainMenuViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameInfo = new JLabel("Current User: ");
        username = new JLabel();

        JPanel userInfo = new JPanel();
        userInfo.add(usernameInfo);
        userInfo.add(username);

        JPanel buttons = new JPanel();
        portfolio = new JButton(MainMenuViewModel.PORTFOLIO_BUTTON_LABEL);
        buttons.add(portfolio);

        search = new JButton(MainMenuViewModel.SEARCH_BUTTON_LABEL);
        buttons.add(search);

        logout = new JButton(MainMenuViewModel.LOGOUT_BUTTON_LABEL);
        buttons.add(logout);

        // --- Action Listeners ---

        portfolio.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(portfolio)) {
                            // Logic to switch to Portfolio View
                            mainMenuController.switchToPortfolioView();
                        }
                    }
                }
        );

        search.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(search)) {
                            // Logic to switch to Search View
                            mainMenuController.switchToSearchView();
                        }
                    }
                }
        );

        logout.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logout)) {
                            // Logic to logout
                            mainMenuController.executeLogout();
                        }
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(userInfo);
        this.add(buttons);
    }


    public MainMenuController getMainMenuController() {
        return mainMenuController;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Update the username label when the state changes (e.g., after login)
        MainMenuState state = (MainMenuState) evt.getNewValue();
        if (state.getUsername() != null) {
            username.setText(state.getUsername());
        }
    }
}