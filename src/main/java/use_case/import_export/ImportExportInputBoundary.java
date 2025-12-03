package use_case.import_export;

import entities.Portfolio.PortfolioList;
import entities.Simulation;
import use_case.InputBoundary;

/**
 * Input boundary for Import/Export use case
 */
public interface ImportExportInputBoundary extends InputBoundary {

    /**
     * execute function for Import/Export use case
     */
    void execute(String operation, String filePath);
}
