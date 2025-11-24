package use_case.import_export;

import java.util.List;

import entities.Portfolio.Portfolio;
import entities.PortfolioList;
import entities.Simulation;

public interface ImportExportDataAccessInterface {
    void savePortfolio(PortfolioList portfolioList, String filePath);
    void saveSimulation(Simulation simulation, String filePath);
    void saveCurrentSession(String filePath);
    PortfolioList loadPortfolios(String filePath);

    List<Portfolio> getAllPortfolios();
    List<Simulation> getAllSimulations();
}
