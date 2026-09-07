package rummy.strategy;

import rummy.card.ICardAdaptor;
import rummy.hand.IHandAdaptor;

import java.util.ArrayList;

public class CountDecisionStrategy implements IDecisionStrategy {
    ArrayList<IDecisionStrategy> decisionCriterions;

    public int decide(IHandAdaptor hand, ICardAdaptor card){
        int count = 0;
        for(IDecisionStrategy decisionCriterion : decisionCriterions){
            count += decisionCriterion.decide(hand,card);
        }
        return count;
    }

    public void add(IDecisionStrategy criterion){
        decisionCriterions.add(criterion);
    }

    public CountDecisionStrategy(){
        decisionCriterions =  new ArrayList<>();
    }
}
