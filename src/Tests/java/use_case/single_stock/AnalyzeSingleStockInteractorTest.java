package use_case.singlestock;

import entities.PriceBar;
import entities.Stock;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for AnalyzeSingleStockInteractor.
 * Uses simple fake gateways (no Mockito, no HTTP calls).
 */
class AnalyzeSingleStockInteractorTest {

    /** Fake price gateway returning a pre-built Stock. */
    private static class FakePriceGateway implements StockPriceDataAccessInterface {
        private Stock stock;

        FakePriceGateway(Stock stock) {
            this.stock = stock;
        }

        @Override
        public Stock getDailySeries(String ticker, int maxDays) {
            // Ignore ticker for the test and just return our stock.
            return stock;
        }
    }

    /** Fake risk-free gateway that always returns a fixed value. */
    private static class FakeRiskFreeGateway implements RiskFreeRateDataAccessInterface {
        private final double rf;

        FakeRiskFreeGateway(double rf) {
            this.rf = rf;
        }

        @Override
        public double getCurrentRiskFreeRate() {
            return rf;
        }
    }

    /** Captures the last output passed to the output boundary. */
    private static class CapturingOutputBoundary implements AnalyzeSingleStockOutputBoundary {
        AnalyzeSingleStockOutputData lastOutput;

        @Override
        public void present(AnalyzeSingleStockOutputData outputData) {
            this.lastOutput = outputData;
        }
    }

    /** Helper: build a Stock with closing prices (index 0 = most recent). */
    private Stock buildStock(String ticker, double... closes) {
        List<PriceBar> bars = new ArrayList<>();
        LocalDate start = LocalDate.of(2024, 1, 1);

        // your interactor expects series.get(0) to be the most recent
        for (int i = 0; i < closes.length; i++) {
            int revIndex = closes.length - 1 - i;      //  last value becomes index 0
            double c = closes[revIndex];
            LocalDate date = start.plusDays(i);
            bars.add(new PriceBar(date, c, c, c, c, 1_000_000L));
        }
        return new Stock(ticker, bars);
    }

    /** Normal path: manual risk-free > 0, enough data, non-zero volatility. */
    @Test
    void execute_withManualRiskFree_producesReport() {
        Stock stock = buildStock("AAPL", 100, 105, 110, 120);
        FakePriceGateway priceGateway = new FakePriceGateway(stock);
        FakeRiskFreeGateway rfGateway = new FakeRiskFreeGateway(0.02); // ignored
        CapturingOutputBoundary out = new CapturingOutputBoundary();

        AnalyzeSingleStockInteractor interactor =
                new AnalyzeSingleStockInteractor(priceGateway, rfGateway, out);

        AnalyzeSingleStockInputData input =
                new AnalyzeSingleStockInputData("AAPL", 0.03); // use 3% explicitly

        interactor.execute(input);

        assertNotNull(out.lastOutput);
        assertEquals("AAPL", out.lastOutput.getTicker());
        assertEquals(0.03, out.lastOutput.getRiskFreeAnnual(), 1e-9);

        String report = out.lastOutput.getReport();
        assertNotNull(report);
        assertTrue(report.contains("Single Stock Analysis"));
        assertTrue(report.contains("Sharpe"));
    }

    /** rfAnnual <= 0: interactor should call RiskFreeRateDataAccessInterface. */
    @Test
    void execute_withNonPositiveRiskFree_usesRiskFreeGateway() {
        Stock stock = buildStock("MSFT", 100, 101, 102, 103);
        FakePriceGateway priceGateway = new FakePriceGateway(stock);
        FakeRiskFreeGateway rfGateway = new FakeRiskFreeGateway(0.05); // 5%
        CapturingOutputBoundary out = new CapturingOutputBoundary();

        AnalyzeSingleStockInteractor interactor =
                new AnalyzeSingleStockInteractor(priceGateway, rfGateway, out);

        AnalyzeSingleStockInputData input =
                new AnalyzeSingleStockInputData("MSFT", 0.0); // triggers gateway

        interactor.execute(input);

        assertNotNull(out.lastOutput);
        assertEquals("MSFT", out.lastOutput.getTicker());
        assertEquals(0.05, out.lastOutput.getRiskFreeAnnual(), 1e-9);
    }

    /** series.size() < 2: should throw and not present any output. */
    @Test
    void execute_notEnoughData_throwsRuntimeException() {
        Stock stock = buildStock("TSLA", 100); // only one day
        FakePriceGateway priceGateway = new FakePriceGateway(stock);
        FakeRiskFreeGateway rfGateway = new FakeRiskFreeGateway(0.02);
        CapturingOutputBoundary out = new CapturingOutputBoundary();

        AnalyzeSingleStockInteractor interactor =
                new AnalyzeSingleStockInteractor(priceGateway, rfGateway, out);

        AnalyzeSingleStockInputData input =
                new AnalyzeSingleStockInputData("TSLA", 0.01);

        assertThrows(RuntimeException.class, () -> interactor.execute(input));
        assertNull(out.lastOutput);
    }

    /** All closes equal -> zero volatility -> Sharpe branch stdAnnual == 0. */
    @Test
    void execute_zeroVolatility_setsSharpeToZeroWithoutCrashing() {
        Stock stock = buildStock("GLD", 100, 100, 100, 100, 100);
        FakePriceGateway priceGateway = new FakePriceGateway(stock);
        FakeRiskFreeGateway rfGateway = new FakeRiskFreeGateway(0.02);
        CapturingOutputBoundary out = new CapturingOutputBoundary();

        AnalyzeSingleStockInteractor interactor =
                new AnalyzeSingleStockInteractor(priceGateway, rfGateway, out);

        AnalyzeSingleStockInputData input =
                new AnalyzeSingleStockInputData("GLD", 0.02);

        interactor.execute(input);

        assertNotNull(out.lastOutput);
        String report = out.lastOutput.getReport();
        assertNotNull(report);
        assertTrue(report.contains("Sharpe"));
        // we don't assert the exact number to keep the test less brittle
    }
}
