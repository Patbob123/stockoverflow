import data_access.FredRiskFreeRateDataAccess;
import interface_adapter.singlestock.SingleStockController;
import interface_adapter.singlestock.SingleStockPresenter;
import use_case.singlestock.AnalyzeSingleStockInteractor;
import use_case.singlestock.RiskFreeRateDataAccessInterface;
import view.SingleStockView;

import javax.swing.*;

public class SingleStockTestMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            String fredKey = System.getenv("FRED_API_KEY");
            RiskFreeRateDataAccessInterface fredGateway =
                    new FredRiskFreeRateDataAccess(fredKey);


            SingleStockView view = new SingleStockView(null);


            SingleStockPresenter presenter = new SingleStockPresenter(view);
            AnalyzeSingleStockInteractor interactor =
                    new AnalyzeSingleStockInteractor(fredGateway, presenter);
            SingleStockController controller = new SingleStockController(interactor);


            view.setController(controller);


            JFrame frame = new JFrame("Stockoverflow - Single Stock");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(view);

            frame.pack();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
