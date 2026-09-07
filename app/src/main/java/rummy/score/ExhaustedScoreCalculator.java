package rummy.score;

import rummy.hand.IHandAdaptor;
import rummy.MeldHandler;

/*
 * Manage scoring when the stockpile is exhausted
 *  player with the lower deadwood wins, and earns their points from
 * their opponenets deadwood val
 *
 * */
public class ExhaustedScoreCalculator implements IScoreCalculator {
    @Override
    public ScoringResult calculate(IHandAdaptor player0, IHandAdaptor player1){
        int player0Deadwood = MeldHandler.minimumDeadwood(player0);
        int player1Deadwood = MeldHandler.minimumDeadwood(player1);
        // if deadwood scores equal no points gained.
        if (player0Deadwood == player1Deadwood){
            return new ScoringResult(0,0);
        }

        // handle case where lower deadwood wins
        if (player0Deadwood < player1Deadwood){
            return new ScoringResult(0,player1Deadwood);
        }else {
            return new ScoringResult(1,player0Deadwood);
        }
    }
}
