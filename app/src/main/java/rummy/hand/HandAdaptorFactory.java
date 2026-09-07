package rummy.hand;

import rummy.deck.IDeckAdaptor;

public class HandAdaptorFactory {
    public static IHandAdaptor fromDeck(IDeckAdaptor deck) {
        return new HandAdaptor(deck);
    }

    public static IHandAdaptor fromHand(ch.aplu.jcardgame.Hand hand) {
        return new HandAdaptor(hand);
    }

    public static IHandAdaptor[] createHands(IDeckAdaptor deck, int nbPlayers) {
        IHandAdaptor[] hands = new IHandAdaptor[nbPlayers];
        for (int i = 0; i < nbPlayers; i++) {
            hands[i] = new HandAdaptor(deck);
        }
        return hands;
    }

}
