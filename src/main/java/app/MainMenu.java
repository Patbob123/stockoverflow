package app;

import java.awt.*;
import java.io.InputStream;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;




public class MainMenu {
    public static void main(String[] args) {

        FlatDarkLaf.setup();
        try {
            InputStream fontStream = MainMenu.class.getResourceAsStream("/fonts/MolganRegular-YqWj2.otf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            setUiFont(new FontUIResource(customFont.deriveFont(Font.PLAIN, 14f)));

        } catch (Exception e) {
            e.printStackTrace();
            setUiFont(new FontUIResource("SansSerif", Font.PLAIN, 14));
        }

        final MainMenuBuilder builder = new MainMenuBuilder();
//        Stock s = new Stock("ASDB", "asdasdasd");
//        System.out.println(s.getTicker());
        final JFrame application = builder
                .autoBuild();

        application.setExtendedState(JFrame.MAXIMIZED_BOTH);
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }

    private static void setUiFont(FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }
}
