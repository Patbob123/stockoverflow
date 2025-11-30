package entities.monte_carlo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MonteCarloSimulation implements Serializable {

    // inputs
    @Getter
    private final String ticker;
    @Getter
    private final double horizonYears;
    @Getter
    private final int nPaths;
    @Getter
    private final int nSteps;
    @Getter
    private final double muAnnual;
    @Getter
    private final double sigmaAnnual;

    // results
    @Getter
    private final double expectedTerminalPrice;
    @Getter
    private final double[][] simulationPaths;

    private final LocalDateTime timestamp;
    @Getter
    @Setter
    private String id; // Unique ID, usually assigned by the data access layer (e.g., database key or file name)

    // Constructor to capture all essential data after a successful Interactor run
    public MonteCarloSimulation(String ticker, double horizonYears, int nPaths, int nSteps,
                                double muAnnual, double sigmaAnnual, double expectedTerminalPrice,
                                double[][] simulationPaths) {

        this.ticker = ticker;
        this.horizonYears = horizonYears;
        this.nPaths = nPaths;
        this.nSteps = nSteps;
        this.muAnnual = muAnnual;
        this.sigmaAnnual = sigmaAnnual;
        this.expectedTerminalPrice = expectedTerminalPrice;
        this.simulationPaths = simulationPaths;
        this.timestamp = LocalDateTime.now();
    }

}