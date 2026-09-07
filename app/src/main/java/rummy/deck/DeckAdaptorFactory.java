package rummy.deck;

import rummy.Rank;
import rummy.Suit;

public class DeckAdaptorFactory {
    public static IDeckAdaptor create(Suit[] suits, Rank[] ranks, String cover) {
        return new DeckAdaptor(suits, ranks, cover);
    }
}
