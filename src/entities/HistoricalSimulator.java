package entities;

import java.time.LocalDate;
import java.util.*;

public class HistoricalSimulator {

    public static class Result {
        public final List<Double> dailyReturns;
        public final double meanDaily;
        public final double stdDaily;
        public final double annualReturn;
        public final double annualVol;
        public final double cumulativeReturn;
        public final double maxDrawdown;
        public final double var95;
        public final double cvar95;

        public Result(List<Double> dailyReturns, double meanDaily, double stdDaily, double annualReturn,
                      double annualVol, double cumulativeReturn, double maxDrawdown, double var95, double cvar95) {
            this.dailyReturns = dailyReturns;
            this.meanDaily = meanDaily;
            this.stdDaily = stdDaily;
            this.annualReturn = annualReturn;
            this.annualVol = annualVol;
            this.cumulativeReturn = cumulativeReturn;
            this.maxDrawdown = maxDrawdown;
            this.var95 = var95;
            this.cvar95 = cvar95;
        }
    }

    public static Result analyze(Map<String, List<PricePoint>> series, Map<String, Double> weights) {
        if (series.isEmpty()) throw new IllegalArgumentException("Series is empty");

        if (weights == null || weights.isEmpty()) {
            double eq = 1.0 / series.size();
            weights = new LinkedHashMap<>();
            for (String t : series.keySet()) weights.put(t, eq);
        }
        double sumW = 0;
        for (double w : weights.values()) sumW += w;
        if (Math.abs(sumW - 1.0) > 1e-6) {
            for (String k : new ArrayList<>(weights.keySet())) {
                weights.put(k, weights.get(k) / sumW);
            }
        }
        List<LocalDate> dates = alignDates(series);
        int n = dates.size();
        if (n < 60) throw new IllegalArgumentException("Series contains less than 60 days");

        Map<String, double[]> logRets = new LinkedHashMap<>();
        for (Map.Entry<String, List<PricePoint>> e : series.entrySet()) {
            double[] p = extractPricesAligned(e.getValue(), dates);
            double[] r = new double[n - 1];
            for (int i = 1; i < n; i++) r[i - 1] = Math.log(p[i] / p[i - 1]);
            logRets.put(e.getKey(), r);
        }

        int m = n - 1;
        List<Double> port = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            double s = 0;
            for (String t : logRets.keySet()) s += weights.getOrDefault(t, 0.0) * logRets.get(t)[i];
            port.add(s);
        }
        double mean = mean(port);
        double sd   = stddev(port, mean);
        double annualRet = Math.expm1(mean * 252.0);
        double annualVol = sd * Math.sqrt(252.0);
        double cum = Math.expm1(port.stream().mapToDouble(Double::doubleValue).sum());

        double peak = 1.0, nav = 1.0, maxDD = 0.0;
        for (double x : port) {
            nav *= Math.exp(x);
            if (nav > peak) peak = nav;
            maxDD = Math.max(maxDD, 1.0 - nav / peak);
        }

        List<Double> simpleR = new ArrayList<>(port.size());
        for (double x : port) simpleR.add(Math.expm1(x));
        double q5 = quantile(simpleR, 0.05);
        double var95  = -q5;
        double cvar95 = -simpleR.stream().filter(v -> v <= q5).mapToDouble(Double::doubleValue).average().orElse(Double.NaN);

        return new Result(port, mean, sd, annualRet, annualVol, cum, maxDD, var95, cvar95);
    }

    // ----- helpers -----
    private static List<LocalDate> alignDates(Map<String, List<PricePoint>> series) {
        Set<LocalDate> set = null;
        for (List<PricePoint> list : series.values()) {
            Set<LocalDate> s = new HashSet<>();
            for (PricePoint p : list) s.add(p.getDate());
            if (set == null) set = s; else set.retainAll(s);
        }
        List<LocalDate> res = new ArrayList<>(set);
        Collections.sort(res);
        return res;
    }

    private static double[] extractPricesAligned(List<PricePoint> list, List<LocalDate> dates) {
        Map<LocalDate, Double> map = new HashMap<>();
        for (PricePoint p : list) map.put(p.getDate(), p.getClose());
        double[] arr = new double[dates.size()];
        for (int i = 0; i < dates.size(); i++) {
            Double v = map.get(dates.get(i));
            if (v == null) throw new IllegalStateException("no dates data: " + dates.get(i));
            arr[i] = v;
        }
        return arr;
    }

    private static double mean(List<Double> xs) {
        double s = 0; for (double x : xs) s += x; return s / xs.size();
    }
    private static double stddev(List<Double> xs, double mean) {
        double s = 0; for (double x : xs) { double d = x - mean; s += d*d; }
        return Math.sqrt(s / Math.max(1, xs.size()-1));
    }
    private static double quantile(List<Double> xs, double q) {
        double[] a = xs.stream().mapToDouble(Double::doubleValue).sorted().toArray();
        double pos = q * (a.length - 1);
        int lo = (int) Math.floor(pos), hi = (int) Math.ceil(pos);
        if (lo == hi) return a[lo];
        double w = pos - lo;
        return a[lo]*(1-w) + a[hi]*w;
    }
}
}
