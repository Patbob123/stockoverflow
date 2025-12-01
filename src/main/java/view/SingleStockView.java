package view;

import data_access.FileMonteCarloDataAccess;
import data_access.StooqStockDataAccess;
import entities.StatisticsCalculator;
import entities.monte_carlo.MonteCarloSimulator;
import interface_adapter.monte_carlo.MonteCarloController;
import interface_adapter.monte_carlo.MonteCarloPresenter;
import interface_adapter.singlestock.SingleStockController;
import interface_adapter.singlestock.SingleStockViewInterface;
import interface_adapter.singlestock.SingleStockViewModel;
import use_case.monte_carlo.MonteCarloAnalysisInteractor;
import use_case.monte_carlo.MonteCarloOutputBoundary;
import use_case.singlestock.AnalyzeSingleStockOutputData;
import view.monte_carlo.MonteCarloInputPanel;
import view.monte_carlo.MonteCarloView;
import view.monte_carlo.SwingMonteCarloView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.Locale;

public class SingleStockView extends PaddedView<SingleStockViewModel, SingleStockController> implements SingleStockViewInterface {

    public static final String VIEW_NAME = "SingleStockView";
    // Inputs
    private final JTextField tickerField = new JTextField("AAPL", 10);
    private final JTextField rfField     = new JTextField("0.02", 6);
    private final JTextField FredAPI     = new JTextField("", 20);

    // Buttons (maybe more )
    private final JButton analyzeBtn = new JButton("Analyze");
    private final JButton backBtn    = new JButton("Back to Main");
    private final JButton FredApiBtn = new JButton("Fred API");
    private final JButton CompareBtn = new JButton("Compare");
    private final JButton ScenarioBtn = new JButton("Scenario and Stress Testing");
    private final JButton MonteCarloBtn = new JButton("Monte Carlo");
    private final JButton ImportBtn  = new JButton("Import/Export");
    private final JButton HistoryBtn  = new JButton("History");
    private final JButton ExitBtn  = new JButton("Exit");



    private final JTextArea infoArea = new JTextArea(18, 48);

    private SingleStockController controller;

    public SingleStockView(SingleStockViewModel controller) {
        super(controller);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(screenSize);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));


        JPanel inputs = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        g.gridx = 0; g.gridy = y;
        inputs.add(new JLabel("Ticker:"), g);
        g.gridx = 1; g.gridy = y++;
        inputs.add(tickerField, g);

        g.gridx = 0; g.gridy = y;
        inputs.add(new JLabel("Fred API Key:"), g);
        g.gridx = 1; g.gridy = y++;
        inputs.add(FredAPI, g);

        g.gridx = 0; g.gridy = y;
        inputs.add(new JLabel("Risk-free (manually type fornow:"), g);
        g.gridx = 1; g.gridy = y++;
        inputs.add(rfField, g);



        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttons.add(analyzeBtn);
        buttons.add(backBtn);
        buttons.add(FredApiBtn);//add later done
        buttons.add(CompareBtn);
        buttons.add(ScenarioBtn);
        buttons.add(MonteCarloBtn);
        buttons.add(ImportBtn);
        buttons.add(HistoryBtn);
        buttons.add(ExitBtn);


        g.gridx = 0; g.gridy = y;
        g.gridwidth = 2;
        inputs.add(buttons, g);


        infoArea.setEditable(false);
        infoArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane infoScroll = new JScrollPane(infoArea);

        add(inputs, BorderLayout.NORTH);
        add(infoScroll, BorderLayout.CENTER);


        analyzeBtn.addActionListener(this::onAnalyzeClicked);
        backBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Back to main menu (hook up in MainMenuBuilder)."));
        //connecting fred api to the button compare is added
        FredApiBtn.addActionListener(this::onFredApiClicked);
        CompareBtn.addActionListener(this::onCompareClicked);
        MonteCarloBtn.addActionListener(this::onMonteCarloClicked);
        backBtn.addActionListener(this::onBackClicked);
        ExitBtn.addActionListener(this::onExitClicked);
    }
    public void setController(SingleStockController controller) {
        this.controller = controller;
    }
    //when analyze button
    private void onAnalyzeClicked(ActionEvent e) {//ERRROR HANDLE
        String tkr = tickerField.getText().trim().toUpperCase(Locale.ROOT);
        if (!tkr.matches("[A-Z0-9.]{1,10}")) {
            showError("Invalid ticker format.");
            return;
        }

        double rf;
        try {
            rf = Double.parseDouble(rfField.getText().trim());
        } catch (NumberFormatException ex) {
            showError("Invalid risk-free rate.");
            return;
        }

        infoArea.setText("Analyzing " + tkr + "...");

        try {
            if (controller == null) {
                showError("Controller not set.");
                return;
            }
            controller.analyze(tkr, rf);
        } catch (RuntimeException ex) {
            // show what went wrong (Stooq error, not enough data, etc.)
            showError(ex.getMessage());
            infoArea.setText("");  // clear the "Analyzing ..." text
        }
    }
    private void onCompareClicked(ActionEvent e) {
        String baseTicker = tickerField.getText().trim().toUpperCase(Locale.ROOT);
        if (!baseTicker.matches("[A-Z0-9.]{1,10}")) {
            showError("Invalid base ticker format.");
            return;
        }

        String other = JOptionPane.showInputDialog(
                this,
                "Enter ticker to compare with " + baseTicker + ":",
                "Compare",
                JOptionPane.QUESTION_MESSAGE
        );
        if (other == null) {
            // user cancelled
            return;
        }

        other = other.trim().toUpperCase(Locale.ROOT);
        if (other.isEmpty()) {
            showError("Second ticker cannot be empty.");
            return;
        }
        if (!other.matches("[A-Z0-9.]{1,10}")) {
            showError("Invalid format for second ticker.");
            return;
        }

        double rf;
        try {
            rf = Double.parseDouble(rfField.getText().trim());
        } catch (NumberFormatException ex) {
            showError("Invalid risk-free rate.");
            return;
        }

        infoArea.setText("Comparing " + baseTicker + " vs " + other + "...");

        try {
            controller.compare(baseTicker, other, rf);
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
            infoArea.setText("");
        }
    }



//fredbuttom

    private void onFredApiClicked(ActionEvent e) {
        String key = FredAPI.getText().trim();
        if (key.isEmpty()) {
            showError("Please enter your FRED API key first.");
            return;
        }

        try {
            data_access.FredRiskFreeRateDataAccess fred =
                    new data_access.FredRiskFreeRateDataAccess(key);

            double rf = fred.getCurrentRiskFreeRate();
            rfField.setText(String.format(Locale.US, "%.4f", rf));
            JOptionPane.showMessageDialog(this,
                    String.format(Locale.US, "Loaded risk-free: %.2f%%", rf * 100),
                    "FRED", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException ex) {
            showError("FRED error: " + ex.getMessage());
        }
    }

    private void onMonteCarloClicked(ActionEvent e) {
        String tkr =  tickerField.getText().trim().toUpperCase(Locale.ROOT);

        if (!tkr.matches("[A-Z0-9.]{1,10}")) {
            showError("Invalid base ticker format.");
            return;
        }
        JFrame frame = new JFrame("Monte Carlo Simulation Input for " + tkr);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingMonteCarloView view = new  SwingMonteCarloView();
        MonteCarloAnalysisInteractor interactor = new MonteCarloAnalysisInteractor(new StooqStockDataAccess(),
                new MonteCarloSimulator(),
                new StatisticsCalculator(),
                new MonteCarloPresenter(view),
                new FileMonteCarloDataAccess());

        MonteCarloInputPanel panel = new MonteCarloInputPanel(tkr, new MonteCarloController(interactor));

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



    }

    private void onBackClicked(ActionEvent e) {

    }

    private void onExitClicked(ActionEvent e) {
        System.exit(0);
    }

    @Override
    public void showAnalysis(AnalyzeSingleStockOutputData outputData) {
        infoArea.setText(outputData.getReport());
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}