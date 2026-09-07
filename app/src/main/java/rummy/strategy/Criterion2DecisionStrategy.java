package rummy.strategy;

import rummy.card.ICardAdaptor;
import rummy.hand.IHandAdaptor;

import java.util.ArrayList;
import java.util.Comparator;


public class Criterion2DecisionStrategy implements IDecisionStrategy {
    public int decide(IHandAdaptor hand, ICardAdaptor card) {
        int currentMinGap = Integer.MAX_VALUE;
        hand.sort("RANK");
        hand.sort("SUIT");
        ArrayList<ICardAdaptor> cards = hand.getCardList();
        cards.sort(
                Comparator.comparing(ICardAdaptor::getSuit)
                        .thenComparingInt(ICardAdaptor::getRankNumber)
        );
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getSuit() == cards.get(i + 1).getSuit()) {
                currentMinGap = Math.min(currentMinGap, cards.get(i + 1).getRankNumber() - cards.get(i).getRankNumber());
            }
        }
        int newGap = Integer.MAX_VALUE;
        for (ICardAdaptor c : cards) {
            if (c.getSuit() == card.getSuit()) {
                newGap = Math.min(newGap,  Math.abs(card.getRankNumber() - c.getRankNumber()));
            }
        }
        if (newGap < currentMinGap) {
            return 1;
        }
        return 0;


    }
    public Criterion2DecisionStrategy() {}
}
