package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import entities.Portfolio.Portfolio;
import entities.Portfolio.PortfolioFactory;
import interface_adapter.ViewModel;
import interface_adapter.add_portfolio.AddPortfolioController;
import interface_adapter.add_portfolio.AddPortfolioViewModel;
import interface_adapter.change_view.ChangeViewController;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import lombok.Getter;
import lombok.Setter;

public class AddPortfolioView
        extends PaddedView<AddPortfolioViewModel, AddPortfolioController>
        implements ActionListener, PropertyChangeListener {
    public static final String VIEW_NAME = "AddPortfolioMenu";
    @Setter
    private ChangeViewController changeViewController;
    private final PortfolioFactory portfolioFactory = new PortfolioFactory();

    private final JButton createButton;
    private final JButton importButton;
    private final JButton backButton = createTextButton(AddPortfolioViewModel.BACK_BUTTON_LABEL);

    public AddPortfolioView(AddPortfolioViewModel viewModel) {
        super(viewModel);
        this.getViewModel().addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        final JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(AddPortfolioViewModel.CARD_COLOUR);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, AddPortfolioViewModel.BORDER_COLOUR),
                new EmptyBorder(20, 30, 20, 30)
        ));

        topPanel.add(backButton, BorderLayout.WEST);

        final JLabel title = new JLabel(AddPortfolioViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setFont(AddPortfolioViewModel.TITLE_FONT);
        title.setForeground(AddPortfolioViewModel.TEXT_PRIMARY);
        topPanel.add(title, BorderLayout.CENTER);

        final JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(100, 0));
        topPanel.add(spacer, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        final JLabel subtitleLabel = new JLabel(AddPortfolioViewModel.SUBTITLE_LABEL);
        subtitleLabel.setFont(AddPortfolioViewModel.SUBTITLE_FONT);
        subtitleLabel.setForeground(AddPortfolioViewModel.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subtitleLabel);

        mainPanel.add(Box.createVerticalStrut(40));

        createButton = createImportButton(
                AddPortfolioViewModel.CREATE_BUTTON_LABEL,
                AddPortfolioViewModel.PRIMARY_COLOUR,
                AddPortfolioViewModel.PRIMARY_HOVER
        );
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(createButton);

        mainPanel.add(Box.createVerticalStrut(AddPortfolioViewModel.BUTTON_SPACING));

        importButton = createImportButton(
                AddPortfolioViewModel.IMPORT_BUTTON_LABEL,
                AddPortfolioViewModel.SUCCESS_COLOUR,
                AddPortfolioViewModel.SUCCESS_HOVER
        );
        importButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(importButton);

        add(mainPanel, BorderLayout.CENTER);

        createButton.addActionListener(evt -> {
            if (this.getController() != null) {
                final Portfolio portfolio = portfolioFactory.createPortfolio("Untitled");
                final PortfolioMenuViewModel portfolioViewModel =
                        (PortfolioMenuViewModel) changeViewController.getViewModel(PortfolioMenuView.VIEW_NAME);
                portfolioViewModel.getState().setPortfolio(portfolio);
                portfolioViewModel.firePropertyChange();
                changeViewController.changeView("PortfolioMenu");
            }
        });

        importButton.addActionListener(evt -> {
            if (this.getController() != null) {
                changeViewController.changeView(ImportExportView.VIEW_NAME);
            }
        });

        backButton.addActionListener(evt -> {
            changeViewController.changeView(MainMenuView.VIEW_NAME);
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    private JButton createTextButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(AddPortfolioViewModel.BUTTON_SECONDARY_FONT);
        button.setForeground(AddPortfolioViewModel.TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        final Color originalColor = button.getForeground();

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(AddPortfolioViewModel.TEXT_SECONDARY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(originalColor);
            }
        });
        return button;
    }

    private JButton createImportButton(String text, Color bgColor, Color hoverColor) {
        final JButton button = new JButton(text);
        button.setFont(AddPortfolioViewModel.BUTTON_FONT);
        button.setForeground(AddPortfolioViewModel.TEXT_PRIMARY);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(
                AddPortfolioViewModel.BUTTON_WIDTH,
                AddPortfolioViewModel.BUTTON_HEIGHT
        ));
        button.setMaximumSize(new Dimension(
                AddPortfolioViewModel.BUTTON_WIDTH,
                AddPortfolioViewModel.BUTTON_HEIGHT
        ));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

}
