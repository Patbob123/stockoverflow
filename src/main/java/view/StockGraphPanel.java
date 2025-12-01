package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * A custom JPanel that draws stock price lines using Java 2D Graphics.
 * Used in ShowGraphView to visualize market data.
 */
public class StockGraphPanel extends JPanel {

    private Map<String, Map<LocalDate, Double>> datasets = new HashMap<>();
    private final Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA};

    private final int padding = 50;
    private final int labelPadding = 25;

    /**
     * Updates the datasets to be drawn and triggers a repaint.
     * @param datasets A map where Key is Ticker, and Value is a Map of Date->Price.
     */
    public void setDatasets(Map<String, Map<LocalDate, Double>> datasets) {
        this.datasets = datasets;
        this.repaint(); // Trigger a redraw
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw Background
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (datasets == null || datasets.isEmpty()) {
            g2.drawString("No data to display. Please select stocks and click Plot.", getWidth() / 2 - 120, getHeight() / 2);
            return;
        }

        // 1. Determine Min/Max for Scaling
        double minPrice = Double.MAX_VALUE;
        double maxPrice = Double.MIN_VALUE;
        LocalDate minDate = LocalDate.MAX;
        LocalDate maxDate = LocalDate.MIN;

        for (Map<LocalDate, Double> data : datasets.values()) {
            for (Map.Entry<LocalDate, Double> entry : data.entrySet()) {
                if (entry.getValue() < minPrice) minPrice = entry.getValue();
                if (entry.getValue() > maxPrice) maxPrice = entry.getValue();
                if (entry.getKey().isBefore(minDate)) minDate = entry.getKey();
                if (entry.getKey().isAfter(maxDate)) maxDate = entry.getKey();
            }
        }

        // Prevent division by zero for flat lines
        if (maxPrice == minPrice) {
            maxPrice += 1.0;
            minPrice -= 1.0;
        }

        // 2. Calculate Scales
        long totalDays = java.time.temporal.ChronoUnit.DAYS.between(minDate, maxDate);
        if (totalDays == 0) totalDays = 1;

        double xScale = (double) (getWidth() - 2 * padding - labelPadding) / totalDays;
        double yScale = (double) (getHeight() - 2 * padding - labelPadding) / (maxPrice - minPrice);

        // 3. Draw Axes
        g2.setColor(Color.BLACK);
        // Y-Axis
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        // X-Axis
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        // 4. Draw Y-Axis Labels (Prices)
        for (int i = 0; i < 5; i++) {
            int y = getHeight() - padding - labelPadding - (int) (i * (getHeight() - 2 * padding - labelPadding) / 4.0);
            double price = minPrice + (maxPrice - minPrice) * i / 4.0;
            String label = String.format("%.2f", price);
            g2.drawString(label, padding - 25, y + 5);
            g2.drawLine(padding + labelPadding - 5, y, padding + labelPadding + 5, y);
        }

        // 5. Draw Legend
        int colorIndex = 0;
        int legendY = padding;
        for (String ticker : datasets.keySet()) {
            g2.setColor(colors[colorIndex % colors.length]);
            g2.drawString("■ " + ticker, getWidth() - padding - 80, legendY);
            legendY += 20;
            colorIndex++;
        }

        // 6. Draw Lines
        colorIndex = 0;
        for (Map.Entry<String, Map<LocalDate, Double>> entry : datasets.entrySet()) {
            Map<LocalDate, Double> history = entry.getValue();
            g2.setColor(colors[colorIndex % colors.length]);
            colorIndex++;

            List<Point> graphPoints = new ArrayList<>();
            for (Map.Entry<LocalDate, Double> point : history.entrySet()) {
                long daysFromStart = java.time.temporal.ChronoUnit.DAYS.between(minDate, point.getKey());
                int x1 = (int) (padding + labelPadding + daysFromStart * xScale);
                int y1 = (int) (getHeight() - padding - labelPadding - (point.getValue() - minPrice) * yScale);
                graphPoints.add(new Point(x1, y1));
            }

            // Draw polyline connecting points
            if (graphPoints.size() > 1) {
                for (int i = 0; i < graphPoints.size() - 1; i++) {
                    int x1 = graphPoints.get(i).x;
                    int y1 = graphPoints.get(i).y;
                    int x2 = graphPoints.get(i + 1).x;
                    int y2 = graphPoints.get(i + 1).y;
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawLine(x1, y1, x2, y2);
                }
            } else if (graphPoints.size() == 1) {
                g2.fillOval(graphPoints.get(0).x - 2, graphPoints.get(0).y - 2, 4, 4);
            }
        }
    }
}