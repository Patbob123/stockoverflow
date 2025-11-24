package view;

import interface_adapter.change_view.ChangeViewController;
import interface_adapter.mainmenu.MainMenuController;
import interface_adapter.mainmenu.MainMenuViewModel;

import javax.swing.*;
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
    private final JButton exitButton = new JButton(MainMenuViewModel.EXIT_BUTTON_LABEL);

    public MainMenuView(MainMenuViewModel mainMenuViewModel) {
        this.mainMenuViewModel = mainMenuViewModel;
        this.mainMenuViewModel.addPropertyChangeListener(this);
        this.mainMenuController = null;
        this.changeViewController = null;

        final JPanel buttons = new JPanel();
        buttons.add(stockButton);
        buttons.add(analyzePortfolioButton);
        buttons.add(createPortfolioButton);
        buttons.add(historyStockButton);
        buttons.add(exitButton);

        stockButton.addActionListener(evt -> {}); // Single stock logic (User Story 1)

        // Navigation to Portfolio Menu
        analyzePortfolioButton.addActionListener(evt -> {
            if (changeViewController != null) {
                changeViewController.changeView("PortfolioMenu");
            }
        });

        // Navigation to Create Portfolio
        createPortfolioButton.addActionListener(evt -> {
            if (changeViewController != null) {
                changeViewController.changeView("CreatePortfolioMenu");
            }
        });

        historyStockButton.addActionListener(evt -> {});

        exitButton.addActionListener(evt -> {
            if (mainMenuController != null) {
                mainMenuController.execute("exit");
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(buttons);
    }

    public String getViewName() { return viewName; }
    public void setMainMenuController(MainMenuController controller) { this.mainMenuController = controller; }
    public void setChangeViewController(ChangeViewController controller) { this.changeViewController = controller; }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}
}