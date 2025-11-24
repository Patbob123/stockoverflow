package data_access;

import entities.Simulation;

public interface SimulationDataAccessInterface {
    Simulation getSimulation(String simId);
    void saveSimulation(Simulation sim);
}
