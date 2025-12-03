package use_case.import_export;

import java.util.List;

import entities.Portfolio.Portfolio;
import entities.Simulation;
import use_case.OutputBoundary;

/**
 * Output boundary for Import/Export use case
 */
public interface ImportExportOutputBoundary extends OutputBoundary {

    /**
     * Prepares the success view to show exported or imported
     */
    void prepareSuccessView(String message);
}
