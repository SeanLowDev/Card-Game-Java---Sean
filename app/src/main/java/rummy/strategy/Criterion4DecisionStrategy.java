package rummy.strategy;

import rummy.card.ICardAdaptor;
import rummy.hand.IHandAdaptor;
import rummy.MeldHandler;
import rummy.Rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Criterion4DecisionStrategy implements IDecisionStrategy {
    @Override
    public int decide(IHandAdaptor hand, ICardAdaptor card) {


        ArrayList<ICardAdaptor> deadwoodCards = MeldHandler.getDeadwoodCards(hand);
        int originalRepeats = numberOfRepeats(deadwoodCards);


        ArrayList<ICardAdaptor> originalCards = hand.getCardList();

        for (ICardAdaptor c: originalCards){
            ArrayList<ICardAdaptor> tempCards = new ArrayList<>(originalCards);
            tempCards.remove(c);
            tempCards.add(card);
            if (numberOfRepeats(tempCards) > originalRepeats){
                return 1;
            }
        }

        return 0;
    }

    private int numberOfRepeats(ArrayList<ICardAdaptor> cards) {
        Map<Rank, Integer> counts = new HashMap<>();

        for (Rank rank : Rank.values()) {
            counts.put(rank, 0);
        }

        for (ICardAdaptor c: cards){
            counts.put(c.getRank(), counts.get(c.getRank()));
        }
        int score = 0;
        for (Integer value: counts.values()){
            if (value >= 2){
                score += value -1;
            }
        }
        return score;
    }

    public Criterion4DecisionStrategy() {}
}
