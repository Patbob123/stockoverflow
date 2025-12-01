package use_case.import_export;

import java.util.List;

import entities.Portfolio.Portfolio;
import entities.Simulation;
import use_case.OutputBoundary;

public interface ImportExportOutputBoundary extends OutputBoundary {
    void prepareSuccessView(Portfolio portfolio);

}
