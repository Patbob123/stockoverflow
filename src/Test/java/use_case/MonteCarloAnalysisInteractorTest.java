package use_case;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

// --- Imports for PRODUCTION Code (These are necessary for the test to compile) ---
c
import entities.StatisticsCalculator;
import entities.StockMetrics;
import use_case.monte_carlo.*; // Includes MonteCarloAnalysisInteractor, MonteCarloInputData
import interface_adapter.monte_carlo.MonteCarloOutputBoundary;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// ----------------------------------------------------------------------
// --- MOCK AND SPY DEFINITIONS ---
// NOTE: These internal classes are defined here to keep the test self-contained.
// ----------------------------------------------------------------------

// 1. Mock Data Access Interface (The Interactor's dependency contract)
interface MockStooqStockDataAccess {
    // This is the interface type the Interactor expects
    List<PriceBar> getDailySeries(String ticker, int days) throws RuntimeException;
}

// 2. Mock Simulator (Core Calculation)
class MockMonteCarloSimulator {
    private static final double[][] KNOWN_PATHS = new double[][]{{100.0, 105.0}, {100.0, 95.0}};
    private static final double KNOWN_MEAN_TERMINAL = 100.0;

    public double[][] simulate(double initialPrice, double muAnnual, double sigmaAnnual,
                               double horizonYears, int nSteps, int nPaths) {
        return KNOWN_PATHS;
    }
    public double meanTerminal(double[][] paths) {
        return KNOWN_MEAN_TERMINAL;
    }
}

// 3. Mock Statistics Calculator (Input for Simulator)
class MockStatisticsCalculator extends StatisticsCalculator {
    private static final double KNOWN_MU = 0.15;
    private static final double KNOWN_SIGMA = 0.25;

    @Override
    public StockMetrics calculateMetrics(List<PriceBar> priceHistory) {
        return new StockMetrics(KNOWN_MU, KNOWN_SIGMA);
    }
}

// ----------------------------------------------------------------------
// --- THE ACTUAL UNIT TEST CLASS ---
// ----------------------------------------------------------------------

public class MonteCarloAnalysisInteractorTest {

    // Dependencies (Mocks/Spies/Fakes)
    private MockStooqStockDataAccess mockDataAccess;
    private MockMonteCarloSimulator mockSimulator;
    private MockStatisticsCalculator mockCalculator;
    private MonteCarloOutputBoundary mockPresenter;
    private MonteCarloAnalysisInteractor interactor;

    private final double INITIAL_PRICE = 200.0;
    private final List<PriceBar> FAKE_HISTORY = Arrays.asList(new PriceBar(INITIAL_PRICE), new PriceBar(199.0), new PriceBar(198.0));
    private MonteCarloInputData inputData;

    @BeforeEach
    void setUp() {
        mockDataAccess = mock(MockStooqStockDataAccess.class);
        mockPresenter = mock(MonteCarloOutputBoundary.class);
        mockSimulator = new MockMonteCarloSimulator();
        mockCalculator = new MockStatisticsCalculator();

        // Define the fixed input data
        inputData = new MonteCarloInputData("TSLA", 1.0, 252, 500);

        // Configure Data Access Mock to return the fixed history when called
        when(mockDataAccess.getDailySeries(anyString(), anyInt())).thenReturn(FAKE_HISTORY);

        // Instantiate the Interactor with all dependencies
        interactor = new MonteCarloAnalysisInteractor(
                (data_access.StooqStockDataAccess) mockDataAccess,
                mockSimulator,
                mockCalculator,
                mockPresenter
        );
    }

    @Test
    void execute_successfulRun_callsAllDependenciesAndPresentsSuccess() {
        // ... (Execution and verification logic remains the same) ...
        interactor.execute(inputData);

        // Verify Data Access was called
        verify(mockDataAccess, times(1)).getDailySeries(anyString(), anyInt());

        // Capture and verify output
        ArgumentCaptor<MonteCarloOutputData> outputCaptor = ArgumentCaptor.forClass(MonteCarloOutputData.class);
        verify(mockPresenter, times(1)).presentSuccess(outputCaptor.capture());

        MonteCarloOutputData capturedOutput = outputCaptor.getValue();
        assertEquals(MockMonteCarloSimulator.KNOWN_MEAN_TERMINAL, capturedOutput.getMeanTerminalPrice(), 0.001);
        assertEquals(INITIAL_PRICE, capturedOutput.getInitialPrice(), 0.001);

        verify(mockPresenter, never()).presentError(anyString());
    }

    @Test
    void execute_dataAccessThrowsError_presentsError() {
        // ... (Error handling logic remains the same) ...
        String errorMessage = "Network connection failed during data fetch.";
        when(mockDataAccess.getDailySeries(anyString(), anyInt())).thenThrow(new RuntimeException(errorMessage));

        interactor.execute(inputData);

        // Verify that the error presentation method was called
        verify(mockPresenter, times(1)).presentError(errorMessage);

        // Verify that the success method was NOT called
        verify(mockPresenter, never()).presentSuccess(any(MonteCarloOutputData.class));
    }
}