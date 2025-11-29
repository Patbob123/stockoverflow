package use_case.portfolio;

import java.util.ArrayList;
import java.util.List;

public interface PortfolioMenuInputBoundary {

    void executeAddStock();

    void executeRemoveStock(ArrayList<String> stocks);

    void executeGraph(List<String> selectedTickers);

    void executeHistoricalAnalysis(int daysAgo);

    void executeCompare(Portfolio comparePortfolio);

    void executeSelectAll();

    void executeClearSelection();

    void executeSavePortfolio();

    void executeExit();

}
