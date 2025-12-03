package use_case.import_export;

import java.util.List;

import entities.Portfolio.Portfolio;
import entities.Portfolio.PortfolioList;
import entities.monte_carlo.MonteCarloSimulation;

/**
 * Data access interface for Import/Export operations.
 */
public interface ImportExportDataAccessInterface {

    /**
     * Saves all portfolios to file path.
     *
     * @param filePath the file path to save portfolios
     */
    void savePortfolio(String filePath);

    /**
     * Saves all simulation data to file path.
     *
     * @param filePath the file path to save simulations
     */
    void saveSimulation(String filePath);

    /**
     * Saves the search history to file path.
     *
     * @param filePath the file path to save the search history
     */
    void saveCurrentSession(String filePath);

    /**
     * Loads portfolio from file path.
     *
     * @param filePath the file path to load portfolios from
     * @return a PortfolioList containing loaded portfolios
     */
    PortfolioList loadPortfolios(String filePath);

    /**
     * Retrieves all portfolios for presenter later.
     *
     * @return a list of all portfolios
     */
    List<Portfolio> getAllPortfolios();

    /**
     * Retrieves all Monte Carlo simulations for presenter later.
     *
     * @return a list of all simulations
     */
    List<MonteCarloSimulation> getAllSimulations();
}
