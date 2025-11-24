package interface_adapter.import_export;

import java.util.List;
import entities.Portfolio.Portfolio;
import entities.Simulation;

public class ImportExportState {
    private List<Portfolio> portfolioList; // TODO: IDK IF WE GONNA USE PORTFOLIOLIST OBJECT
    private List<Simulation> simDataList;

    private Portfolio selectedPortfolio;
    private Simulation selectedSimData;

    public List<Portfolio> getPortfolios() {
        return portfolioList;
    }

    public Portfolio getSelectedPortfolio() {
        return selectedPortfolio;
    }

    public List<Simulation> getSimData() {
        return simDataList;
    }
    public Simulation getSelectedSimData() {
        return selectedSimData;
    }

    public void setPortfolios(List<Portfolio> newPortfolioList) {
        this.portfolioList = newPortfolioList;
    }

    public void setSimData(List<Simulation> newSimDataList) {
        this.simDataList = newSimDataList;
    }

    public void setSelectedPortfolio(Portfolio newSelectedPortfolio) {
        this.selectedPortfolio = newSelectedPortfolio;
    }

    public void setSelectedSimData(Simulation newSelectedSimData) {
        this.selectedSimData = newSelectedSimData;
    }


}
