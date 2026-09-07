package rummy.score;

import rummy.hand.IHandAdaptor;

public interface IScoreCalculator {
    ScoringResult calculate(IHandAdaptor player0, IHandAdaptor player1);
}

