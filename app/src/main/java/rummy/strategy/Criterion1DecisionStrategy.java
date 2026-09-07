package rummy.strategy;

import rummy.card.ICardAdaptor;
import rummy.hand.IHandAdaptor;

public class Criterion1DecisionStrategy implements IDecisionStrategy {
    public int decide(IHandAdaptor hand, ICardAdaptor card) {
        // Sets
        int count = 0;
        for (ICardAdaptor c : hand.getCardList()) {
            if (c.getRankNumber() == card.getRankNumber()) {
                count ++;
            }
        }
        if (count >= 2) {
            return 1;
        }

        // Runs
        boolean b2 = false;
        boolean b1 = false;
        boolean a1 = false;
        boolean a2 = false;
        for  (ICardAdaptor c : hand.getCardList()) {
            if (c.getRankNumber() == card.getRankNumber() - 2) {
                b2 = true;
            }
            if  (c.getRankNumber() == card.getRankNumber() - 1) {
                b1 = true;
            }
            if (c.getRankNumber() == card.getRankNumber() + 1) {
                a1 = true;
            }
            if (c.getRankNumber() == card.getRankNumber() + 2) {
                a2 = true;
            }
        }

        if ((b2 && b1) || (b1 && a1) || (a1 && a2) ) {return 1;}

        return 0;
    }

    public Criterion1DecisionStrategy() {}
}
