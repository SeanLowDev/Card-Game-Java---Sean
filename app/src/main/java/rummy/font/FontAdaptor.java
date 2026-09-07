package rummy.font;

import java.awt.*;

public class FontAdaptor implements IFontAdaptor{
    Font font;
    public static final int BOLD = Font.BOLD;
    public static final int ITALIC = Font.ITALIC;

    public FontAdaptor(Font font){
        this.font = font;
    }


    public FontAdaptor(String fontName, int fontStyle, int fontSize ){
        this.font = new Font(fontName, fontStyle, fontSize);
    }

    public Font getFont(){
        return this.font;
    }
}
