package view.monte_carlo;

import data_access.StooqStockDataAccess;
import entities.StatisticsCalculator;
import entities.monte_carlo.MonteCarloSimulator;
import interface_adapter.monte_carlo.MonteCarloController;
import interface_adapter.monte_carlo.MonteCarloPresenter;
import use_case.monte_carlo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonteCarloInputPanel extends JPanel {

    private final MonteCarloController controller;
    private final String selectedTicker; // New field to store the pre-selected Ticker

    // UI Components
    private final JLabel tickerLabel = new JLabel(); // Label to display the selected Ticker
    private final JFormattedTextField horizonField = new JFormattedTextField(1.0);
    private final JFormattedTextField stepsField = new JFormattedTextField(252);
    private final JFormattedTextField pathsField = new JFormattedTextField(500);
    private final JButton runButton = new JButton("Run Simulation");

    /**
     * Constructs the input panel, requiring the pre-selected Ticker and the Controller.
     */
    public MonteCarloInputPanel(String ticker, MonteCarloController controller) {
        this.selectedTicker = ticker.toUpperCase();
        this.controller = controller;
        this.tickerLabel.setText("Stock: " + this.selectedTicker);

        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createTitledBorder("Simulation Parameters"));
        this.setPreferredSize(new Dimension(350, 250));

        horizonField.setValue(1.0);
        stepsField.setValue(252);
        pathsField.setValue(500);

        setupLayout();
        setupActionListener();
    }

    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Ticker Display Row (Read-only)
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2; // Span across both columns
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(tickerLabel, gbc);

        // Reset width and span for input fields
        gbc.gridwidth = 1;

        // Horizon Years Row
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.weightx = 0.0;
        this.add(new JLabel("Horizon (Years):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        this.add(horizonField, gbc);

        // Number of Steps Row
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.weightx = 0.0;
        this.add(new JLabel("Steps (e.g., 252 for daily):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        this.add(stepsField, gbc);

        // Number of Paths Row
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.weightx = 0.0;
        this.add(new JLabel("Number of Paths:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        this.add(pathsField, gbc);

        // Run Button Row
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 5, 5);
        this.add(runButton, gbc);
    }

    private void setupActionListener() {
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    // Parse numbers and validate
                    double horizon = ((Number) horizonField.getValue()).doubleValue();
                    int steps = ((Number) stepsField.getValue()).intValue();
                    int paths = ((Number) pathsField.getValue()).intValue();

                    if (horizon <= 0 || steps <= 0 || paths <= 0) {
                        throw new IllegalArgumentException("All numerical fields must be positive.");
                    }

                    // Call the Controller, passing the known ticker
                    controller.executeSimulation(selectedTicker, horizon, steps, paths);

                } catch (ClassCastException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MonteCarloInputPanel.this,
                            "Please enter valid numerical values.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(MonteCarloInputPanel.this,
                            ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {

        SwingMonteCarloView view = new SwingMonteCarloView();
        MonteCarloAnalysisInteractor interactor = new MonteCarloAnalysisInteractor(new StooqStockDataAccess(),
        new MonteCarloSimulator(),
        new StatisticsCalculator(),
        new MonteCarloPresenter(view));

        // Instantiate the Controller
        MonteCarloController controller = new MonteCarloController(interactor);

        // Create the GUI Frame and Panel, passing the pre-selected ticker
        JFrame frame = new JFrame("Monte Carlo Simulation Input");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MonteCarloInputPanel inputPanel = new MonteCarloInputPanel("AAPL", controller);

        frame.add(inputPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}