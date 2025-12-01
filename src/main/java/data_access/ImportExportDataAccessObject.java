package data_access;

import entities.Portfolio.Portfolio;
import entities.Portfolio.PortfolioList;
import entities.Simulation;
import use_case.import_export.ImportExportDataAccessInterface;
import java.io.*;
import java.util.List;

public class ImportExportDataAccessObject implements ImportExportDataAccessInterface {

    public ImportExportDataAccessObject() {
    }

    @Override
    public PortfolioList loadPortfolios(String filePath) {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(filePath));
            reader.close();
            return null; // TODO: implement CSV parsing
        }
        catch (IOException exception) {
            throw new RuntimeException("died: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void savePortfolio(PortfolioList portfolioList, String filePath) {
        try {
            final FileWriter writer = new FileWriter(filePath);
            writer.append("wow portfoliolist here");

            writer.close();
        } catch (IOException exception) {
            throw new RuntimeException("portfolioList failed to save: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void saveSimulation(Simulation simulation, String filePath) {
        try {
            final FileWriter writer = new FileWriter(filePath);
            writer.append("simulation is here");

            writer.close();
        }
        catch (IOException exception) {
            throw new RuntimeException("died: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void saveCurrentSession(String filePath) {
        //IDK I HAVE NO CLUE YET
    }

    @Override
    public List<Portfolio> getAllPortfolios() {
        return List.of();
    }

    @Override
    public List<Simulation> getAllSimulations() {
        return List.of();
    }

}
