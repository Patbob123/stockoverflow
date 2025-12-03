package interface_adapter.import_export;

import use_case.import_export.ImportExportOutputBoundary;

/**
 * Presenter for import/export use case.
 */
public class ImportExportPresenter implements ImportExportOutputBoundary {
    private final ImportExportViewModel importExportViewModel;

    /**
     * Constructor for ImportExportPresenter.
     *
     * @param importExportViewModel   view model for import and export
     */
    public ImportExportPresenter(ImportExportViewModel importExportViewModel) {
        this.importExportViewModel = importExportViewModel;
    }

    /**
     * Creates a view when anything is exported or imported.
     *
     * @param message       The message to the user
     */
    @Override
    public void prepareSuccessView(String message) {
        importExportViewModel.firePropertyChange();
    }
}
