package use_case.show_graph;

import entities.PriceBar;
import entities.Stock;
import org.junit.jupiter.api.Test;
import use_case.singlestock.StockPriceDataAccessInterface;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ShowGraphInteractorTestCase {

    private static class TestShowGraphPresenter implements ShowGraphOutputBoundary {

        String lastFailMessage;
        ShowGraphOutputData lastSuccessOutput;
        int failCount = 0;
        int successCount = 0;

        @Override
        public void prepareFailView(String error) {
            this.lastFailMessage = error;
            this.failCount++;
        }

        @Override
        public void prepareSuccessView(ShowGraphOutputData outputData) {
            this.lastSuccessOutput = outputData;
            this.successCount++;
        }
    }

    @Test
    void execute_withNullTickers_callsFailViewImmediately() {
        StockPriceDataAccessInterface api = mock(StockPriceDataAccessInterface.class);
        TestShowGraphPresenter presenter = new TestShowGraphPresenter();
        ShowGraphInteractor interactor = new ShowGraphInteractor(api, presenter);

        ShowGraphInputData input = mock(ShowGraphInputData.class);
        when(input.getTickers()).thenReturn(null);

        interactor.execute(input);

        assertEquals("No tickers provided.", presenter.lastFailMessage);
        assertEquals(1, presenter.failCount);
        assertEquals(0, presenter.successCount);
        verifyNoInteractions(api);
    }

    @Test
    void execute_whenAllTickersHaveNoData_callsFailView() {
        StockPriceDataAccessInterface api = mock(StockPriceDataAccessInterface.class);
        TestShowGraphPresenter presenter = new TestShowGraphPresenter();
        ShowGraphInteractor interactor = new ShowGraphInteractor(api, presenter);

        ShowGraphInputData input = mock(ShowGraphInputData.class);
        List<String> tickers = Collections.singletonList("EMPTY");
        when(input.getTickers()).thenReturn(tickers);

        Stock emptyStock = mock(Stock.class);
        when(emptyStock.getPriceHistory()).thenReturn(Collections.emptyList());

        when(api.getDailySeries(eq("EMPTY"), anyInt()))
                .thenReturn(emptyStock);

        interactor.execute(input);

        assertEquals("No valid data found for the provided tickers.", presenter.lastFailMessage);
        assertEquals(1, presenter.failCount);
        verify(api).getDailySeries(eq("EMPTY"), anyInt());
    }

    @Test
    void execute_withValidDataAndException_callsSuccessView() {
        StockPriceDataAccessInterface api = mock(StockPriceDataAccessInterface.class);
        TestShowGraphPresenter presenter = new TestShowGraphPresenter();
        ShowGraphInteractor interactor = new ShowGraphInteractor(api, presenter);

        ShowGraphInputData input = mock(ShowGraphInputData.class);
        when(input.getTickers()).thenReturn(Arrays.asList("GOOD", "BAD"));
        when(input.getPreviousViewName()).thenReturn("previous-view");

        PriceBar bar1 = mock(PriceBar.class);
        when(bar1.getDate()).thenReturn(LocalDate.of(2024, 1, 1));
        when(bar1.getClose()).thenReturn(10.0);

        PriceBar bar2 = mock(PriceBar.class);
        when(bar2.getDate()).thenReturn(LocalDate.of(2024, 1, 2));
        when(bar2.getClose()).thenReturn(12.5);

        Stock goodStock = mock(Stock.class);
        when(goodStock.getPriceHistory()).thenReturn(Arrays.asList(bar1, bar2));

        when(api.getDailySeries(eq("GOOD"), anyInt())).thenReturn(goodStock);

        when(api.getDailySeries(eq("BAD"), anyInt()))
                .thenThrow(new RuntimeException("API failure"));

        interactor.execute(input);

        assertEquals(0, presenter.failCount);
        assertEquals(1, presenter.successCount);
        assertNotNull(presenter.lastSuccessOutput);

        ShowGraphOutputData output = presenter.lastSuccessOutput;
        assertNotNull(output.getStockData());

        assertTrue(output.getStockData().containsKey("GOOD"));
        assertFalse(output.getStockData().containsKey("BAD"));

        assertEquals("previous-view", output.getPreviousViewName());
    }

    @Test
    void execute_withEmptyTickers_callsFailView() {
        StockPriceDataAccessInterface api = mock(StockPriceDataAccessInterface.class);
        TestShowGraphPresenter presenter = new TestShowGraphPresenter();
        ShowGraphInteractor interactor = new ShowGraphInteractor(api, presenter);

        ShowGraphInputData input = mock(ShowGraphInputData.class);
        when(input.getTickers()).thenReturn(Collections.emptyList());

        interactor.execute(input);

        assertEquals("No tickers provided.", presenter.lastFailMessage);
        assertEquals(1, presenter.failCount);
    }

    @Test
    void execute_withNullPriceBars_skipsTickerButStillSuccess() {
        StockPriceDataAccessInterface api = mock(StockPriceDataAccessInterface.class);
        TestShowGraphPresenter presenter = new TestShowGraphPresenter();
        ShowGraphInteractor interactor = new ShowGraphInteractor(api, presenter);

        ShowGraphInputData input = mock(ShowGraphInputData.class);
        when(input.getTickers()).thenReturn(Arrays.asList("NULLDATA", "GOOD"));

        when(api.getDailySeries(eq("NULLDATA"), anyInt()))
                .thenReturn(null);

        PriceBar bar1 = mock(PriceBar.class);
        when(bar1.getDate()).thenReturn(LocalDate.of(2024, 1, 1));
        when(bar1.getClose()).thenReturn(100.0);

        Stock goodStock = mock(Stock.class);
        when(goodStock.getPriceHistory()).thenReturn(Collections.singletonList(bar1));

        when(api.getDailySeries(eq("GOOD"), anyInt())).thenReturn(goodStock);

        interactor.execute(input);

        assertEquals(1, presenter.successCount);
        assertEquals(0, presenter.failCount);
        assertNotNull(presenter.lastSuccessOutput);

        assertTrue(presenter.lastSuccessOutput.getStockData().containsKey("GOOD"));
        assertFalse(presenter.lastSuccessOutput.getStockData().containsKey("NULLDATA"));
    }

    @Test
    void execute_withMixedData_coversAllBranches() {
        StockPriceDataAccessInterface api = mock(StockPriceDataAccessInterface.class);
        TestShowGraphPresenter presenter = new TestShowGraphPresenter();
        ShowGraphInteractor interactor = new ShowGraphInteractor(api, presenter);

        ShowGraphInputData input = new ShowGraphInputData(
                Arrays.asList("NORMAL", "EMPTY_LIST", "NULL_LIST"),
                null,
                null,
                "any_view"
        );

        Stock stockNormal = mock(Stock.class);
        PriceBar bar = mock(PriceBar.class);
        when(bar.getDate()).thenReturn(LocalDate.now());
        when(bar.getClose()).thenReturn(100.0);
        when(stockNormal.getPriceHistory()).thenReturn(Collections.singletonList(bar));
        when(api.getDailySeries(eq("NORMAL"), anyInt())).thenReturn(stockNormal);

        Stock stockEmpty = mock(Stock.class);
        when(stockEmpty.getPriceHistory()).thenReturn(Collections.emptyList());
        when(api.getDailySeries(eq("EMPTY_LIST"), anyInt())).thenReturn(stockEmpty);

        Stock stockNullList = mock(Stock.class);
        when(stockNullList.getPriceHistory()).thenReturn(null);
        when(api.getDailySeries(eq("NULL_LIST"), anyInt())).thenReturn(stockNullList);

        interactor.execute(input);

        assertEquals(1, presenter.successCount);
        assertNotNull(presenter.lastSuccessOutput);

        assertTrue(presenter.lastSuccessOutput.getStockData().containsKey("NORMAL"));
        assertFalse(presenter.lastSuccessOutput.getStockData().containsKey("EMPTY_LIST"));
        assertFalse(presenter.lastSuccessOutput.getStockData().containsKey("NULL_LIST"));
    }
}