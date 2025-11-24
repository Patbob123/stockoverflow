package view;

import interface_adapter.change_view.ChangeViewController;
import interface_adapter.mainmenu.MainMenuController;
import interface_adapter.mainmenu.MainMenuViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainMenuView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "MainMenu";
    private final MainMenuViewModel mainMenuViewModel;

    private MainMenuController mainMenuController;
    private ChangeViewController changeViewController;

    private final JButton stockButton = new JButton(MainMenuViewModel.STOCK_BUTTON_LABEL);
    private final JButton analyzePortfolioButton = new JButton(MainMenuViewModel.PORTFOLIO_BUTTON_LABEL);
    private final JButton createPortfolioButton = new JButton(MainMenuViewModel.CREATE_PORTFOLIO_BUTTON_LABEL);
    private final JButton historyStockButton = new JButton(MainMenuViewModel.HISTORY_BUTTON_LABEL);
    private final JButton simulationButton = new JButton("Simulation"); // Label could be in ViewModel
    private final JButton exitButton = new JButton(MainMenuViewModel.EXIT_BUTTON_LABEL);

    public MainMenuView(MainMenuViewModel mainMenuViewModel) {
        //noteName.setAlignmentX(Component.CENTER_ALIGNMENT); ADD DATE HERE TOO
        this.mainMenuViewModel = mainMenuViewModel;
        this.mainMenuViewModel.addPropertyChangeListener(this);
        this.mainMenuController = null;
        this.changeViewController = null;

        final JPanel buttons = new JPanel();
        buttons.add(stockButton);
        buttons.add(analyzePortfolioButton);
        buttons.add(createPortfolioButton);
        buttons.add(historyStockButton);
        buttons.add(simulationButton);
        buttons.add(exitButton);

        stockButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(stockButton)) {
                        //MainMenuController.execute(noteInputField.getText());

                    }
                }
        );

        analyzePortfolioButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(analyzePortfolioButton)) {
                        changeViewController.changeView("PortfolioMenu");
                    }
                }
        );

        createPortfolioButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(createPortfolioButton)) {
                        changeViewController.changeView("CreatePortfolioMenu");

                    }
                }
        );

        historyStockButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(historyStockButton)) {
                        //MainMenuController.execute(noteInputField.getText());

                    }
                }
        );

        simulationButton.addActionListener(
            evt -> {
                if (evt.getSource().equals(simulationButton)) {
                    if (changeViewController != null) {
                        changeViewController.changeView("simulation");
                    }
                }
            }
        );

        exitButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(exitButton)) {
                        mainMenuController.execute("exit"); //fix this later

                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //this.add(noteName);
        this.add(buttons);
    }

    public String getViewName() {
        return viewName;
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public void setChangeViewController(ChangeViewController changeViewController) {
        this.changeViewController = changeViewController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
