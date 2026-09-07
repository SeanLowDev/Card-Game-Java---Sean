package rummy.font;

import java.awt.*;

public class FontAdaptorFactory {
    public static IFontAdaptor fromFont(Font font) {
        return new FontAdaptor(font);
    }

    public static IFontAdaptor create(String fontName, int fontStyle, int fontSize) {
        return new FontAdaptor(fontName, fontStyle, fontSize);
    }
}
