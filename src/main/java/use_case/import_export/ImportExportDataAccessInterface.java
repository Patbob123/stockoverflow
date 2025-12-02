package use_case.import_export;

import java.util.List;

import entities.Portfolio.Portfolio;
import entities.Portfolio.PortfolioList;
import entities.Simulation;
import entities.monte_carlo.MonteCarloSimulation;

public interface ImportExportDataAccessInterface {
    void savePortfolio(PortfolioList portfolioList, String filePath);
    void saveSimulation(String filePath);
    void saveCurrentSession(String filePath);
    PortfolioList loadPortfolios(String filePath);

    List<Portfolio> getAllPortfolios();
    List<MonteCarloSimulation> getAllSimulations();
}
