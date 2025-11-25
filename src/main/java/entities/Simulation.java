package entities;

import java.util.Arrays;

public class Simulation {
    private final String ticker;
    private final double[][] simulationPaths; // [pathIndex][timeStep]
    private final double meanTerminalPrice;
    private final int numberOfPaths;
    private final int timeHorizon;

    public Simulation(String ticker, double[][] simulationPaths, double meanTerminalPrice, int numberOfPaths, int timeHorizon) {
        this.ticker = ticker;
        this.simulationPaths = simulationPaths;
        this.meanTerminalPrice = meanTerminalPrice;
        this.numberOfPaths = numberOfPaths;
        this.timeHorizon = timeHorizon;
    }

    public String getTicker() {
        return ticker;
    }

    public double[][] getSimulationPaths() {
        return simulationPaths;
    }

    public double getMeanTerminalPrice() {
        return meanTerminalPrice;
    }

    public double[] getPath(int index) {
        if (index >= 0 && index < simulationPaths.length) {
            return simulationPaths[index];
        }
        return new double[0];
    }

    @Override
    public String toString() {
        return "Simulation Result for " + ticker + ": Mean Price = " + meanTerminalPrice;
    }
}