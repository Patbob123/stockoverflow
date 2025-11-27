package app;

import javax.swing.*;

public class MainMenu {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final MainMenuBuilder builder = new MainMenuBuilder();

            final JFrame application = builder
                    .addMainView()
                    .addCreatePortfolioView()
                    .addPortfolioMenuView()
                    .addChangeViewUseCase()
                    .addMainViewUseCase()
                    .addPortfolioMenuUseCase()
                    .addUserAuthenticationViews()  // bz
                    .addUserAuthenticationUseCases()  // bz
                    .addRefreshDataUseCase()  //bz
                    .build();

            application.setExtendedState(JFrame.MAXIMIZED_BOTH);
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        });
    }
}