package rummy;

import rummy.card.ICardAdaptor;
import rummy.hand.IHandAdaptor;

import java.util.List;
import java.util.stream.Collectors;

public class GameLogger {

    private final StringBuilder logResult = new StringBuilder();

    private String cardDescriptionForLog(ICardAdaptor card) {
        Rank cardRank = (Rank) card.getRank();
        Suit cardSuit = (Suit) card.getSuit();
        return cardRank.getCardLog() + cardSuit.getSuitShortHand();
    }

    /**
     * Logging Logic
     * @param player
     * @param discardCard
     * @param pickupCard
     */

    public void addCardPlayedToLog(int player, ICardAdaptor discardCard, ICardAdaptor pickupCard, String action) {
        logResult.append("P" + player + "-");
        logResult.append(cardDescriptionForLog(getPickupCard(pickupCard)) + "-");
        logResult.append(cardDescriptionForLog(discardCard));
        if (action != null) {
            logResult.append("-" + action);
        }

        logResult.append(",");
    }

    private static ICardAdaptor getPickupCard(ICardAdaptor pickupCard) {
        return pickupCard;
    }

    public void addRoundInfoToLog(int roundNumber) {
        logResult.append("\n");
        logResult.append("Round" + roundNumber + ":");
    }

    public void addTurnInfoToLog(int turnNumber) {
        logResult.append("\n");
        logResult.append("Turn" + turnNumber + ":");
    }

    public void addPlayerCardsToLog(int nbPlayers, IHandAdaptor[] hands) {
        logResult.append("\n");
        logResult.append("Initial Cards:");
        for (int i = 0; i < nbPlayers; i++) {
            logResult.append("P" + i + "-");
            logResult.append(convertCardListoString(hands[i]));
        }
    }

    private String convertCardListoString(IHandAdaptor hand) {
        StringBuilder sb = new StringBuilder();
        sb.append(hand.getCardList().stream().map(card -> {
            Rank rank = (Rank) card.getRank();
            Suit suit = (Suit) card.getSuit();
            return rank.getCardLog() + suit.getSuitShortHand();
        }).collect(Collectors.joining(",")));
        sb.append("-");
        return sb.toString();
    }

    public void addEndOfRoundToLog(int currentRound, int[] scores) {
        logResult.append("\n");
        logResult.append("Round" + currentRound +  " End:P0-" + scores[0] + ",P1-" + scores[1]);
    }

    public void addEndOfGameToLog(List<Integer> winners) {
        logResult.append("\n");
        if (winners.size() > 1){
            logResult.append("Game End:P0, P1");
        }else{
            logResult.append("Game End:P" + winners.get(0).toString());
        }
    }

    public String getLogResult() {
        return logResult.toString();
    }
}
