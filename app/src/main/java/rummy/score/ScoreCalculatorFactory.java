package rummy.score;

public class ScoreCalculatorFactory {

    // when RUMMY or GIN is called...
    public static IScoreCalculator getDeclared(int declarerIndex) {
        return new DeclaredScoreCalculator(declarerIndex);
    }

    // stockpile exhausted:
    public static IScoreCalculator getExhausted() {
        return new ExhaustedScoreCalculator();
    }

    // when player does a KNOCK for GIN RUMMY check
    public static IScoreCalculator getKnock(int knockerIndex,int knockLimit) {
        return new KnockScoreCalculator(knockerIndex);
    }

    // provide correct score calculator based on the round.
    public static IScoreCalculator getFor(rummy.CardAction roundResultReason, Integer playerIndex, Integer knockLimit){
        switch (roundResultReason){
            case RUMMY:
            case  GIN:
                if (playerIndex == null){
                    throw new RuntimeException("playerIndex is null");
                }
                return getDeclared(playerIndex);

            case KNOCK:
                if (playerIndex == null || knockLimit == null){
                    throw new RuntimeException("Knock parameters are null");
                }
                return getKnock(playerIndex, knockLimit);
            case NONE:
                return getExhausted();
            default:
                throw new RuntimeException("round result reason not known: " + roundResultReason);
        }
    }

}
