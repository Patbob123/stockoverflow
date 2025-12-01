package data_access;

import entities.monte_carlo.MonteCarloSimulation;
import use_case.monte_carlo.MonteCarloAccessInterface;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class FileMonteCarloDataAccess implements MonteCarloAccessInterface{

    private static final String STORAGE_DIR = "monte_carlo_history";
    private final Path storagePath;

    public FileMonteCarloDataAccess(){
        this.storagePath = Paths.get(STORAGE_DIR);

        try {
            // Ensure the directory exists
            Files.createDirectories(this.storagePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create storage directory: " + STORAGE_DIR, e);
        }
    }

    @Override
    public String saveSimulation(MonteCarloSimulation simulation) {
        String id = UUID.randomUUID().toString();
        simulation.setId(id);

        Path filePath = storagePath.resolve(id + ".ser"); // .ser for serialized file

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath.toFile()))) {


            oos.writeObject(simulation);
            System.out.println("Simulation saved successfully with ID: " + id);
            return id;

        } catch (IOException e) {
            throw new RuntimeException("Failed to save simulation ID: " + id, e);
        }
    }

    @Override
    public MonteCarloSimulation getSimulation(String id) {
        Path filePath = storagePath.resolve(id + ".ser");

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath.toFile()))) {

            // 3. Read and cast the object back to its original type
            return (MonteCarloSimulation) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load simulation ID: " + id, e);
        }
    }


    // Inside data_access.FileMonteCarloDataAccess.java's getHistory method:
    @Override
    public List<MonteCarloSimulation> getHistory(String ticker) {
        List<MonteCarloSimulation> history = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(storagePath, "*.ser")) {
            for (Path entry : stream) {
                try {
                    MonteCarloSimulation sim = getSimulation(entry.getFileName().toString().replace(".ser", ""));

                    // Filter by Ticker
                    if (sim.getTicker().equalsIgnoreCase(ticker)) {
                        history.add(sim);
                    }
                } catch (RuntimeException ignore) {
                    // Ignore corrupt or unreadable files
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading history directory.", e);
        }

        // Sort by timestamp (newest first)
        history.sort(Comparator.comparing(MonteCarloSimulation::getTimestamp).reversed());

        return history;
    }
}