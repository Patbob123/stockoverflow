package use_case.monte_carlo;

import entities.monte_carlo.MonteCarloSimulation;
import java.util.List;

public interface MonteCarloAccessInterface {

    // Saves a new simulation result
    String saveSimulation(MonteCarloSimulation simulation);

    // Retrieves a specific simulation by ID
    MonteCarloSimulation getSimulation(String id);

    // Retrieves a list of simulations for a given ticker
    List<MonteCarloSimulation> getHistory(String ticker);
}