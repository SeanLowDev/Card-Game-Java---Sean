package rummy.card;

import ch.aplu.jcardgame.Card;

public class LocalCardAdaptorFactory {
    public static ICardAdaptor fromCard(Card card) {
        return new LocalCardAdaptor(card);
    }
}
