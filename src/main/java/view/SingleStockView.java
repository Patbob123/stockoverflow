package view;

import data_access.FredRiskFreeRateDataAccess;
import interface_adapter.singlestock.SingleStockController;
import interface_adapter.singlestock.SingleStockViewInterface;
import interface_adapter.singlestock.SingleStockViewModel;
import use_case.singlestock.AnalyzeSingleStockOutputData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class SingleStockView extends JPanel implements SingleStockViewInterface {

    private static final Path HISTORY_FILE =
            Paths.get(System.getProperty("user.home"),
                    SingleStockViewModel.HISTORY_FILE_NAME);
            //or maybe to the project im not sure
    //private static final Path HISTORY_FILE = Paths.get("stockoverflow-history.txt");   // not sure which is better , choose when mege with import stuff

    private final SingleStockViewModel viewModel;

    // Inputs
    private final JTextField tickerField;
    private final JTextField rfField;
    private final JTextField FredAPI;

    // Buttons (maybe more )
    private final JButton analyzeBtn;
    private final JButton backBtn;
    private final JButton FredApiBtn;
    private final JButton CompareBtn;
    private final JButton ScenarioBtn;
    private final JButton MonteCarloBtn;
    private final JButton ImportBtn;
    private final JButton HistoryBtn;
    private final JButton ExitBtn;

    //now lets add history

    private final DefaultComboBoxModel<String> historyModel = new DefaultComboBoxModel<>();
    private final JComboBox<String> historyBox = new JComboBox<>(historyModel);



    private final JTextArea infoArea = new JTextArea(18, 48);

    private SingleStockController controller;

    public SingleStockView(SingleStockViewModel viewModel,
                           SingleStockController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        // Initialize text fields using ViewModel defaults
        this.tickerField = new JTextField(
                SingleStockViewModel.DEFAULT_TICKER,
                SingleStockViewModel.TICKER_FIELD_COLUMNS);

        this.rfField = new JTextField(
                SingleStockViewModel.DEFAULT_RISK_FREE_TXT,
                SingleStockViewModel.RISK_FREE_FIELD_COLUMNS);

        this.FredAPI = new JTextField(
                viewModel.getFredApiKey(),
                SingleStockViewModel.FRED_API_FIELD_COLUMNS);

        // Initialize buttons using ViewModel labels
        this.analyzeBtn    = new JButton(SingleStockViewModel.BUTTON_ANALYZE);
        this.backBtn       = new JButton(SingleStockViewModel.BUTTON_BACK);
        this.FredApiBtn    = new JButton(SingleStockViewModel.BUTTON_FRED);
        this.CompareBtn    = new JButton(SingleStockViewModel.BUTTON_COMPARE);
        this.ScenarioBtn   = new JButton(SingleStockViewModel.BUTTON_SCENARIO);
        this.MonteCarloBtn = new JButton(SingleStockViewModel.BUTTON_MONTECARLO);
        this.ImportBtn     = new JButton(SingleStockViewModel.BUTTON_IMPORT);
        this.HistoryBtn    = new JButton(SingleStockViewModel.BUTTON_HISTORY);
        this.ExitBtn       = new JButton(SingleStockViewModel.BUTTON_EXIT);
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
        inputs.add(new JLabel(SingleStockViewModel.LABEL_TICKER), g);
        g.gridx = 1; g.gridy = y++;
        inputs.add(tickerField, g);

        g.gridx = 0; g.gridy = y;
        inputs.add(new JLabel(SingleStockViewModel.LABEL_HISTORY), g);
        g.gridx = 1; g.gridy = y++;
        historyBox.setPrototypeDisplayValue("XXXXXXXXXX");
        inputs.add(historyBox, g);

        g.gridx = 0; g.gridy = y;
        inputs.add(new JLabel(SingleStockViewModel.LABEL_FRED_API), g);
        g.gridx = 1; g.gridy = y++;
        inputs.add(FredAPI, g);

        g.gridx = 0; g.gridy = y;
        inputs.add(new JLabel(SingleStockViewModel.LABEL_RISK_FREE), g);
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

        loadHistoryFromDisk();

        historyBox.addActionListener(e -> {
            Object sel = historyBox.getSelectedItem();
            if (sel != null) {
                String t = sel.toString();
                tickerField.setText(t);
                viewModel.setCurrentTicker(t);
            }
        });

        analyzeBtn.addActionListener(this::onAnalyzeClicked);
        backBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Back to main menu (hook up in MainMenuBuilder)."));
        //connecting fred api to the button compare is added
        FredApiBtn.addActionListener(this::onFredApiClicked);
        CompareBtn.addActionListener(this::onCompareClicked);
        ScenarioBtn.addActionListener(this::onScenarioClicked);
        MonteCarloBtn.addActionListener(this::onMonteCarloClicked);
    }
    public void setController(SingleStockController controller) {
        this.controller = controller;
    }
    //lets save history after we closed the app
    private void addToHistory(String ticker) {
        if (ticker == null) return;
        String t = ticker.trim().toUpperCase(Locale.ROOT);
        if (t.isEmpty()) return;

        // Remove duplicate if exists
        for (int i = 0; i < historyModel.getSize(); i++) {
            if (t.equals(historyModel.getElementAt(i))) {
                historyModel.removeElementAt(i);
                break;
            }
        }
        // Insert at top
        historyModel.insertElementAt(t, 0);
        historyBox.setSelectedIndex(0);

        // Persist updated model to disk
        saveHistoryToDisk();
    }


    private void loadHistoryFromDisk() {
        try {
            if (!Files.exists(HISTORY_FILE)) {
                return; // nothing yet, first run
            }
            for (String line : Files.readAllLines(HISTORY_FILE)) {
                String t = line.trim().toUpperCase(Locale.ROOT);
                if (!t.isEmpty()) {
                    // Avoid duplicates while loading
                    boolean exists = false;
                    for (int i = 0; i < historyModel.getSize(); i++) {
                        if (t.equals(historyModel.getElementAt(i))) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        historyModel.addElement(t);
                    }
                }
            }
        } catch (IOException e) {
            // Don't crash the app because of history; just log
            System.err.println("Could not load history: " + e.getMessage());
        }
    }

//TODO for you to do these buttons implement your use cases for single stock
    private void saveHistoryToDisk() {
        try {
            int n = historyModel.getSize();
            java.util.List<String> lines = new java.util.ArrayList<>();
            for (int i = 0; i < n; i++) {
                lines.add(historyModel.getElementAt(i));
            }
            Files.write(HISTORY_FILE, lines);
        } catch (IOException e) {
            // Again, don't annoy user; just log
            System.err.println("Could not save history: " + e.getMessage());
        }
    }
    private void onScenarioClicked(ActionEvent e) {
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

        if (controller == null) {
            showError("Controller not set.");
            return;
        }

        // TODO: implement this in controller for scenario ALEX!
        controller.runScenario(tkr, rf);
    }

    private void onMonteCarloClicked(ActionEvent e) {
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

        if (controller == null) {
            showError("Controller not set.");
            return;
        }

        // TODO:implement this in controller for monte carlo ALI!
        controller.runMonteCarlo(tkr, rf);
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

        viewModel.setCurrentTicker(tkr);
        viewModel.setCurrentRiskFree(rfField.getText().trim());
        addToHistory(tkr);
    } catch (RuntimeException ex) {
        showError(ex.getMessage());
        infoArea.setText("");
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
            if (controller == null) {
                showError("Controller not set.");
                return;
            }
            controller.compare(baseTicker, other, rf);

            addToHistory(baseTicker);
            addToHistory(other);
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
            FredRiskFreeRateDataAccess fred =
                    new FredRiskFreeRateDataAccess(key);

            double rf = fred.getCurrentRiskFreeRate();
            String rfTxt = String.format(Locale.US, "%.4f", rf);
            rfField.setText(rfTxt);
            viewModel.setFredApiKey(key);
            viewModel.setCurrentRiskFree(rfTxt);
            JOptionPane.showMessageDialog(this,
                    String.format(Locale.US, "Loaded risk-free: %.2f%%", rf * 100),
                    "FRED", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException ex) {
            showError("FRED error: " + ex.getMessage());
        }
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
}
