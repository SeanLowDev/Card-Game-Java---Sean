package rummy.customCardAdaptor;

import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardListener;

public abstract class CustomCardAdaptor implements ICustomCardAdaptor {
    CardAdapter cardAdaptor;
    public CustomCardAdaptor() {
        this.cardAdaptor = new CardAdapter();
    }

    public CardListener getListener() {
        return cardAdaptor;
    }


}
