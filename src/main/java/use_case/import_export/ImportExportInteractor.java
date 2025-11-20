package use_case.import_export;

import entities.Portfolio.Portfolio;
import entities.Simulation;

import java.nio.file.Files;
import java.util.List;

public class ImportExportInteractor implements ImportExportInputBoundary {
    private final ImportExportOutputBoundary importExportOutputBoundary;

    public ImportExportInteractor(ImportExportOutputBoundary importExportOutputBoundary) {
        this.importExportOutputBoundary = importExportOutputBoundary;
    }


//    public void loadDummyData() { // TODO: REMOVE THIS CHATGPT DUMMY TEST LATER
//        List<Portfolio> dummyPortfolios = List.of(
//                new Portfolio("Tech Portfolio"),
//                new Portfolio("Energy Portfolio"),
//                new Portfolio("Growth Portfolio")
//        );
//
//        List<Simulation> dummySimulations = List.of(
//                new Simulation("Morning Run"),
//                new Simulation("Evening Run"),
//                new Simulation("Stress Test")
//        );
//
//        // Update ViewModel state
//        viewModel.setPortfolios(dummyPortfolios);
//        viewModel.setSimData(dummySimulations);
//    }

    @Override
    public void executeImport(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            importExportOutputBoundary.prepareSuccessView("Import cancelled");
            return;
        }

        // TODO: need parse file logic bro
        System.out.println("we find and import the thing at "+filePath);
        importExportOutputBoundary.prepareSuccessView("Imported portfolio from: " + filePath);
    }

    @Override
    public void executeExportCurrentSession(String filePath) {
        System.out.println("we find and export the ses at "+filePath);
    }

    @Override
    public void executeExportPortfolio(Portfolio portfolio, String filePath) {
        System.out.println("we find and exp[ort the port at "+filePath);
    }

    @Override
    public void executeExportSimData(Simulation simulation, String filePath) {
        System.out.println("we find and export the sim at "+filePath);
    }

}
