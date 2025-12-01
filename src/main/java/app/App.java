package app;

import java.awt.*;
import java.io.InputStream;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import com.formdev.flatlaf.FlatDarkLaf;


public class App {
    public static void main(String[] args) {

        FlatDarkLaf.setup();
        try {
            InputStream fontStream = App.class.getResourceAsStream("/fonts/MolganRegular-YqWj2.otf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            setUiFont(new FontUIResource(customFont.deriveFont(Font.PLAIN, 14f)));

        } catch (Exception e) {
            e.printStackTrace();
            setUiFont(new FontUIResource("SansSerif", Font.PLAIN, 14));
        }

        final AppBuilder builder = new AppBuilder();
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
