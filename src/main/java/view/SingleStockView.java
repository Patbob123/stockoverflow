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
    private final JTextField tickerInputField = new JTextField(15);
    private final JTextField riskFreeInputField = new JTextField(6);
    private final JTextField fredApiKeyField = new JTextField(15);
    private final JTextArea reportArea = new JTextArea(15, 50);

    // Buttons
    private final JButton analyzeButton;
    private final JButton compareButton;
    private final JButton monteCarloButton;
    private final JButton graphButton;
    private final JButton fredButton;
    private final JButton backButton;
    private final JButton scenarioButton;
    private final JButton importButton;
    private final JButton historyButton;
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

        // --- Title ---
        JLabel title = new JLabel("Stock Analysis Dashboard");
        title.setFont(SingleStockViewModel.TITLE_FONT);
        title.setForeground(SingleStockViewModel.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(SingleStockViewModel.BG_COLOUR);
        titlePanel.add(title);
        this.add(titlePanel, BorderLayout.NORTH);

        // --- Center ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(SingleStockViewModel.BG_COLOUR);
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Inputs
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(SingleStockViewModel.CARD_COLOUR);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SingleStockViewModel.BORDER_COLOUR, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(createStyledLabel(SingleStockViewModel.LABEL_TICKER), gbc);
        gbc.gridx = 1;
        tickerInputField.setText(SingleStockViewModel.DEFAULT_TICKER);
        inputPanel.add(tickerInputField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(createStyledLabel(SingleStockViewModel.LABEL_RISK_FREE), gbc);
        gbc.gridx = 1;
        riskFreeInputField.setText(SingleStockViewModel.DEFAULT_RISK_FREE_TXT);
        inputPanel.add(riskFreeInputField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(createStyledLabel(SingleStockViewModel.LABEL_FRED_API), gbc);
        gbc.gridx = 1;
        inputPanel.add(fredApiKeyField, gbc);

        centerPanel.add(inputPanel);
        centerPanel.add(Box.createVerticalStrut(20));

        // Buttons Grid
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonsPanel.setBackground(SingleStockViewModel.BG_COLOUR);

        analyzeButton = createStyledButton(SingleStockViewModel.BUTTON_ANALYZE, SingleStockViewModel.PRIMARY_COLOUR);
        compareButton = createStyledButton(SingleStockViewModel.BUTTON_COMPARE, SingleStockViewModel.SECONDARY_COLOUR);
        monteCarloButton = createStyledButton(SingleStockViewModel.BUTTON_MONTECARLO, SingleStockViewModel.SECONDARY_COLOUR);
        graphButton = createStyledButton("Show Graph", SingleStockViewModel.SUCCESS_COLOUR);
        fredButton = createStyledButton(SingleStockViewModel.BUTTON_FRED, SingleStockViewModel.SECONDARY_COLOUR);
        scenarioButton = createStyledButton(SingleStockViewModel.BUTTON_SCENARIO, SingleStockViewModel.SECONDARY_COLOUR);
        importButton = createStyledButton(SingleStockViewModel.BUTTON_IMPORT, SingleStockViewModel.SECONDARY_COLOUR);
        historyButton = createStyledButton(SingleStockViewModel.BUTTON_HISTORY, SingleStockViewModel.SECONDARY_COLOUR);

        buttonsPanel.add(analyzeButton);
        buttonsPanel.add(compareButton);
        buttonsPanel.add(monteCarloButton);
        buttonsPanel.add(graphButton);
        buttonsPanel.add(fredButton);
        buttonsPanel.add(scenarioButton);
        buttonsPanel.add(importButton);
        buttonsPanel.add(historyButton);

        centerPanel.add(buttonsPanel);
        centerPanel.add(Box.createVerticalStrut(20));

        // Report Area
        JLabel reportLabel = createStyledLabel("Analysis Report:");
        reportLabel.setForeground(SingleStockViewModel.TEXT_SECONDARY);
        JPanel labelWrapper = new JPanel(new BorderLayout());
        labelWrapper.setBackground(SingleStockViewModel.BG_COLOUR);
        labelWrapper.add(reportLabel, BorderLayout.WEST);
        centerPanel.add(labelWrapper);
        centerPanel.add(Box.createVerticalStrut(5));

        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reportArea.setBackground(new Color(240, 240, 240));
        reportArea.setLineWrap(true);
        reportArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(SingleStockViewModel.BORDER_COLOUR));
        scrollPane.setPreferredSize(new Dimension(600, 300));
        centerPanel.add(scrollPane);

        this.add(centerPanel, BorderLayout.CENTER);

        // --- Bottom ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(SingleStockViewModel.BG_COLOUR);

        exitButton = createStyledButton(SingleStockViewModel.BUTTON_EXIT, new Color(180, 60, 60));
        backButton = createStyledButton(SingleStockViewModel.BUTTON_BACK, SingleStockViewModel.SECONDARY_COLOUR);

        bottomPanel.add(exitButton);
        bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SingleStockViewModel.BASE_FONT);
        label.setForeground(SingleStockViewModel.TEXT_PRIMARY);
        return label;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFont(SingleStockViewModel.BUTTON_PRIMARY_FONT);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        return button;
    }

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
                JOptionPane.showMessageDialog(this, "Enter two tickers (e.g., AAPL MSFT)", "Input Error", JOptionPane.WARNING_MESSAGE);
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