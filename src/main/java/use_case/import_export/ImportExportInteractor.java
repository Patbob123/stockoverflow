package use_case.import_export;

import entities.Portfolio.Portfolio;
import entities.Portfolio.PortfolioFactory;
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

    public void executeImport(String filePath) {
        try {
            Portfolio portfolio = importExportDAO.loadPortfolios(filePath);
            importExportOutputBoundary.prepareSuccessView(portfolio);
        } catch (Exception e) {
            PortfolioFactory portfolioFactory = new PortfolioFactory();
            importExportOutputBoundary.prepareSuccessView(portfolioFactory.createPortfolio("error"));
        }
    }

    @Override
    public void executeExportPortfolio(PortfolioList portfolioList, String filePath) {
//        try {
            importExportDAO.savePortfolio(portfolioList, filePath);
            //importExportOutputBoundary.prepareSuccessView("we find and exp[ort the port at " + filePath);

//        }
//        catch (IOException ioException) {
//            //importExportOutputBoundary.prepareFailView("IO Excetpion at: " + ioException.getMessage());
//        }
        System.out.println(" " + filePath);
    }

    @Override
    public void executeExportCurrentSession(String filePath) {
//        try {
        PortfolioFactory portfolioFactory = new PortfolioFactory();
            importExportDAO.saveCurrentSession(filePath);
            importExportOutputBoundary.prepareSuccessView(portfolioFactory.createPortfolio("a"));

//        }
//        catch (IOException ioException) {
//            //importExportOutputBoundary.prepareFailView("IO Excetpion at: " + ioException.getMessage());
//        }
        System.out.println("ses "+filePath);
    }

    @Override
    public void executeExportSimData(String filePath) {
//        try {
        PortfolioFactory portfolioFactory = new PortfolioFactory();
            importExportDAO.saveSimulation(filePath);
            importExportOutputBoundary.prepareSuccessView(portfolioFactory.createPortfolio("b"));

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
            //List<Simulation> simulations = importExportDAO.getAllSimulations();
            //importExportOutputBoundary.presentAvailableData(portfolios, simulations);
        //}
//        catch (Exception e) {
//            importExportOutputBoundary.prepareFailView("Failed to load data: " + e.getMessage());
//        }
    }

}
