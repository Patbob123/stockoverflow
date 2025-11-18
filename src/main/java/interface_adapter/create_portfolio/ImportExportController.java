package interface_adapter.create_portfolio;

import use_case.import_export.ImportExportInputBoundary;

public class ImportExportController {
    private final ImportExportInputBoundary createPortfolioInteractor;

    public ImportExportController(ImportExportInputBoundary createPortfolioInteractor) {
        this.createPortfolioInteractor = createPortfolioInteractor;
    }

    /**
     * Executes the Note related Use Cases.
     * @param note the note to be recorded
     */
    public void importPortfolio(String filepath) {
        createPortfolioInteractor.executeImport(filepath);
    }
}
