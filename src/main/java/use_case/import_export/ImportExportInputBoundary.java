package use_case.import_export;

import use_case.InputBoundary;

/**
 * Input boundary for Import/Export use case.
 */
public interface ImportExportInputBoundary extends InputBoundary {

    /**
     * Execute function for Import/Export use case.
     *
     * @param operation operation.
     * @param filePath filePath.
     */
    void execute(String operation, String filePath);
}
