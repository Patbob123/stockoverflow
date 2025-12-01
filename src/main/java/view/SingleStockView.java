package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.singlestock.SingleStockController;
import interface_adapter.singlestock.SingleStockState;
import interface_adapter.singlestock.SingleStockViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SingleStockView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = SingleStockViewModel.VIEW_NAME;

    private final SingleStockViewModel viewModel;
    private final SingleStockController controller;
    private final ViewManagerModel viewManagerModel;

    // --- UI Components ---
    private final JTextField tickerInputField = new JTextField();
    private final JTextField riskFreeInputField = new JTextField();
    private final JTextField fredApiKeyField = new JTextField();
    private final JTextArea reportArea = new JTextArea();

    // Buttons
    private final JButton analyzeButton;
    private final JButton compareButton;
    private final JButton monteCarloButton;
    private final JButton graphButton;

    // Secondary Buttons (Tools)
    private final JButton fredButton;
    private final JButton scenarioButton;
    private final JButton importButton;
    private final JButton historyButton;

    // Navigation
    private final JButton backButton;
    private final JButton exitButton;

    public SingleStockView(SingleStockController controller,
                           SingleStockViewModel viewModel,
                           ViewManagerModel viewManagerModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;

        this.viewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setBackground(SingleStockViewModel.BG_COLOUR);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(SingleStockViewModel.BG_COLOUR);
        headerPanel.setBorder(new EmptyBorder(20, 0, 10, 0));

        JLabel title = new JLabel("Stock Analysis Dashboard");
        title.setFont(SingleStockViewModel.TITLE_FONT.deriveFont(26f));
        title.setForeground(SingleStockViewModel.SECONDARY_COLOUR);
        headerPanel.add(title);

        this.add(headerPanel, BorderLayout.NORTH);


        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(SingleStockViewModel.BG_COLOUR);
        contentPanel.setBorder(new EmptyBorder(10, 40, 20, 40));


        JPanel inputCard = new JPanel(new GridBagLayout());
        inputCard.setBackground(SingleStockViewModel.CARD_COLOUR);
        inputCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SingleStockViewModel.BORDER_COLOUR, 1),
                new EmptyBorder(20, 30, 20, 30)
        ));
        inputCard.setMaximumSize(new Dimension(1000, 120));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ticker Input
        gbc.gridx = 0; gbc.gridy = 0;
        inputCard.add(createStyledLabel("Ticker Symbol:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tickerInputField.setText(SingleStockViewModel.DEFAULT_TICKER);
        styleTextField(tickerInputField);
        inputCard.add(tickerInputField, gbc);

        // Risk Free Input
        gbc.gridx = 2;
        inputCard.add(createStyledLabel("Risk-Free Rate:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        riskFreeInputField.setText(SingleStockViewModel.DEFAULT_RISK_FREE_TXT);
        styleTextField(riskFreeInputField);
        inputCard.add(riskFreeInputField, gbc);

        // Fred API Key
        gbc.gridx = 4;
        inputCard.add(createStyledLabel("Fred API Key:"), gbc);
        gbc.gridx = 5; gbc.weightx = 0.8;
        styleTextField(fredApiKeyField);
        inputCard.add(fredApiKeyField, gbc);

        contentPanel.add(inputCard);
        contentPanel.add(Box.createVerticalStrut(20));

        JPanel primaryActionPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        primaryActionPanel.setBackground(SingleStockViewModel.BG_COLOUR);
        primaryActionPanel.setMaximumSize(new Dimension(1000, 50));

        analyzeButton = createStyledButton(SingleStockViewModel.BUTTON_ANALYZE, SingleStockViewModel.PRIMARY_COLOUR);
        graphButton = createStyledButton("Show Graph", SingleStockViewModel.SUCCESS_COLOUR);
        compareButton = createStyledButton(SingleStockViewModel.BUTTON_COMPARE, SingleStockViewModel.SECONDARY_COLOUR);
        monteCarloButton = createStyledButton("Monte Carlo", SingleStockViewModel.SECONDARY_COLOUR);

        primaryActionPanel.add(analyzeButton);
        primaryActionPanel.add(graphButton);
        primaryActionPanel.add(compareButton);
        primaryActionPanel.add(monteCarloButton);

        contentPanel.add(primaryActionPanel);
        contentPanel.add(Box.createVerticalStrut(15));

        JPanel toolActionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        toolActionPanel.setBackground(SingleStockViewModel.BG_COLOUR);
        toolActionPanel.setMaximumSize(new Dimension(1000, 40));

        Color toolBtnColor = new Color(120, 130, 140);
        fredButton = createStyledButton("Update Fred", toolBtnColor);
        historyButton = createStyledButton("History", toolBtnColor);
        scenarioButton = createStyledButton("Scenario", toolBtnColor);
        importButton = createStyledButton("Import", toolBtnColor);

        Font toolFont = SingleStockViewModel.BASE_FONT.deriveFont(12f);
        fredButton.setFont(toolFont);
        historyButton.setFont(toolFont);
        scenarioButton.setFont(toolFont);
        importButton.setFont(toolFont);

        toolActionPanel.add(new JLabel("Tools: "));
        toolActionPanel.add(fredButton);
        toolActionPanel.add(historyButton);
        toolActionPanel.add(scenarioButton);
        toolActionPanel.add(importButton);

        contentPanel.add(toolActionPanel);
        contentPanel.add(Box.createVerticalStrut(15));

        JPanel reportContainer = new JPanel(new BorderLayout());
        reportContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(SingleStockViewModel.BORDER_COLOUR), " Analysis Output "
        ));
        reportContainer.setBackground(SingleStockViewModel.BG_COLOUR);

        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        reportArea.setBackground(new Color(35, 39, 42));
        reportArea.setForeground(new Color(230, 230, 230));
        reportArea.setMargin(new Insets(15, 15, 15, 15));
        reportArea.setLineWrap(true);
        reportArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(null);

        reportContainer.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(reportContainer);

        this.add(contentPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        bottomPanel.setBackground(SingleStockViewModel.BG_COLOUR);

        backButton = createStyledButton(SingleStockViewModel.BUTTON_BACK, Color.GRAY);
        exitButton = createStyledButton(SingleStockViewModel.BUTTON_EXIT, new Color(180, 60, 60));

        bottomPanel.add(backButton);
        bottomPanel.add(exitButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    // ================= UI Helpers =================

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SingleStockViewModel.BASE_FONT.deriveFont(Font.BOLD));
        label.setForeground(SingleStockViewModel.TEXT_SECONDARY);
        return label;
    }

    private void styleTextField(JTextField field) {
        field.setFont(SingleStockViewModel.BASE_FONT.deriveFont(14f));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        field.setBackground(new Color(245, 245, 245));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 35));
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFont(SingleStockViewModel.BUTTON_PRIMARY_FONT);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.setBackground(bg.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { button.setBackground(bg); }
        });

        button.addActionListener(this);
        return button;
    }

    // ================= Action Handling =================

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == analyzeButton) {
            String ticker = tickerInputField.getText().trim().toUpperCase();
            String rfText = riskFreeInputField.getText().trim();
            try {
                double rf = Double.parseDouble(rfText);
                controller.analyze(ticker, rf);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Risk Free Rate.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == compareButton) {
            String input = tickerInputField.getText().trim().toUpperCase();
            String[] parts = input.split("[,\\s]+");
            if (parts.length >= 2) {
                try {
                    double rf = Double.parseDouble(riskFreeInputField.getText().trim());
                    controller.compare(parts[0], parts[1], rf);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Risk Free Rate.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter two tickers separated by space or comma (e.g., AAPL MSFT)", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        } else if (source == monteCarloButton) {
            String ticker = tickerInputField.getText().trim().toUpperCase();
            if (!ticker.isEmpty()) controller.runMonteCarlo(ticker);
        } else if (source == graphButton) {
            String ticker = tickerInputField.getText().trim().toUpperCase();
            if (!ticker.isEmpty()) controller.showGraph(ticker);
        }

        else if (source == backButton) {
            if (viewManagerModel != null) {
                viewManagerModel.setActiveView("main menu");
                viewManagerModel.firePropertyChanged();
            }
        } else if (source == exitButton) {
            System.exit(0);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            SingleStockState state = (SingleStockState) evt.getNewValue();
            if (state.getReport() != null) {
                reportArea.setText(state.getReport());
                reportArea.setCaretPosition(0);
            }
            if (state.getErrorMessage() != null) {
                JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}