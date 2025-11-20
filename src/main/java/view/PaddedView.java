package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PaddedView extends JPanel {

    public PaddedView(int padding) {
        super();
        setBorder(new EmptyBorder(padding, padding, padding, padding));
    }

    public PaddedView(int top, int left, int bottom, int right) {
        super();
        setBorder(new EmptyBorder(top, left, bottom, right));
    }

    public PaddedView(LayoutManager layout, int padding) {
        super(layout);
        setBorder(new EmptyBorder(padding, padding, padding, padding));
    }

    public PaddedView(LayoutManager layout, int top, int left, int bottom, int right) {
        super(layout);
        setBorder(new EmptyBorder(top, left, bottom, right));
    }
}