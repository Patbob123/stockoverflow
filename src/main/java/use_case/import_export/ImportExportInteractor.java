package use_case.import_export;

public class ImportExportInteractor implements ImportExportInputBoundary {
    private final ImportExportOutputBoundary importExportOutputBoundary;

    public ImportExportInteractor(ImportExportOutputBoundary importExportOutputBoundary) {
        this.importExportOutputBoundary = importExportOutputBoundary;
    }

    @Override
    public void executeImport(String filepath) {
        if (filepath == null || filepath.isEmpty()) {
            importExportOutputBoundary.prepareSuccessView("Import cancelled");
            return;
        }

        // TODO: need parse file logic bro
        importExportOutputBoundary.prepareSuccessView("Imported portfolio from: " + filepath);
    }

}
