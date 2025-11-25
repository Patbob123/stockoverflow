package view;

import javax.swing.*;
import java.awt.*;

public class StockResultPanel extends JPanel {
    private final JCheckBox checkBox;
    private final JLabel tickerLabel;
    private final JButton viewGraphButton;
    private final String ticker;

    public StockResultPanel(String ticker) {
        this.ticker = ticker;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        checkBox = new JCheckBox();
        tickerLabel = new JLabel(ticker);
        tickerLabel.setPreferredSize(new Dimension(100, 20));

        viewGraphButton = new JButton("View Graph");

        this.add(checkBox);
        this.add(tickerLabel);
        this.add(viewGraphButton);
    }

    public boolean isSelected() {
        return checkBox.isSelected();
    }

    public String getTicker() {
        return ticker;
    }

    public JButton getViewGraphButton() {
        return viewGraphButton;
    }
}