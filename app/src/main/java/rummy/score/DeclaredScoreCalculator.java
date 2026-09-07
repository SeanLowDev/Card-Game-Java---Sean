package rummy.score;

import rummy.hand.IHandAdaptor;
import rummy.MeldHandler;


public class DeclaredScoreCalculator implements IScoreCalculator {
    private final int declarerIndex;

    public DeclaredScoreCalculator(int declarerIndex) {
        this.declarerIndex = declarerIndex;
    }

    @Override
    public ScoringResult calculate(IHandAdaptor player0, IHandAdaptor player1){
        int opponentIndex = 1 - declarerIndex;
        IHandAdaptor opponentHand = (opponentIndex == 0) ? player0 : player1;
        // Points = the deadwood total of the opponent
        int points = MeldHandler.minimumDeadwood(opponentHand);
        return new ScoringResult(declarerIndex,points);
    }
}
