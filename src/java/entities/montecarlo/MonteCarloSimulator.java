package entities.montecarlo;

import java.util.*;

public class MonteCarloSimulator {

    private final Random rng;

    public MonteCarloSimulator() {
        this.rng = new Random();
    }

    /**
     * Simulates multiple stock price paths using GBM.
     *
     * @param s0           initial stock price
     * @param muAnnual     expected annual return (e.g. 0.08 for 8%)
     * @param sigmaAnnual  annual volatility (e.g. 0.2 for 20%)
     * @param horizonYears number of years to simulate
     * @param nSteps       steps per path (e.g. 252 for daily)
     * @param nPaths       number of simulated paths
     * @return double[][] array of simulated prices [path][step]
     */
    public double[][] simulate(double s0, double muAnnual, double sigmaAnnual,
                               double horizonYears, int nSteps, int nPaths) {

        double dt = horizonYears / nSteps;
        double drift = (muAnnual - 0.5 * sigmaAnnual * sigmaAnnual) * dt;
        double volStep = sigmaAnnual * Math.sqrt(dt);

        double[][] paths = new double[nPaths][nSteps + 1];

        for (int i = 0; i < nPaths; i++) {
            double s = s0;
            paths[i][0] = s;

            for (int t = 1; t <= nSteps; t++) {
                double z = nextStandardNormal();
                s *= Math.exp(drift + volStep * z);
                paths[i][t] = s;
            }
        }
        return paths;
    }

    /**
     * Generates a single standard normal variable using the Box-Muller transform.
     */
    private double nextStandardNormal() {
        double u1 = rng.nextDouble();
        double u2 = rng.nextDouble();
        return Math.sqrt(-2.0 * Math.log(u1)) * Math.cos(2 * Math.PI * u2);
    }

    /**
     * Utility: Compute the mean of the terminal prices (expected simulated price).
     */
    public double meanTerminal(double[][] paths) {
        double sum = 0.0;
        for (double[] path : paths) {
            sum += path[path.length - 1];
        }
        return sum / paths.length;
    }

}
