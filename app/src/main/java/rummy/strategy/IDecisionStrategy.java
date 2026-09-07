package rummy.strategy;

import rummy.card.ICardAdaptor;
import rummy.hand.IHandAdaptor;

public interface IDecisionStrategy {
    public int decide(IHandAdaptor hand, ICardAdaptor card);
}
