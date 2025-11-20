package interface_adapter.create_portfolio;

import entities.Portfolio.Portfolio;
import entities.Simulation;
import use_case.import_export.ImportExportInputBoundary;

public class ImportExportController {
    private final ImportExportInputBoundary importExportInputBoundary;

    public ImportExportController(ImportExportInputBoundary importExportInputBoundary) {
        this.importExportInputBoundary = importExportInputBoundary;
    }

    /**
     * Executes the Note related Use Cases.
     * @param note the note to be recorded
     */
    public void importPortfolio(String filepath) {
        importExportInputBoundary.executeImport(filepath);
    }

    public void exportCurrentSession(String filepath) {
        importExportInputBoundary.executeExportCurrentSession(filepath);
    }

    public void exportPortfolio(Portfolio portfolio, String filepath) {
        importExportInputBoundary.executeExportPortfolio(portfolio, filepath);
    }

    public void exportSimData(Simulation simulation, String filepath) {
        importExportInputBoundary.executeExportSimData(simulation, filepath);
    }

}
