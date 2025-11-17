package view.components;

import entities.PricePoint;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class MultiLineChartPanel extends JPanel {
    private Map<String, List<PricePoint>> series;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final List<Color> palette = List.of(
            Color.decode("#1f77b4"), Color.decode("#ff7f0e"), Color.decode("#2ca02c"),
            Color.decode("#d62728"), Color.decode("#9467bd"), Color.decode("#8c564b"),
            Color.decode("#e377c2"), Color.decode("#7f7f7f"), Color.decode("#bcbd22"),
            Color.decode("#17becf")
    );

    public void setSeries(Map<String, List<PricePoint>> series) {
        this.series = series;
        repaint();
    }

    @Override public Dimension getPreferredSize() { return new Dimension(900, 500); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (series == null || series.isEmpty()) { g.drawString("No data loaded.", 20, 20); return; }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Insets pad = new Insets(40, 70, 50, 20);
        int W = getWidth(), H = getHeight();
        int x0 = pad.left, y0 = pad.top, w = W - pad.left - pad.right, h = H - pad.top - pad.bottom;

        // min/max
        double min = Double.POSITIVE_INFINITY, max = Double.NEGATIVE_INFINITY;
        int M = 0;
        for (var e : series.entrySet()) {
            for (PricePoint p : e.getValue()) { min = Math.min(min, p.getClose()); max = Math.max(max, p.getClose()); }
            M = Math.max(M, e.getValue().size());
        }
        if (min == max) max = min + 1e-6;

        // edge
        g2.setColor(Color.DARK_GRAY); g2.drawRect(x0, y0, w, h);
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(new Color(220,220,220));
        for (int i = 1; i < 5; i++) g2.drawLine(x0, y0 + i*h/5, x0 + w, y0 + i*h/5);

        // Y
        g2.setColor(Color.BLACK);
        for (int i = 0; i <= 5; i++) {
            double v = min + (max - min) * i / 5.0;
            int y = y0 + h - (int)Math.round((v - min)/(max - min)*h);
            g2.drawString(String.format("%.2f", v), x0 - 55, y + 4);
        }

        // X
        var any = series.values().iterator().next();
        int ticks = 6;
        for (int i = 0; i < ticks; i++) {
            int idx = (int)Math.round(i * (any.size() - 1) / (double)(ticks - 1));
            int x = x0 + (int)Math.round(idx * (w / (double)(any.size() - 1)));
            g2.setColor(new Color(220,220,220)); g2.drawLine(x, y0, x, y0 + h);
            g2.setColor(Color.BLACK); g2.drawString(any.get(idx).getDate().format(fmt), x - 25, y0 + h + 20);
        }

        // draw line + graph
        int colorIdx = 0, legendX = x0 + 10, legendY = y0 + 15;
        for (var e : series.entrySet()) {
            Color c = palette.get(colorIdx++ % palette.size());
            var pts = e.getValue();
            g2.setColor(c);
            Path2D path = new Path2D.Double();
            for (int i = 0; i < pts.size(); i++) {
                int x = x0 + (int)Math.round(i * (w / (double)(any.size() - 1)));
                int y = y0 + h - (int)Math.round((pts.get(i).getClose() - min)/(max - min)*h);
                if (i == 0) path.moveTo(x,y); else path.lineTo(x,y);
            }
            g2.setStroke(new BasicStroke(2f)); g2.draw(path);
            g2.fillRect(legendX, legendY - 8, 20, 8);
            g2.setColor(Color.BLACK); g2.drawString(e.getKey(), legendX + 26, legendY); legendY += 16;
        }
        g2.dispose();
    }
}
