package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * A custom panel to draw stock price lines.
 */
public class GraphPanel extends JPanel {
    private Map<String, Map<LocalDate, Double>> data;
    private final Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA};

    public GraphPanel() {
        this.setPreferredSize(new Dimension(800, 400));
        this.setBackground(Color.WHITE);
        this.data = new HashMap<>();
    }

    public void updateData(Map<String, Map<LocalDate, Double>> newData) {
        this.data = newData;
        this.repaint(); // Trigger paintComponent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data == null || data.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minPrice = Double.MAX_VALUE;
        double maxPrice = Double.MIN_VALUE;
        long minTime = Long.MAX_VALUE;
        long maxTime = Long.MIN_VALUE;

        // 1. Calculate min/max for scaling
        for (Map<LocalDate, Double> history : data.values()) {
            for (Map.Entry<LocalDate, Double> entry : history.entrySet()) {
                double price = entry.getValue();
                long time = entry.getKey().toEpochDay();

                if (price < minPrice) minPrice = price;
                if (price > maxPrice) maxPrice = price;
                if (time < minTime) minTime = time;
                if (time > maxTime) maxTime = time;
            }
        }

        // Add some padding to the range
        double priceRange = maxPrice - minPrice;
        long timeRange = maxTime - minTime;
        if (priceRange == 0) priceRange = 1;
        if (timeRange == 0) timeRange = 1;

        int width = getWidth();
        int height = getHeight();
        int padding = 50;

        // Draw axes
        g2.drawLine(padding, height - padding, width - padding, height - padding); // X-axis
        g2.drawLine(padding, padding, padding, height - padding); // Y-axis

        // 2. Draw lines for each stock
        int colorIndex = 0;
        for (Map.Entry<String, Map<LocalDate, Double>> entry : data.entrySet()) {
            String ticker = entry.getKey();
            Map<LocalDate, Double> history = entry.getValue();
            g2.setColor(colors[colorIndex % colors.length]);
            colorIndex++;

            Point lastPoint = null;

            // Sort dates to draw lines in order
            List<LocalDate> sortedDates = new ArrayList<>(history.keySet());
            Collections.sort(sortedDates);

            for (LocalDate date : sortedDates) {
                double price = history.get(date);
                long time = date.toEpochDay();

                // Map time -> x, price -> y
                int x = padding + (int) ((time - minTime) * (width - 2 * padding) / timeRange);
                int y = height - padding - (int) ((price - minPrice) * (height - 2 * padding) / priceRange);

                if (lastPoint != null) {
                    g2.draw(new Line2D.Double(lastPoint.x, lastPoint.y, x, y));
                }
                lastPoint = new Point(x, y);
            }

            // Draw Legend
            g2.drawString(ticker, width - padding - 50, padding + (colorIndex * 20));
        }
    }
}