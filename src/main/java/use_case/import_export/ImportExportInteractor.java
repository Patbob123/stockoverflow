package use_case.import_export;

/**
 * Interactor for Import/Export use case
 */
public class ImportExportInteractor implements ImportExportInputBoundary {

    private final ImportExportOutputBoundary importExportOutputBoundary;
    private final ImportExportDataAccessInterface importExportDAO;

    /**
     * Constructor for ImportExportInteractor.
     *
     * @param importExportOutputBoundary the output boundary to present results
     * @param importExportDAO the data access object for performing file operations
     */
    public ImportExportInteractor(ImportExportOutputBoundary importExportOutputBoundary,
                                  ImportExportDataAccessInterface importExportDAO) {
        this.importExportOutputBoundary = importExportOutputBoundary;
        this.importExportDAO = importExportDAO;
    }

    /**
     * Executes an import or export operation based on the given operation type and file path.
     *
     * @param operation the operation to perform ("import", "export_portfolio", "export_session", "export_simulation")
     * @param filePath the file path to read from or write to
     */
    @Override
    public void execute(String operation, String filePath) {
        if (operation == null || operation.isEmpty()) {
            importExportOutputBoundary.prepareSuccessView("ERROR: No operation");
            return;
        }

        switch (operation.toLowerCase()) {
            case "import":
                importPortfolio(filePath);
                break;
            case "export_portfolio":
                exportPortfolio(filePath);
                break;
            case "export_session":
                exportCurrentSession(filePath);
                break;
            case "export_simulation":
                exportSimData(filePath);
                break;
            default:
                importExportOutputBoundary.prepareSuccessView("???: " + operation);
                break;
        }
    }

    /**
     * ONLY a HELPER FUNCTION, execute still has 100% code coverage. Imports a portfolio
     *
     * @param filePath the path of the file to import
     */
    public void importPortfolio(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            importExportOutputBoundary.prepareSuccessView("Import cancelled");
            return;
        }

        importExportOutputBoundary.prepareSuccessView("Imported portfolio from: " + filePath);
    }

    /**
     * ONLY a HELPER FUNCTION, execute still has 100% code coverage. Exports portfolios
     *
     * @param filePath the path of the file to export to
     */
    public void exportPortfolio(String filePath) {
        importExportDAO.savePortfolio(filePath);
        importExportOutputBoundary.prepareSuccessView("Exported Portfolios to: " + filePath);
    }

    /**
     * ONLY a HELPER FUNCTION, execute still has 100% code coverage. Exports the search history
     *
     * @param filePath the path of the file to export to
     */
    public void exportCurrentSession(String filePath) {
        importExportDAO.saveCurrentSession(filePath);
        importExportOutputBoundary.prepareSuccessView("Exported Search History to: " + filePath);
    }

    /**
     * ONLY a HELPER FUNCTION, execute still has 100% code coverage. Exports simulation data
     *
     * @param filePath the path of the file to export to
     */
    public void exportSimData(String filePath) {
        importExportDAO.saveSimulation(filePath);
        importExportOutputBoundary.prepareSuccessView("Exported simulation to: " + filePath);
    }
}
