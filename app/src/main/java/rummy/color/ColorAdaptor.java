package rummy.color;

import java.awt.*;

public class ColorAdaptor implements IColorAdaptor{
    Color color;
    public static final ColorAdaptor WHITE = new ColorAdaptor(Color.WHITE);
    public static final ColorAdaptor BLACK = new ColorAdaptor(Color.BLACK);

    public ColorAdaptor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }

}
