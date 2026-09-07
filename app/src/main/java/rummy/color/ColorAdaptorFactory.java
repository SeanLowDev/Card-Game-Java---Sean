package rummy.color;

import java.awt.*;

public class ColorAdaptorFactory {

    public static IColorAdaptor fromColor(Color color) {
        return new ColorAdaptor(color);
    }

    public static IColorAdaptor white() {
        return new ColorAdaptor(java.awt.Color.WHITE);
    }
    public static IColorAdaptor black() {
        return new ColorAdaptor(java.awt.Color.BLACK);
    }
}
