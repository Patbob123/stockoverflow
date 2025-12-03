package use_case;

import entities.Portfolio.PortfolioList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.import_export.ImportExportDataAccessInterface;
import use_case.import_export.ImportExportInteractor;
import use_case.import_export.ImportExportOutputBoundary;

import static org.junit.jupiter.api.Assertions.*;

class ImportExportInteractorTest {

    private ImportExportInteractor interactor;
    private TestOutputBoundary output;
    private TestDAO dao;

    static class TestOutputBoundary implements ImportExportOutputBoundary {
        String lastMessage;

        @Override
        public void prepareSuccessView(String message) {
            lastMessage = message;
        }
    }

    static class TestDAO implements ImportExportDataAccessInterface {

        String savedPortfolio;
        String savedSession;
        String savedSim;

        @Override
        public void savePortfolio(String filePath) {
            savedPortfolio = filePath;
        }

        @Override
        public void saveSimulation(String filePath) {
            savedSim = filePath;
        }

        @Override
        public void saveCurrentSession(String filePath) {
            savedSession = filePath;
        }

        @Override
        public PortfolioList loadPortfolios(String filePath) {
            return null;
        }

        @Override
        public java.util.List<entities.Portfolio.Portfolio> getAllPortfolios() {
            return null;
        }

        @Override
        public java.util.List<entities.monte_carlo.MonteCarloSimulation> getAllSimulations() {
            return null;
        }
    }

    @BeforeEach
    void setUp() {
        output = new TestOutputBoundary();
        dao = new TestDAO();
        interactor = new ImportExportInteractor(output, dao);
    }

    @Test
    void testImportPortfolio() {
        interactor.execute("import", "file.csv");
        assertEquals("Imported portfolio from: file.csv", output.lastMessage);
    }

    @Test
    void testImportCancelled() {
        interactor.execute("import", "");
        assertEquals("Import cancelled", output.lastMessage);
    }

    @Test
    void testExportPortfolio() {
        interactor.execute("export_portfolio", "portfolio.csv");
        assertEquals("Exported Portfolios to: portfolio.csv", output.lastMessage);
        assertEquals("portfolio.csv", dao.savedPortfolio);
    }

    @Test
    void testExportSession() {
        interactor.execute("export_session", "session.csv");
        assertEquals("Exported Search History to: session.csv", output.lastMessage);
        assertEquals("session.csv", dao.savedSession);
    }

    @Test
    void testExportSimulation() {
        interactor.execute("export_simulation", "sim.csv");
        assertEquals("Exported simulation to: sim.csv", output.lastMessage);
        assertEquals("sim.csv", dao.savedSim);
    }

    @Test
    void testUnknownOperation() {
        interactor.execute("unknown", "file.csv");
        assertEquals("???: unknown", output.lastMessage);
    }

    @Test
    void testNullOrEmptyOperation() {
        interactor.execute(null, "file.csv");
        assertEquals("ERROR: No operation", output.lastMessage);

        interactor.execute("", "file.csv");
        assertEquals("ERROR: No operation", output.lastMessage);
    }
}
