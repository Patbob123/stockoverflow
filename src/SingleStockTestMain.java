import main.java.data_access.AlphaVantageStockPriceDataAccess;
import main.java.data_access.CombinedStockPriceDataAccess;
import main.java.data_access.FredRiskFreeRateDataAccess;
import main.java.data_access.StooqStockDataAccess;
import main.java.interface_adapter.singlestock.SingleStockController;
import main.java.interface_adapter.singlestock.SingleStockPresenter;
import main.java.use_case.singlestock.AnalyzeSingleStockInteractor;
import main.java.use_case.singlestock.CompareTwoStocksInteractor;
import main.java.use_case.singlestock.RiskFreeRateDataAccessInterface;
import main.java.use_case.singlestock.StockPriceDataAccessInterface;
import view.SingleStockView;

import javax.swing.*;

public class SingleStockTestMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            String alphaKey = "YOUR_ALPHA_KEY_HERE";

            StockPriceDataAccessInterface stooqGateway =
                    new StooqStockDataAccess();
            StockPriceDataAccessInterface alphaGateway =
                    new AlphaVantageStockPriceDataAccess(alphaKey);
            StockPriceDataAccessInterface priceGateway =
                    new CombinedStockPriceDataAccess(stooqGateway, alphaGateway);

            String fredKey = System.getenv("FRED_API_KEY");
            RiskFreeRateDataAccessInterface fredGateway =
                    new FredRiskFreeRateDataAccess(fredKey);

            SingleStockView view = new SingleStockView(null);
            SingleStockPresenter presenter = new SingleStockPresenter(view);

            AnalyzeSingleStockInteractor analyzeInteractor =
                    new AnalyzeSingleStockInteractor(priceGateway, fredGateway, presenter);

            CompareTwoStocksInteractor compareInteractor =
                    new CompareTwoStocksInteractor(priceGateway, fredGateway, presenter);

            SingleStockController controller =
                    new SingleStockController(analyzeInteractor, compareInteractor);

            view.setController(controller);

            JFrame frame = new JFrame("Stockoverflow - Single Stock (Compare mode)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(view);
            frame.pack();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

