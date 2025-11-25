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

        this.mainMenuViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(mainMenuViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameInfo = new JLabel(mainMenuViewModel.LOGGED_IN_USER_LABEL);
        username = new JLabel();

        JPanel userInfoPanel = new JPanel();
        userInfoPanel.add(usernameInfo);
        userInfoPanel.add(username);

        JPanel buttons = new JPanel();
        portfolio = new JButton(mainMenuViewModel.PORTFOLIO_BUTTON_LABEL);
        buttons.add(portfolio);

        search = new JButton(mainMenuViewModel.SEARCH_BUTTON_LABEL);
        buttons.add(search);

        logout = new JButton(mainMenuViewModel.LOGOUT_BUTTON_LABEL);
        buttons.add(logout);

        portfolio.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(portfolio)) {
                            System.out.println("Go to Portfolio clicked");
                        }
                    }
                }
        );

        search.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(search)) {
                            System.out.println("Go to Search clicked");
                        }
                    }
                }
        );

        logout.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logout)) {
                            mainMenuController.executeLogout();
                        }
                    }
                }
        );

        search.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(search)) {
                            mainMenuController.goToSearch(); // <--- 调用这个
                        }
                    }
                }
        );

        portfolio.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(portfolio)) {
                            String currentUser = username.getText();
                            System.out.println("MainMenuView: Clicking Portfolio for user: [" + currentUser + "]"); // 调试打印

                            if (currentUser == null || currentUser.trim().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Error: No user logged in (Label is empty).");
                            } else {
                                mainMenuController.goToPortfolio(currentUser);
                            }
                        }
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(userInfoPanel);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        MainMenuState state = (MainMenuState) evt.getNewValue();
        username.setText(state.getUsername());
    }
}