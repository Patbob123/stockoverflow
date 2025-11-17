package view.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistogramPanel extends JPanel {
    private List<Double> data;
    private int bins = 40;

    public void setData(List<Double> data, int bins) {
        this.data = data;
        if (bins > 0) this.bins = bins;
        repaint();
    }

    @Override public Dimension getPreferredSize() { return new Dimension(900, 380); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data == null || data.isEmpty()) { g.drawString("No data.", 20, 20); return; }

        double min = data.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        double max = data.stream().mapToDouble(Double::doubleValue).max().orElse(1);
        if (min == max) max = min + 1e-6;
        double width = (max - min) / bins;
        int[] counts = new int[bins];
        for (double v : data) {
            int i = (int)Math.floor((v - min) / width);
            if (i < 0) i = 0; else if (i >= bins) i = bins - 1;
            counts[i]++;
        }
        int maxCount = 0; for (int c : counts) maxCount = Math.max(maxCount, c);

        Insets pad = new Insets(20, 40, 40, 20);
        int W = getWidth(), H = getHeight();
        int x0 = pad.left, y0 = pad.top, w = W - pad.left - pad.right, h = H - pad.top - pad.bottom;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.DARK_GRAY); g2.drawRect(x0, y0, w, h);

        int barW = (int)Math.max(1, w / (double)bins);
        for (int i = 0; i < bins; i++) {
            int barH = (int)Math.round(counts[i] / (double)maxCount * h);
            int x = x0 + i * barW, y = y0 + h - barH;
            g2.setColor(new Color(180, 180, 250));
            g2.fillRect(x, y, (int)(barW * 0.98), barH);
        }
        g2.setColor(Color.BLACK);
        g2.drawString(String.format("%.2f%%", min * 100), x0, y0 + h + 15);
        g2.drawString(String.format("%.2f%%", max * 100), x0 + w - 60, y0 + h + 15);
        g2.dispose();
    }
}
