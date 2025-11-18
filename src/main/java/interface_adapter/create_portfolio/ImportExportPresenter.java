package interface_adapter.create_portfolio;

import use_case.import_export.ImportExportOutputBoundary;

public class ImportExportPresenter implements ImportExportOutputBoundary {
    private final ImportExportViewModel importExportViewModel;

    public ImportExportPresenter(ImportExportViewModel importExportViewModel) {
        this.importExportViewModel = importExportViewModel;
    }

    @Override
    public void prepareSuccessView(String message) {
        System.out.println("Main menu did something idk what dont ask me");
        importExportViewModel.firePropertyChange();
    }
}
