package rummy.deck;

import ch.aplu.jcardgame.Deck;
import rummy.hand.HandAdaptor;
import rummy.hand.IHandAdaptor;
import rummy.Rank;
import rummy.Suit;

public class DeckAdaptor implements IDeckAdaptor{
    Deck deck;
    public DeckAdaptor(Suit[] suits, Rank[] ranks, String cover){
        deck = new Deck(suits, ranks, cover);
    }

    public IHandAdaptor toHand(boolean shuffle){
        return new HandAdaptor(deck.toHand());
    }

    public Deck getUnderlying(){
        return deck;
    }

}
