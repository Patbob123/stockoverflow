package use_case.import_export;

import entities.Portfolio.Portfolio;
import entities.Portfolio.PortfolioList;
import entities.Simulation;

import java.util.List;

public class ImportExportInteractor implements ImportExportInputBoundary {
    private final ImportExportOutputBoundary importExportOutputBoundary;
    private final ImportExportDataAccessInterface importExportDAO;

    public ImportExportInteractor(ImportExportOutputBoundary importExportOutputBoundary,
                                  ImportExportDataAccessInterface importExportDAO) {
        this.importExportOutputBoundary = importExportOutputBoundary;
        this.importExportDAO = importExportDAO;
    }

    @Override
    public void executeImport(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            importExportOutputBoundary.prepareSuccessView("Import cancelled");
            return;
        }

        // TODO: I GOT RID OF ALL THE TRY CATCHES I GOTTA ADD THEM BACK
//        try {

            System.out.println("we find and import the thing at "+filePath);
            importExportOutputBoundary.prepareSuccessView("Imported portfolio from: " + filePath);
//        }
//        catch (IOException ioException)  {
//            importExportOutputBoundary.prepareSuccessView("IO Excetpion at: " + ioException.getMessage());
//
//        }
    }

    @Override
    public void executeExportPortfolio(PortfolioList portfolioList, String filePath) {
//        try {
            importExportDAO.savePortfolio(portfolioList, filePath);
            importExportOutputBoundary.prepareSuccessView("we find and exp[ort the port at " + filePath);

//        }
//        catch (IOException ioException) {
//            //importExportOutputBoundary.prepareFailView("IO Excetpion at: " + ioException.getMessage());
//        }
        System.out.println(" " + filePath);
    }

    @Override
    public void executeExportCurrentSession(String filePath) {
//        try {
            importExportDAO.saveCurrentSession(filePath);
            importExportOutputBoundary.prepareSuccessView("we find and exp[ort the ses at: " + filePath);

//        }
//        catch (IOException ioException) {
//            //importExportOutputBoundary.prepareFailView("IO Excetpion at: " + ioException.getMessage());
//        }
        System.out.println("ses "+filePath);
    }

    @Override
    public void executeExportSimData(Simulation simulation, String filePath) {
//        try {
            importExportDAO.saveSimulation(simulation, filePath);
            importExportOutputBoundary.prepareSuccessView("Exported simulation to: " + filePath);

//        }
//        catch (IOException ioException) {
//            //importExportOutputBoundary.prepareFailView("Failed to export simulation: " + ioException.getMessage());
//        }
        System.out.println("sim " + filePath);
    }

    @Override
    public void loadAvailableData() {
        //try {
            List<Portfolio> portfolios = importExportDAO.getAllPortfolios();
            List<Simulation> simulations = importExportDAO.getAllSimulations();
            //importExportOutputBoundary.presentAvailableData(portfolios, simulations);
        //}
//        catch (Exception e) {
//            importExportOutputBoundary.prepareFailView("Failed to load data: " + e.getMessage());
//        }
    }

}
