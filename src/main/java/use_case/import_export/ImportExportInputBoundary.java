package use_case.import_export;

import entities.Portfolio.Portfolio;
import entities.PortfolioList;
import entities.Simulation;

public interface ImportExportInputBoundary {
    void executeImport(String filepath);

    void executeExportCurrentSession(String filepath);

    void executeExportPortfolio(PortfolioList portfolioList, String filepath);

    void executeExportSimData(Simulation simulation, String filepath);

    void loadAvailableData();

}
