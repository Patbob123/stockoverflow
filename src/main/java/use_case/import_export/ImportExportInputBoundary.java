package use_case.import_export;

import entities.Portfolio.Portfolio;
import entities.PortfolioList;
import entities.Simulation;
import use_case.InputBoundary;

public interface ImportExportInputBoundary extends InputBoundary {
    void executeImport(String filepath);

    void executeExportCurrentSession(String filepath);

    void executeExportPortfolio(PortfolioList portfolioList, String filepath);

    void executeExportSimData(Simulation simulation, String filepath);

    void loadAvailableData();

}
