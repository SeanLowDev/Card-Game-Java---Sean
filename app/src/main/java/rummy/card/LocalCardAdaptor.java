package rummy.card;

import ch.aplu.jcardgame.Card;
import rummy.Rank;
import rummy.Suit;

public class LocalCardAdaptor implements ICardAdaptor {
    Card card;


    public LocalCardAdaptor(Card card) {
        this.card = card;
    }

    public int getRankNumber(){
        return ((Rank) card.getRank()).getShortHandValue();
    }

    public Rank getRank(){
        return (Rank) card.getRank();
    }

    public Suit getSuit(){
        return (Suit) card.getSuit();
    }

    public Card getCard(){
        return card;
    }

    public void removeFromHand(boolean reDraw){
        card.removeFromHand(reDraw);
    }


}
