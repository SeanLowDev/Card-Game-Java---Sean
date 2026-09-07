package rummy.score;

public class ScoringResult {
    public final int winnerIndex;
    public final int points;

    // minimal result object for scoring

    public ScoringResult(int winnerIndex, int points) {
        this.winnerIndex = winnerIndex;
        this.points = points;
    }
}