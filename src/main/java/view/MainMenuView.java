package view;

import entities.Portfolio.Portfolio;
import entities.Portfolio.PortfolioFactory;
import interface_adapter.ViewModel;
import interface_adapter.change_view.ChangeViewController;
import interface_adapter.mainmenu.MainMenuController;
import interface_adapter.mainmenu.MainMenuViewModel;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import interface_adapter.singlestock.SingleStockViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainMenuView extends PaddedView<MainMenuViewModel, MainMenuController> implements ActionListener, PropertyChangeListener {

    @Setter
    private ChangeViewController changeViewController;

    private final JButton stockButton = new JButton(MainMenuViewModel.STOCK_BUTTON_LABEL);
    private final JButton analyzePortfolioButton = new JButton(MainMenuViewModel.PORTFOLIO_BUTTON_LABEL);
    private final JButton createPortfolioButton = new JButton(MainMenuViewModel.CREATE_PORTFOLIO_BUTTON_LABEL);
    private final JButton historyStockButton = new JButton(MainMenuViewModel.HISTORY_BUTTON_LABEL);
    private final JButton exitButton = new JButton(MainMenuViewModel.EXIT_BUTTON_LABEL);
    private final PortfolioFactory portfolioFactory = new PortfolioFactory();

    private final JLabel usernameLabel;
    private final JButton portfolioCard;
    private final JButton singleStockCard;
    private final JButton logoutButton;

    public static final String VIEW_NAME = "MainMenu";

    public MainMenuView(MainMenuViewModel viewModel) {
        super(viewModel);
        //noteName.setAlignmentX(Component.CENTER_ALIGNMENT); ADD DATE HERE TOO
        this.getViewModel().addPropertyChangeListener(this);
        this.changeViewController = null;




        final JPanel buttons = new JPanel();
        buttons.add(stockButton);
        buttons.add(analyzePortfolioButton);
        buttons.add(createPortfolioButton);
        buttons.add(historyStockButton);
        buttons.add(exitButton);

        stockButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(stockButton)) {
                        changeViewController.changeView("SingleStockMenu");

                    }
                }
        );

        analyzePortfolioButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(analyzePortfolioButton)) {
                        final Portfolio portfolio = portfolioFactory.createPortfolio("Untitled");
                        final PortfolioMenuViewModel portfolioViewModel =
                                (PortfolioMenuViewModel) changeViewController
                                        .getViewModel(PortfolioMenuView.VIEW_NAME);
                        portfolioViewModel.getState().setPortfolio(portfolio);
                        portfolioViewModel.firePropertyChange();
                        changeViewController.changeView("PortfolioMenu");
                    }
                }
        );

        createPortfolioButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(createPortfolioButton)) {
                        changeViewController.changeView("AddPortfolioMenu");

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

        exitButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(exitButton)) {
                        this.getController().execute("exit"); //fix this later

                    }
                }
        );

        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //this.add(noteName);
        //this.add(buttons);

        this.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(40, 50, 20, 50));

        JLabel titleLabel = new JLabel("Stock Overflow Dashboard");
        titleLabel.setFont(SingleStockViewModel.TITLE_FONT.deriveFont(36f));
        titleLabel.setForeground(Color.WHITE);

        usernameLabel = new JLabel("Welcome, " + viewModel.getState().getUsername());
        usernameLabel.setFont(SingleStockViewModel.BASE_FONT.deriveFont(Font.BOLD, 18f));
        usernameLabel.setForeground(SingleStockViewModel.PRIMARY_COLOUR);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(usernameLabel, BorderLayout.SOUTH);
        this.add(headerPanel, BorderLayout.NORTH);

        // Cards
        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        cardsPanel.setBorder(new EmptyBorder(20, 50, 50, 50));

        portfolioCard = createDashboardCard("My Portfolios", "Manage holdings", "P", SingleStockViewModel.SUCCESS_COLOUR);
        singleStockCard = createDashboardCard("Market Analysis", "Analyze stocks", "A", SingleStockViewModel.PRIMARY_COLOUR);

        cardsPanel.add(portfolioCard);
        cardsPanel.add(singleStockCard);
        this.add(cardsPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBorder(new EmptyBorder(0, 0, 30, 50));

        logoutButton = new JButton("Log Out");
        logoutButton.setFont(SingleStockViewModel.BUTTON_PRIMARY_FONT);
        logoutButton.setBackground(new Color(200, 60, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(140, 45));

        footerPanel.add(logoutButton);
        this.add(footerPanel, BorderLayout.SOUTH);

        // Listeners
//        portfolioCard.addActionListener(e -> mainMenuController.switchToPortfolioView());
//        singleStockCard.addActionListener(e -> mainMenuController.switchToSingleStockView());
//        logoutButton.addActionListener(e -> mainMenuController.executeLogout());
    }

    private JButton createDashboardCard(String title, String subtitle, String iconText, Color accentColor) {
        JButton card = new JButton();
        card.setLayout(new BorderLayout());
        card.setBackground(SingleStockViewModel.CARD_COLOUR);
        card.setBorder(BorderFactory.createMatteBorder(0, 6, 0, 0, accentColor));
        card.setFocusPainted(false);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel iconLabel = new JLabel(iconText);
        iconLabel.setFont(new Font("Serif", Font.BOLD, 60));
        iconLabel.setForeground(new Color(255, 255, 255, 40));
        iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(SingleStockViewModel.TITLE_FONT.deriveFont(26f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subLabel = new JLabel(subtitle);
        subLabel.setFont(SingleStockViewModel.BASE_FONT.deriveFont(14f));
        subLabel.setForeground(SingleStockViewModel.TEXT_SECONDARY);
        subLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(iconLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(subLabel);

        card.add(contentPanel, BorderLayout.CENTER);

        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(SingleStockViewModel.CARD_COLOUR.brighter());
                card.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, accentColor));
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(SingleStockViewModel.CARD_COLOUR);
                card.setBorder(BorderFactory.createMatteBorder(0, 6, 0, 0, accentColor));
            }
        });

        return card;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        usernameLabel.setText("Welcome, " + getViewModel().getState().getUsername());
    }
}
