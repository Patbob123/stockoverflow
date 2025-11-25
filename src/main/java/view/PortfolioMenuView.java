package view;

import interface_adapter.create_portfolio.CreatePortfolioController;
import interface_adapter.create_portfolio.CreatePortfolioState;
import interface_adapter.create_portfolio.CreatePortfolioViewModel;
import interface_adapter.mainmenu.MainMenuController;
import interface_adapter.ViewManagerModel;
import use_case.UserDataAccessInterface;
import entities.User;
import entities.Portfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PortfolioMenuView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "create portfolio";

    private final CreatePortfolioViewModel viewModel;
    private final CreatePortfolioController controller;
    private final UserDataAccessInterface userDataAccess;
    private final ViewManagerModel viewManagerModel;
    private final MainMenuController mainMenuController; // 新增：用于跳转

    private final JTextField nameInputField = new JTextField(15);
    private final JButton createButton;
    private final JButton backButton;
    private final JPanel portfolioListPanel;

    public PortfolioMenuView(CreatePortfolioViewModel viewModel,
                             CreatePortfolioController controller,
                             UserDataAccessInterface userDataAccess,
                             ViewManagerModel viewManagerModel,
                             MainMenuController mainMenuController) {

        this.viewModel = viewModel;
        this.controller = controller;
        this.userDataAccess = userDataAccess;
        this.viewManagerModel = viewManagerModel;
        this.mainMenuController = mainMenuController;

        this.viewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(CreatePortfolioViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel nameInfo = new LabelTextPanel(
                new JLabel(CreatePortfolioViewModel.INPUT_LABEL), nameInputField);

        createButton = new JButton(CreatePortfolioViewModel.CREATE_BUTTON_LABEL);
        backButton = new JButton(CreatePortfolioViewModel.BACK_LABEL);

        JPanel inputPanel = new JPanel();
        inputPanel.add(nameInfo);
        inputPanel.add(createButton);
        inputPanel.add(backButton);

        portfolioListPanel = new JPanel();
        portfolioListPanel.setLayout(new BoxLayout(portfolioListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(portfolioListPanel);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        // Create Button Logic
        createButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(createButton)) {
                            CreatePortfolioState currentState = viewModel.getState();
                            String currentUser = currentState.getUsername();

                            if (currentUser != null && !currentUser.isEmpty()) {
                                controller.execute(currentUser, currentState.getPortfolioName());
                            } else {
                                JOptionPane.showMessageDialog(null, "Error: No user logged in.");
                            }
                        }
                    }
                }
        );

        backButton.addActionListener(e -> {
            viewManagerModel.setActiveView("main menu");
            viewManagerModel.firePropertyChanged();
        });

        nameInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                CreatePortfolioState currentState = viewModel.getState();
                currentState.setPortfolioName(nameInputField.getText() + e.getKeyChar());
                viewModel.setState(currentState);
            }
            @Override public void keyPressed(KeyEvent e) {}
            @Override public void keyReleased(KeyEvent e) {}
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(inputPanel);
        this.add(new JLabel("Your Portfolios (Click to Add Stocks):"));
        this.add(scrollPane);
    }

    private void refreshPortfolioList() {
        portfolioListPanel.removeAll();
        String currentUsername = viewModel.getState().getUsername();

        if (currentUsername != null && !currentUsername.isEmpty()) {
            User user = userDataAccess.get(currentUsername);
            if (user != null && user.getPortfolioList() != null) {
                for (Portfolio p : user.getPortfolioList()) {
                    JButton pButton = new JButton(p.getName());

                    pButton.addActionListener(e -> {
                        mainMenuController.goToAddStock(p.getName(), currentUsername);
                    });

                    portfolioListPanel.add(pButton);
                }
            }
        }
        portfolioListPanel.revalidate();
        portfolioListPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CreatePortfolioState state = (CreatePortfolioState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
        } else {
            nameInputField.setText("");
            refreshPortfolioList();
        }
    }
}