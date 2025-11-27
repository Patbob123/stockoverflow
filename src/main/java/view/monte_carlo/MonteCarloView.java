package view.monte_carlo;

public interface MonteCarloView {

    void displayMetrics(String initialPrice, String expectedTerminalPrice);

    void showPaths(double[][] paths, int nToShow, String title);

    void showErrorMessage(String message);
}