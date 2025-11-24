package app;
import javax.swing.*;


public class MainMenu {
    public static void main(String[] args) {

        final MainMenuBuilder builder = new MainMenuBuilder();
        final JFrame application = builder
                .addMainView()
                .addCreatePortfolioView()
                .addPortfolioMenuView()
                .addSimulationView() // Add simulation view
                .addChangeViewUseCase()
                .addMainViewUseCase()
                .build();

        application.setExtendedState(JFrame.MAXIMIZED_BOTH);
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
