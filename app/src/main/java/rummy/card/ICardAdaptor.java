package rummy.card;

import rummy.Rank;
import rummy.Suit;

public interface ICardAdaptor {
    int getRankNumber();
    Rank getRank();
    Suit getSuit();
    void removeFromHand(boolean reDraw);
}
