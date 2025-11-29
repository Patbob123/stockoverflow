package view;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.xy.*;


import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MonteCarloChartView {

    // ---------- PUBLIC API ----------

    /** Show N sample paths in a window. */
    public static void showPaths(double[][] paths, int nToShow, String title) {
        JFreeChart chart = buildPathsChart(paths, nToShow, title);
        showInFrame(chart, 900, 600);
    }

    // ---------- CHART BUILDERS ----------

    private static JFreeChart buildPathsChart(double[][] paths, int nToShow, String title) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        int show = Math.min(nToShow, paths.length);

        for (int i = 0; i < show; i++) {
            XYSeries s = new XYSeries("Path " + (i + 1));
            double[] path = paths[i];
            for (int t = 0; t < path.length; t++) s.add(t, path[t]);
            dataset.addSeries(s);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                title != null ? title : "Monte Carlo Simulation Paths",
                "Time Step (days)",
                "Price",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer r = new XYLineAndShapeRenderer(true, false);
        r.setDefaultStroke(new BasicStroke(1.0f));
        plot.setRenderer(r);
        return chart;
    }


    /** Save the paths chart as a PNG image file. */
    public static void savePathsChartPng(double[][] paths, int nToShow, String title,
                                         String filePath, int width, int height) throws IOException {
        JFreeChart chart = buildPathsChart(paths, nToShow, title);
        ChartUtils.saveChartAsPNG(new File(filePath), chart, width, height);
    }


    // ---------- UTIL ----------

    private static void showInFrame(JFreeChart chart, int width, int height) {
        JFrame frame = new JFrame(chart.getTitle().getText());
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

