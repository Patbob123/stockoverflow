package interface_adapter.import_export;

import entities.Portfolio.PortfolioList;
import interface_adapter.AbsController;
import use_case.import_export.ImportExportInputBoundary;

/**
 * Controller for the Import/Export use case.
 */
public class ImportExportController extends AbsController {

    private final ImportExportInputBoundary importExportInputBoundary;

    /**
     * Constructor for ImportExportController.
     *
     * @param importExportInputBoundary the input boundary
     */
    public ImportExportController(ImportExportInputBoundary importExportInputBoundary) {
        this.importExportInputBoundary = importExportInputBoundary;
    }

    /**
     * Imports a portfolio from file path.
     *
     * @param filepath path to the CSV file to import
     */
    public void importPortfolio(String filepath) {
        importExportInputBoundary.execute("import", filepath);
    }

    /**
     * Exports the search history (yeah not renaming it) to file path.
     *
     * @param filepath path to save the current session
     */
    public void exportCurrentSession(String filepath) {
        importExportInputBoundary.execute("export_session", filepath);
    }

    /**
     * Exports the given portfolio list to file path.
     *
     * @param portfolioList the portfolios to export
     * @param filepath path to save the exported portfolios
     */
    public void exportPortfolio(PortfolioList portfolioList, String filepath) {
        importExportInputBoundary.execute("export_portfolio", filepath);
    }

    /**
     * Exports simulation data to file path.
     *
     * @param filepath path to save the simulation data
     */
    public void exportSimData(String filepath) {
        importExportInputBoundary.execute("export_simulation", filepath);
    }
}
