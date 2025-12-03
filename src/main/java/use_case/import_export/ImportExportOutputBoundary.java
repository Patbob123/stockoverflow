package use_case.import_export;

import use_case.OutputBoundary;

/**
 * Output boundary for Import/Export use case.
 */
public interface ImportExportOutputBoundary extends OutputBoundary {

    /**
     * Prepares the success view to show exported or imported.
     *
     * @param message message
     */
    void prepareSuccessView(String message);
}
