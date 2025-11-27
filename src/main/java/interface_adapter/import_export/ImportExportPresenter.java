package interface_adapter.import_export;

import entities.Portfolio.Portfolio;
import entities.PortfolioList;
import entities.Simulation;
import use_case.import_export.ImportExportOutputBoundary;

import java.util.List;

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
