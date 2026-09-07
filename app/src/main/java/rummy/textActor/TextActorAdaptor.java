package rummy.textActor;

import ch.aplu.jgamegrid.TextActor;
import rummy.actor.ActorAdaptor;
import rummy.color.ColorAdaptor;
import rummy.color.IColorAdaptor;
import rummy.font.FontAdaptor;
import rummy.font.IFontAdaptor;

public class TextActorAdaptor extends ActorAdaptor implements ITextActorAdaptor{
    TextActor textActor;

    public TextActorAdaptor(String s, IColorAdaptor c1, IColorAdaptor c2, IFontAdaptor f){
        super(new TextActor(s, ((ColorAdaptor)c1).getColor(), ((ColorAdaptor)c2).getColor(), ((FontAdaptor)f).getFont()));
        this.textActor = (TextActor) getActor();
    }

    public TextActor getTextActor() {
        return textActor;
    }

}
