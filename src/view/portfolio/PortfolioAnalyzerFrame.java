package view.portfolio;

import data_access.CsvPriceDataGateway;
import entities.HistoricalSimulator;
import entities.PricePoint;
import view.components.HistogramPanel;
import view.components.MultiLineChartPanel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.io.IOException;
import java.util.List;

/**
 * Swing frame for portfolio analysis.
 * Tab 1: plot normalized price series for multiple tickers.
 * Tab 2: run a historical portfolio simulation and show statistics + return distribution.
 */
public class PortfolioAnalyzerFrame extends JFrame {
    private final JTextField tickersField = new JTextField("AAPL,MSFT,GOOG", 28);
    private final JTextField startField = new JTextField("2019-01-01", 10);
    private final JTextField endField = new JTextField(LocalDate.now().toString(), 10);
    private final JTextField weightsField = new JTextField("", 25);

    private final JLabel status = new JLabel(" ");
    private final MultiLineChartPanel chart = new MultiLineChartPanel();
    private final HistogramPanel hist = new HistogramPanel();
    private final CsvPriceDataGateway gateway = new CsvPriceDataGateway("data");

    public PortfolioAnalyzerFrame() {
        super("Portfolio Analyzer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();

        // Chart tab
        JPanel chartTab = new JPanel(new BorderLayout());
        chartTab.add(buildTopBarForChart(), BorderLayout.NORTH);
        chartTab.add(chart, BorderLayout.CENTER);
        chartTab.add(status, BorderLayout.SOUTH);
        tabs.addTab("Chart", chartTab);

        // Historical simulation tab
        JPanel simTab = new JPanel(new BorderLayout());
        simTab.add(buildTopBarForSim(), BorderLayout.NORTH);
        simTab.add(hist, BorderLayout.CENTER);
        tabs.addTab("Historical", simTab);

        add(tabs, BorderLayout.CENTER);
        pack();
        setSize(980, 640);
        setLocationRelativeTo(null);
    }


    private JPanel buildTopBarForChart() {
        JPanel p = new JPanel();
        p.add(new JLabel("Tickers:"));
        p.add(tickersField);
        p.add(new JLabel("Start (yyyy-MM-dd):"));
        p.add(startField);
        p.add(new JLabel("End:"));
        p.add(endField);

        JButton load = new JButton("Generate Graph");
        load.addActionListener(evt -> onLoadClicked());
        p.add(load);

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(evt -> onLoadClicked());
        p.add(refresh);

        return p;
    }


    private JPanel buildTopBarForSim() {
        JPanel p = new JPanel();
        p.add(new JLabel("Tickers:"));
        p.add(tickersField);
        p.add(new JLabel("Weights:"));
        weightsField.setToolTipText("");
        p.add(weightsField);

        JButton analyze = new JButton("Analyze Historical Returns");
        analyze.addActionListener(evt -> onAnalyzeClicked());
        p.add(analyze);

        return p;
    }


    private void onLoadClicked() {
        try {
            // 1. Load raw closing prices
            Map<String, List<PricePoint>> raw = loadSeries();
            // 2. Normalize each series so the first day value is 100
            Map<String, List<PricePoint>> normalized = normalizeTo100(raw);
            // 3. Pass data into the chart panel
            chart.setSeries(normalized);
            status.setText("Loaded " + normalized.size() + " series.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Load failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAnalyzeClicked() {
        try {
            Map<String, List<PricePoint>> series = loadSeries();
            Map<String, Double> weights = parseWeights(series.keySet());
            HistoricalSimulator.Result res = HistoricalSimulator.analyze(series, weights);

            String msg = String.format(
                    "Annualized return: %.2f%% | Volatility: %.2f%% | Cumulative: %.2f%% | MaxDD: %.2f%% | 1-day VaR95: %.2f%% | CVaR95: %.2f%%",
                    res.annualReturn * 100, res.annualVol * 100, res.cumulativeReturn * 100,
                    res.maxDrawdown * 100, res.var95 * 100, res.cvar95 * 100
            );
            JOptionPane.showMessageDialog(this, msg, "Historical stats", JOptionPane.INFORMATION_MESSAGE);

            // dailyReturns is assumed to be log returns; convert to simple returns for the histogram
            hist.setData(toSimpleReturns(res.dailyReturns), 40);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Analyze failed", JOptionPane.ERROR_MESSAGE);
        }
    }


    private Map<String, List<PricePoint>> loadSeries() throws IOException {
        String[] tickers = tickersField.getText().trim().toUpperCase().split("\\s*,\\s*");
        LocalDate start = parseDate(startField.getText().trim());
        LocalDate end = parseDate(endField.getText().trim());
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date is before start date.");
        }

        Map<String, List<PricePoint>> series = new LinkedHashMap<>();
        List<String> failed = new ArrayList<>();

        for (String t : tickers) {
            if (t.isBlank()) continue;
            try {
                List<PricePoint> s = gateway.load(t, start, end);
                if (s.size() < 2) {
                    failed.add(t);
                } else {
                    series.put(t, s);
                }
            } catch (IOException ex) {
                failed.add(t);
            }
        }

        if (!failed.isEmpty() && !series.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "The following tickers have missing or insufficient data and were ignored: " + String.join(", ", failed),
                    "Partial failure",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        if (series.isEmpty()) {
            throw new IOException("No usable data found. Please put <TICKER>.csv files (Yahoo format) into the /data directory.");
        }
        return series;
    }

    private static LocalDate parseDate(String text) {
        try {
            return LocalDate.parse(text);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date: " + text + " (expected yyyy-MM-dd).");
        }
    }


    private Map<String, Double> parseWeights(Set<String> tickers) {
        String w = weightsField.getText().trim();
        if (w.isBlank()) {
            return null; // use equal-weight portfolio; HistoricalSimulator will handle this
        }

        String[] parts = w.split("\\s*,\\s*");
        if (parts.length != tickers.size()) {
            throw new IllegalArgumentException("Number of weights must match number of tickers.");
        }

        Map<String, Double> map = new LinkedHashMap<>();
        int i = 0;
        for (String t : tickers) {
            map.put(t, Double.parseDouble(parts[i++]));
        }
        return map;
    }


    private static Map<String, List<PricePoint>> normalizeTo100(Map<String, List<PricePoint>> raw) {
        Map<String, List<PricePoint>> out = new LinkedHashMap<>();
        for (Map.Entry<String, List<PricePoint>> e : raw.entrySet()) {
            List<PricePoint> src = e.getValue();
            double base = src.get(0).getClose();
            List<PricePoint> dst = new ArrayList<>(src.size());
            for (PricePoint p : src) {
                double v = p.getClose() / base * 100.0; // normalize so the first day equals 100
                dst.add(new PricePoint(p.getDate(), v));
            }
            out.put(e.getKey(), dst);
        }
        return out;
    }


    private static List<Double> toSimpleReturns(List<Double> logReturns) {
        List<Double> s = new ArrayList<>(logReturns.size());
        for (double x : logReturns) {
            s.add(Math.expm1(x)); // convert log return to simple return (exp(x) - 1)
        }
        return s;
    }
}
