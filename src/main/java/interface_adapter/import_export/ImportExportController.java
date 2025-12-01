package interface_adapter.import_export;

import entities.PortfolioList;
import entities.Simulation;
import interface_adapter.AbsController;
import use_case.import_export.ImportExportInputBoundary;

public class ImportExportController extends AbsController {
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

    public void exportPortfolio(PortfolioList portfolioList, String filepath) {
        importExportInputBoundary.executeExportPortfolio(portfolioList, filepath);
    }

    public void exportSimData(Simulation simulation, String filepath) {
        importExportInputBoundary.executeExportSimData(simulation, filepath);
    }

    public void loadAvailableData() {
        importExportInputBoundary.loadAvailableData();
    }

}
