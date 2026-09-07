package rummy.score;

import rummy.hand.IHandAdaptor;
import rummy.MeldHandler;


public class KnockScoreCalculator implements IScoreCalculator{
    private final int knockerIndex;

    public KnockScoreCalculator(int knockerIndex){
        this.knockerIndex = knockerIndex;
    }

    @Override
    public ScoringResult calculate(IHandAdaptor player0, IHandAdaptor player1){
        IHandAdaptor knocker;
        IHandAdaptor opponent;
        // player0 is knocker,player1 is opponent
        if (knockerIndex == 0){
            knocker = player0;
            opponent = player1;
        }else {
            // player1 is knocker,player0 is opponent
            knocker = player1;
            opponent = player0;
        }

        // get minimum deadwood for each player
        int deadwoodKnocker = MeldHandler.minimumDeadwood(knocker);
        int deadwoodOpponent = MeldHandler.minimumDeadwood(opponent);

        // equal deadwood, means no points
        if (deadwoodOpponent == deadwoodKnocker){
            return new ScoringResult(knockerIndex,0);
        }

        // handle case knock valid and knocker has less deadwood than opponent
        if(deadwoodKnocker < deadwoodOpponent){
            int pointsGiven = deadwoodOpponent - deadwoodKnocker;
            return new ScoringResult(knockerIndex,pointsGiven);
        }

        // handle case knocker called a false knock (deadwoodKnocker has higher value than opponent),
        // then opponent wins the difference in points between the two players
        int opponentIndex = 1 - knockerIndex;
        int pointsGiven = deadwoodKnocker - deadwoodOpponent;
        return new ScoringResult(opponentIndex,pointsGiven);
    }
}
