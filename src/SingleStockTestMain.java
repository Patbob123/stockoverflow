import data_access.AlphaVantageStockPriceDataAccess;
import data_access.CombinedStockPriceDataAccess;
import data_access.FredRiskFreeRateDataAccess;
import data_access.StooqStockDataAccess;
import interface_adapter.singlestock.SingleStockController;
import interface_adapter.singlestock.SingleStockPresenter;
import interface_adapter.singlestock.SingleStockViewModel;
import use_case.singlestock.AnalyzeSingleStockInteractor;
import use_case.singlestock.CompareTwoStocksInteractor;
import use_case.singlestock.RiskFreeRateDataAccessInterface;
import use_case.singlestock.StockPriceDataAccessInterface;
import view.SingleStockView;

import javax.swing.*;

public class SingleStockTestMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            String alphaKey = "YOUR_ALPHA_KEY_HERE"; // or System.getenv("ALPHAVANTAGE_API_KEY");

            StockPriceDataAccessInterface stooqGateway =
                    new StooqStockDataAccess();
            StockPriceDataAccessInterface alphaGateway =
                    new AlphaVantageStockPriceDataAccess(alphaKey);
            StockPriceDataAccessInterface priceGateway =
                    new CombinedStockPriceDataAccess(stooqGateway, alphaGateway);

            String fredKey = System.getenv("FRED_API_KEY");
            RiskFreeRateDataAccessInterface fredGateway =
                    new FredRiskFreeRateDataAccess(fredKey);

            SingleStockViewModel viewModel = new SingleStockViewModel();
            SingleStockView view = new SingleStockView(viewModel, null);

            SingleStockPresenter presenter = new SingleStockPresenter(view);

            AnalyzeSingleStockInteractor analyzeInteractor =
                    new AnalyzeSingleStockInteractor(priceGateway, fredGateway, presenter);

            CompareTwoStocksInteractor compareInteractor =
                    new CompareTwoStocksInteractor(priceGateway, fredGateway, presenter);

            SingleStockController controller =
                    new SingleStockController(analyzeInteractor, compareInteractor);

            view.setController(controller);


            JFrame frame = new JFrame("Stockoverflow - Single Stock (Compare + History)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(view);
            frame.pack();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

