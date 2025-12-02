package view;

import data_access.FileMonteCarloDataAccess;
import data_access.FredRiskFreeRateDataAccess;
import data_access.StooqStockDataAccess;
import entities.StatisticsCalculator;
import entities.monte_carlo.MonteCarloSimulator;
import interface_adapter.import_export.ImportExportViewModel;
import interface_adapter.monte_carlo.MonteCarloController;
import interface_adapter.monte_carlo.MonteCarloPresenter;
import interface_adapter.show_graph.ShowGraphController;
import interface_adapter.singlestock.SingleStockController;
import interface_adapter.singlestock.SingleStockViewInterface;
import interface_adapter.singlestock.SingleStockViewModel;
import interface_adapter.singlestock.SingleStockController;
import lombok.Setter;
import use_case.monte_carlo.MonteCarloAnalysisInteractor;
import use_case.monte_carlo.MonteCarloOutputBoundary;
import use_case.singlestock.AnalyzeSingleStockOutputData;
import view.monte_carlo.MonteCarloInputPanel;
import view.monte_carlo.SwingMonteCarloView;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class SingleStockView extends PaddedView<SingleStockViewModel, SingleStockController> implements SingleStockViewInterface {

    public static final String VIEW_NAME = "SingleStockMenu";

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
    private final JButton graphBtn = new JButton("Show Graph");
    private final JButton analyzeBtn;
    private final JButton FredApiBtn;
    private final JButton CompareBtn;
    private final JButton ScenarioBtn;
    private final JButton MonteCarloBtn;
    private final JButton MonteCarloHistoryBtn;
    private final JButton ImportBtn;
    private final JButton backButton = createTextButton(ImportExportViewModel.BACK_BUTTON_LABEL);

    //now lets add history

    private final DefaultComboBoxModel<String> historyModel = new DefaultComboBoxModel<>();
    private final JComboBox<String> historyBox = new JComboBox<>(historyModel);


    private final JTextArea infoArea = new JTextArea(18, 48);

    @Setter
    private ShowGraphController showGraphController;

    public SingleStockView(SingleStockViewModel viewModel) {
        super(viewModel);
        this.viewModel = viewModel;


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
        this.analyzeBtn = new JButton(SingleStockViewModel.BUTTON_ANALYZE);
        this.FredApiBtn = new JButton(SingleStockViewModel.BUTTON_FRED);
        this.CompareBtn = new JButton(SingleStockViewModel.BUTTON_COMPARE);
        this.ScenarioBtn = new JButton(SingleStockViewModel.BUTTON_SCENARIO);
        this.MonteCarloBtn = new JButton(SingleStockViewModel.BUTTON_MONTECARLO);
        this.MonteCarloHistoryBtn = new JButton(SingleStockViewModel.BUTTON_MONTECARLOHISTORY);
        this.ImportBtn = new JButton(SingleStockViewModel.BUTTON_IMPORT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(screenSize);
        setLayout(new BorderLayout());

        JPanel inputs = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        g.gridx = 0;
        g.gridy = y;
        inputs.add(new JLabel(SingleStockViewModel.LABEL_TICKER), g);
        g.gridx = 1;
        g.gridy = y++;
        inputs.add(tickerField, g);

        g.gridx = 0;
        g.gridy = y;
        inputs.add(new JLabel(SingleStockViewModel.LABEL_HISTORY), g);
        g.gridx = 1;
        g.gridy = y++;
        historyBox.setPrototypeDisplayValue("XXXXXXXXXX");
        inputs.add(historyBox, g);

        g.gridx = 0;
        g.gridy = y;
        inputs.add(new JLabel(SingleStockViewModel.LABEL_FRED_API), g);
        g.gridx = 1;
        g.gridy = y++;
        inputs.add(FredAPI, g);

        g.gridx = 0;
        g.gridy = y;
        inputs.add(new JLabel(SingleStockViewModel.LABEL_RISK_FREE), g);
        g.gridx = 1;
        g.gridy = y++;
        inputs.add(rfField, g);


        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttons.add(analyzeBtn);
        buttons.add(graphBtn);
        buttons.add(FredApiBtn);//add later done
        buttons.add(CompareBtn);
        buttons.add(ScenarioBtn);
        buttons.add(MonteCarloBtn);
        buttons.add(MonteCarloHistoryBtn);
        buttons.add(ImportBtn);


        g.gridx = 0;
        g.gridy = y;
        g.gridwidth = 2;
        inputs.add(buttons, g);


        infoArea.setEditable(false);
        infoArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane infoScroll = new JScrollPane(infoArea);

        add(inputs, BorderLayout.CENTER);
        add(infoScroll, BorderLayout.SOUTH);

        final JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(SingleStockViewModel.CARD_COLOUR);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, SingleStockViewModel.BORDER_COLOUR),
                new EmptyBorder(20, 30, 20, 30)
        ));

        topPanel.add(backButton, BorderLayout.WEST);

        final JLabel title = new JLabel(SingleStockViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setFont(SingleStockViewModel.TITLE_FONT);
        title.setForeground(SingleStockViewModel.TEXT_PRIMARY);
        topPanel.add(title, BorderLayout.CENTER);

        final JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(100, 0));
        topPanel.add(spacer, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        backButton.addActionListener(evt -> {
            this.getChangeViewController().backView();
        });

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
        graphBtn.addActionListener(this::onGraphClicked);
        //connecting fred api to the button compare is added
        FredApiBtn.addActionListener(this::onFredApiClicked);
        CompareBtn.addActionListener(this::onCompareClicked);
        ScenarioBtn.addActionListener(this::onScenarioClicked);
        MonteCarloBtn.addActionListener(this::onMonteCarloClicked);
        MonteCarloHistoryBtn.addActionListener(this::onMonteCarloHistoryClicked);
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

        if (this.getController() == null) {
            showError("Controller not set.");
            return;
        }

        // TODO: implement this in controller for scenario ALEX!
        this.getController().runScenario(tkr, rf);
    }

    private void onMonteCarloClicked(ActionEvent e) {
        String tkr = tickerField.getText().trim().toUpperCase(Locale.ROOT);

        if (!tkr.matches("[A-Z0-9.]{1,10}")) {
            showError("Invalid base ticker format.");
            return;
        }
        JFrame frame = new JFrame("Monte Carlo Simulation Input for " + tkr);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SwingMonteCarloView view = new SwingMonteCarloView();
        MonteCarloAnalysisInteractor interactor = new MonteCarloAnalysisInteractor(new StooqStockDataAccess(),
                new MonteCarloSimulator(),
                new StatisticsCalculator(),
                new MonteCarloPresenter(view),
                new FileMonteCarloDataAccess());
        MonteCarloInputPanel panel = new MonteCarloInputPanel(tkr, new MonteCarloController(interactor));

        Window parent = SwingUtilities.getWindowAncestor(this);

        // Use JDialog to ensure it is modal and closes correctly
        JDialog dialog = new JDialog(
                (parent instanceof Frame) ? (Frame) parent : null, // Cast parent to Frame if possible
                "Monte Carlo Simulation Input for " + tkr,
                Dialog.ModalityType.APPLICATION_MODAL // Blocks interaction with parent until dismissed
        );

        // --- CLOSING BUG FIX ---
        // This tells the dialog to just close and dispose of itself, NOT the entire application.
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // 4. Display the Dialog
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);


    }

    //when analyze button
    private void onAnalyzeClicked(ActionEvent e) {  //ERROR HANDLE
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
            if (this.getController() == null) {
                showError("Controller not set.");
                return;
            }
            this.getController().analyze(tkr, rf);

            viewModel.setCurrentTicker(tkr);
            viewModel.setCurrentRiskFree(rfField.getText().trim());
            addToHistory(tkr);
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
            infoArea.setText("");
        }
    }



private void onGraphClicked(ActionEvent e) {
    String tkr = tickerField.getText().trim().toUpperCase(Locale.ROOT);
    if (!tkr.matches("[A-Z0-9.]{1,10}")) {
        showError("Invalid ticker format.");
        return;
    }

    infoArea.setText("generating graph for" + tkr + "...");

    try {
        if (showGraphController != null) {
            showGraphController.execute(tkr, VIEW_NAME);
        } else if (this.getController() != null) {
            this.getController().showGraph(tkr);
        } else {
            showError("Controller not set.");
        }
        this.getChangeViewController().changeView(ShowGraphView.VIEW_NAME);
    } catch (RuntimeException ex) {
        showError("Graph error: " + ex.getMessage());
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
        if (this.getController() == null) {
            showError("Controller not set.");
            return;
        }
        this.getController().compare(baseTicker, other, rf);

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

private void onMonteCarloHistoryClicked(ActionEvent e) {
    String tkr = tickerField.getText().trim().toUpperCase(Locale.ROOT);

    // 1. Ticker Validation
    if (!tkr.matches("[A-Z0-9.]{1,10}")) {
        showError("Invalid ticker format for history lookup.");
        return;
    }

    SwingMonteCarloView view = new SwingMonteCarloView();
    MonteCarloAnalysisInteractor interactor = new MonteCarloAnalysisInteractor(new StooqStockDataAccess(),
            new MonteCarloSimulator(),
            new StatisticsCalculator(),
            new MonteCarloPresenter(view),
            new FileMonteCarloDataAccess());
    MonteCarloController controller = new MonteCarloController(interactor);
    MonteCarloInputPanel panel = new MonteCarloInputPanel(tkr, controller);

    try {
        controller.showHistory(tkr);
    } catch (Exception ex) {
        // Catch any unexpected runtime errors from the retrieval process
        showError("Failed to initiate history retrieval: " + ex.getMessage());
    }
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

private JButton createTextButton(String text) {
    final JButton button = new JButton(text);
    button.setFont(SingleStockViewModel.BUTTON_SECONDARY_FONT);
    button.setForeground(SingleStockViewModel.TEXT_PRIMARY);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setContentAreaFilled(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    final Color originalColor = button.getForeground();

    button.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setForeground(SingleStockViewModel.TEXT_SECONDARY);
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setForeground(originalColor);
        }
    });
    return button;
}
}
