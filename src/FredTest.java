import data_access.FredRiskFreeRateDataAccess;
import use_case.singlestock.RiskFreeRateDataAccessInterface;

public class FredTest {
    public static void main(String[] args) {
        String apiKey = "7d3793701beaa71a8263c3ae2d4a508b"; // get your own fred key at the website
        RiskFreeRateDataAccessInterface fred =
                new FredRiskFreeRateDataAccess(apiKey);

        double rf = fred.getCurrentRiskFreeRate();
        System.out.println("Current risk-free rate = " + rf);
        System.out.printf("Current risk-free rate = %.2f%%%n", rf * 100); //checkthe precentage
    }
}
// its working