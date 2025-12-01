package data_access;

import entities.Portfolio.Portfolio;
import entities.Portfolio.PortfolioFactory;
import entities.Portfolio.PortfolioList;
import entities.Simulation;
import entities.Stock;
import entities.monte_carlo.MonteCarloSimulation;
import interface_adapter.singlestock.SingleStockViewModel;
import use_case.import_export.ImportExportDataAccessInterface;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImportExportDataAccessObject implements ImportExportDataAccessInterface {

    FileMonteCarloDataAccess monteCarloDataAccess = new FileMonteCarloDataAccess();

    public ImportExportDataAccessObject() {
    }

    @Override
    public Portfolio loadPortfolios(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            Portfolio portfolio = null;

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("Portfolio:")) {
                    String portfolioName = line.substring("Portfolio:".length()).trim();
                    PortfolioFactory portfolioFactory = new PortfolioFactory();
                    portfolio = portfolioFactory.createPortfolio(portfolioName);
                }
                else if (line.startsWith("Ticker,")) {
                }

                else if (!line.isEmpty() && portfolio != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        String ticker = parts[0].trim();
                        int shares = Integer.parseInt(parts[1].trim());

                        Stock stock = new Stock(ticker);
                        portfolio.addStock(stock, shares);
                    }
                }
            }

            reader.close();
            return portfolio;

        } catch (IOException exception) {
            throw new RuntimeException("failed to load portfolio: " + exception.getMessage(), exception);
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
    public void saveSimulation(String filePath) {
        List<MonteCarloSimulation> sims = getAllSimulations();

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("=== SIMULATIONS ===\n\n");

            for (MonteCarloSimulation sim : sims) {
                writer.write(formatSimulation(sim));
            }

        }
        catch (IOException exception) {
            throw new RuntimeException("died: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void saveCurrentSession(String filePath) {
        try {
            Path historyFile = Paths.get(System.getProperty("user.home"),
                    SingleStockViewModel.HISTORY_FILE_NAME);

            if (!Files.exists(historyFile)) {
                return;
            }

            List<String> historyLines = Files.readAllLines(historyFile);

            final FileWriter writer = new FileWriter(filePath);
            writer.append("=== TICKER HISTORY ===\n");
            for (String ticker : historyLines) {
                writer.append(ticker).append("\n");
            }
            writer.close();
        }
        catch (IOException exception) {
            throw new RuntimeException("save failed: " + exception.getMessage(), exception);
        }
    }


    @Override
    public List<Portfolio> getAllPortfolios() {
        return new ArrayList<>();
    }

    @Override
    public List<MonteCarloSimulation> getAllSimulations() {
        List<MonteCarloSimulation> simulations = new ArrayList<>();

        try {
            Path storagePath = Paths.get("monte_carlo_history");
            if (!Files.exists(storagePath)) {
                return simulations;
            }

            Files.list(storagePath)
                    .filter(path -> path.toString().endsWith(".ser"))
                    .forEach(path -> {
                        try {
                            String id = path.getFileName().toString().replace(".ser", "");
                            MonteCarloSimulation sim = monteCarloDataAccess.getSimulation(id);
                            simulations.add(sim);
                        } catch (RuntimeException e) {
                            System.err.println("where sims: " + path);
                        }
                    });

        } catch (IOException e) {
            System.err.println("err: " + e.getMessage());
        }

        return simulations;
    }

    private String formatSimulation(MonteCarloSimulation sim) {
        StringBuilder sb = new StringBuilder();

        sb.append("Sim ID: ").append(sim.getId()).append("\n");
        sb.append("Timestamp: ").append(sim.getTimestamp()).append("\n");
        sb.append("Ticker: ").append(sim.getTicker()).append("\n");
        sb.append("Horizon: ").append(sim.getHorizonYears()).append("\n");
        sb.append("Paths: ").append(sim.getNPaths()).append("\n");
        sb.append("Steps: ").append(sim.getNSteps()).append("\n");
        sb.append("Mu Annual: ").append(sim.getMuAnnual()).append("\n");
        sb.append("Sigma Annual: ").append(sim.getSigmaAnnual()).append("\n");
        sb.append("Expected Terminal Price: ").append(sim.getExpectedTerminalPrice()).append("\n");

        sb.append("Simulation Paths:\n");
        double[][] paths = sim.getSimulationPaths();
        for (int i = 0; i < paths.length; i++) {
            sb.append("  Path ").append(i).append(": ");
            for (double v : paths[i]) {
                sb.append(v).append(" ");
            }
            sb.append("\n");
        }

        sb.append("\n--------------------------------------------\n\n");

        return sb.toString();
    }

}
