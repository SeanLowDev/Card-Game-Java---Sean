package rummy.cardListener;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import rummy.card.LocalCardAdaptor;

public class CardListenerAdaptor extends CardAdapter {
    CustomCardListener listener;

    public CardListenerAdaptor(CustomCardListener listener) {
        this.listener = listener;
    }

    @Override
    public void leftDoubleClicked(Card card) {
        listener.leftDoubleClicked(new LocalCardAdaptor(card));
    }
}
