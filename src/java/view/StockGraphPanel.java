package view;

import entities.Stock;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * A custom JFrame to display the stock graph.
 */
public class StockGraphPanel extends JFrame {
    private final List<Stock> stocks;

    public StockGraphPanel(List<Stock> stocks) {
        this.stocks = stocks;
        this.setTitle("Stock Price History - User Story 5");
        this.setSize(800, 600);
        // Dispose on close so it doesn't kill the main app
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center on screen

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                int padding = 50;

                // Draw Axis
                g2.setColor(Color.BLACK);
                g2.drawLine(padding, h - padding, w - padding, h - padding); // X-axis
                g2.drawLine(padding, h - padding, padding, padding);         // Y-axis

                if (stocks == null || stocks.isEmpty()) {
                    g2.drawString("No Data to Display", w / 2, h / 2);
                    return;
                }

                // Determine global min/max to scale graph
                double maxPrice = Double.MIN_VALUE;
                double minPrice = Double.MAX_VALUE;

                for (Stock s : stocks) {
                    if (s.getHistoricalPrices() != null) {
                        for (double p : s.getHistoricalPrices().values()) {
                            if (p > maxPrice) maxPrice = p;
                            if (p < minPrice) minPrice = p;
                        }
                    }
                }

                // Add some buffer to scale
                if (maxPrice == minPrice) { maxPrice += 10; minPrice -= 10; }
                double range = maxPrice - minPrice;

                Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA};
                int colorIndex = 0;
                int legendY = padding;

                // Draw lines
                for (Stock s : stocks) {
                    if (s.getHistoricalPrices() == null || s.getHistoricalPrices().isEmpty()) continue;

                    g2.setColor(colors[colorIndex % colors.length]);

                    Map<LocalDate, Double> history = s.getHistoricalPrices();
                    Object[] dates = history.keySet().toArray();
                    int points = dates.length;

                    int prevX = -1;
                    int prevY = -1;

                    for (int i = 0; i < points; i++) {
                        double price = history.get(dates[i]);

                        // Scale X and Y
                        int x = padding + (i * (w - 2 * padding) / (Math.max(1, points - 1)));
                        int y = h - padding - (int) (((price - minPrice) / range) * (h - 2 * padding));

                        if (i > 0) {
                            g2.setStroke(new BasicStroke(2));
                            g2.drawLine(prevX, prevY, x, y);
                        }
                        g2.fillOval(x - 3, y - 3, 6, 6);
                        prevX = x;
                        prevY = y;
                    }

                    // Draw Legend
                    g2.drawString(s.getTicker(), w - 100, legendY);
                    legendY += 20;
                    colorIndex++;
                }
            }
        };

        panel.setBackground(Color.WHITE);
        this.add(panel);
        this.setVisible(true);
    }
}