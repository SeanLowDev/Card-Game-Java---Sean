package rummy.strategy;

import rummy.card.ICardAdaptor;
import rummy.hand.IHandAdaptor;
import rummy.Suit;

import java.util.HashMap;
import java.util.Map;

public class Criterion3DecisionStrategy implements IDecisionStrategy {
    public int decide(IHandAdaptor hand, ICardAdaptor card){
        Map<Suit, Integer> counts = new HashMap<>();
        for (Suit suit : Suit.values()) {
            counts.put(suit, 0);
        }
        for (ICardAdaptor c: hand.getCardList()){
            counts.put(c.getSuit(), counts.get(c.getSuit()) + 1);
        }
        int suitOfInterest = counts.get(card.getSuit());
        for (Integer values: counts.values()){
            if (suitOfInterest < values){return 0;}
        }
        return 1;
    }
    public Criterion3DecisionStrategy(){}
}
