package app;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;


public class MainMenu {
    public static void main(String[] args) {

        FlatDarkLaf.setup();

        final MainMenuBuilder builder = new MainMenuBuilder();
//        Stock s = new Stock("ASDB", "asdasdasd");
//        System.out.println(s.getTicker());
        final JFrame application = builder
                .addMainView()
                .addImportExportView()
                .addPortfolioMenuView()
                .addChangeViewUseCase()
                .addMainViewUseCase()
                .addImportExportUseCase()
                .build();

        application.setExtendedState(JFrame.MAXIMIZED_BOTH);
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
