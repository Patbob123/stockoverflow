package view;

import interface_adapter.create_portfolio.CreatePortfolioController;
import interface_adapter.create_portfolio.CreatePortfolioState;
import interface_adapter.create_portfolio.CreatePortfolioViewModel;
import interface_adapter.singlestock.SingleStockViewModel;
import use_case.UserDataAccessInterface;
import entities.User;
import entities.Portfolio.Portfolio;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CreatePortfolioMenuView extends PaddedView<CreatePortfolioViewModel, CreatePortfolioController> implements ActionListener, PropertyChangeListener {
    public static final String VIEW_NAME = "CreatePortfolioMenu";

    private final CreatePortfolioViewModel viewModel;

    private final JTextField nameInputField = new JTextField();
    private final JButton createButton;
    private final JButton backButton;
    private final JPanel portfolioListPanel;

    public CreatePortfolioMenuView(CreatePortfolioViewModel viewModel) {
        super(viewModel);
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);


        this.setLayout(new BorderLayout());
        this.setBackground(SingleStockViewModel.BG_COLOUR);


        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 20));
        headerPanel.setBackground(SingleStockViewModel.BG_COLOUR);
        JLabel title = new JLabel("My Portfolios");
        title.setFont(SingleStockViewModel.TITLE_FONT);
        title.setForeground(SingleStockViewModel.SECONDARY_COLOUR);
        headerPanel.add(title);
        this.add(headerPanel, BorderLayout.NORTH);


        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        contentPanel.setBackground(SingleStockViewModel.BG_COLOUR);
        contentPanel.setBorder(new EmptyBorder(0, 40, 40, 40));


        JPanel createCard = createCardPanel();

        JLabel createTitle = new JLabel("Create New Portfolio");
        createTitle.setFont(SingleStockViewModel.BASE_FONT.deriveFont(Font.BOLD, 20f));
        createTitle.setForeground(SingleStockViewModel.PRIMARY_COLOUR);
        createTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subTitle = new JLabel("Enter a unique name below:");
        subTitle.setFont(SingleStockViewModel.BASE_FONT);
        subTitle.setForeground(SingleStockViewModel.TEXT_SECONDARY);
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameInputField.setFont(SingleStockViewModel.BASE_FONT.deriveFont(16f));
        nameInputField.setMaximumSize(new Dimension(300, 40));
        nameInputField.setHorizontalAlignment(JTextField.CENTER);

        createButton = createStyledButton("Create Portfolio", SingleStockViewModel.PRIMARY_COLOUR);
        createButton.setMaximumSize(new Dimension(200, 45));
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        createCard.setLayout(new BoxLayout(createCard, BoxLayout.Y_AXIS));
        createCard.add(Box.createVerticalStrut(40));
        createCard.add(createTitle);
        createCard.add(Box.createVerticalStrut(10));
        createCard.add(subTitle);
        createCard.add(Box.createVerticalStrut(30));
        createCard.add(nameInputField);
        createCard.add(Box.createVerticalStrut(20));
        createCard.add(createButton);
        createCard.add(Box.createVerticalGlue());


        JPanel listCard = createCardPanel();
        listCard.setLayout(new BorderLayout());

        JLabel listTitle = new JLabel("Your Collections");
        listTitle.setFont(SingleStockViewModel.BASE_FONT.deriveFont(Font.BOLD, 18f));
        listTitle.setForeground(SingleStockViewModel.SUCCESS_COLOUR);
        listTitle.setBorder(new EmptyBorder(0, 0, 15, 0));

        portfolioListPanel = new JPanel();
        portfolioListPanel.setLayout(new BoxLayout(portfolioListPanel, BoxLayout.Y_AXIS));
        portfolioListPanel.setBackground(SingleStockViewModel.CARD_COLOUR);

        JScrollPane scrollPane = new JScrollPane(portfolioListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(SingleStockViewModel.CARD_COLOUR);

        listCard.add(listTitle, BorderLayout.NORTH);
        listCard.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(createCard);
        contentPanel.add(listCard);
        this.add(contentPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 40, 20));
        bottomPanel.setBackground(SingleStockViewModel.BG_COLOUR);
        backButton = createStyledButton("Back to Dashboard", SingleStockViewModel.SECONDARY_COLOUR);
        bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        createButton.addActionListener(evt -> {
            CreatePortfolioState currentState = viewModel.getState();
            String currentUser = currentState.getUsername();
//            if (currentUser != null && !currentUser.isEmpty()) {
//                getController().execute(currentUser, currentState.getPortfolioName());
//                nameInputField.setText("");
//            } else {
//                JOptionPane.showMessageDialog(this, "Error: No user logged in.");
//            }
            getController().execute("Alex", currentState.getPortfolioName());
            nameInputField.setText("");
        });

        backButton.addActionListener(e -> {
        });

        nameInputField.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {
                CreatePortfolioState currentState = viewModel.getState();
                currentState.setPortfolioName(nameInputField.getText());
                viewModel.setState(currentState);
            }
        });
    }


    private void refreshPortfolioList() {
        portfolioListPanel.removeAll();
        if (getViewModel().getState() == null) return;

        for (String name : getViewModel().getState().getPortfolioNames()) {
            JButton pButton = new JButton(name);
            pButton.setFont(SingleStockViewModel.BASE_FONT.deriveFont(16f));
            pButton.setBackground(new Color(60, 64, 66));
            pButton.setForeground(Color.WHITE);
            pButton.setFocusPainted(false);
            pButton.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            pButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            pButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            pButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    pButton.setBackground(SingleStockViewModel.PRIMARY_COLOUR);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    pButton.setBackground(new Color(60, 64, 66));
                }
            });

            portfolioListPanel.add(pButton);
            portfolioListPanel.add(Box.createVerticalStrut(10));
        }
        portfolioListPanel.revalidate();
        portfolioListPanel.repaint();
    }

    // --- UI Helpers ---
    private JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setBackground(SingleStockViewModel.CARD_COLOUR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SingleStockViewModel.BORDER_COLOUR, 1),
                new EmptyBorder(30, 30, 30, 30)
        ));
        return card;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFont(SingleStockViewModel.BUTTON_PRIMARY_FONT);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    @Override public void actionPerformed(ActionEvent e) {}

    @Override public void propertyChange(PropertyChangeEvent evt) {
        CreatePortfolioState state = (CreatePortfolioState) evt.getNewValue();
        if (state.getError() != null) JOptionPane.showMessageDialog(this, state.getError());
        refreshPortfolioList();
    }
}