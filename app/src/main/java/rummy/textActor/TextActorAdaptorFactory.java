package rummy.textActor;

import rummy.color.IColorAdaptor;
import rummy.font.IFontAdaptor;

public class TextActorAdaptorFactory {
    public static ITextActorAdaptor create(String text, IColorAdaptor foreground, IColorAdaptor background, IFontAdaptor font) {
        return new TextActorAdaptor(text, foreground, background, font);
    }
}
