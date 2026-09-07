package rummy;

import rummy.card.ICardAdaptor;
import rummy.hand.IHandAdaptor;

import java.util.ArrayList;
import java.util.Comparator;

public final class MeldHandler {

    public MeldHandler() {
    }

    private static class DeadwoodResult {
        int score;
        ArrayList<ICardAdaptor> deadwood;

        DeadwoodResult(int score, ArrayList<ICardAdaptor> deadwood) {
            this.score = score;
            this.deadwood = deadwood;
        }
    }



    public static int minimumDeadwood(IHandAdaptor hand) {
        hand.sort("RANK");
        ArrayList<ICardAdaptor> cards = hand.getCardList();
        cards.sort(Comparator.comparingInt(ICardAdaptor::getRankNumber));
        return minimumDeadwoodRec(cards).score;
    }

    public static ArrayList<ICardAdaptor> getDeadwoodCards(IHandAdaptor hand) {
        hand.sort("RANK");
        ArrayList<ICardAdaptor> cards = hand.getCardList();
        cards.sort(Comparator.comparingInt(ICardAdaptor::getRankNumber));
        return minimumDeadwoodRec(cards).deadwood;
    }

    private static DeadwoodResult minimumDeadwoodRec(ArrayList<ICardAdaptor> input) {
        ArrayList<ICardAdaptor> cards = new ArrayList<>(input);
        ArrayList<ICardAdaptor> original = new ArrayList<>(cards);

        if (cards.size() <= 2) {
            int score = 0;
            for (ICardAdaptor c : cards) score += Math.min(c.getRankNumber(), 10);
            return new DeadwoodResult(score, cards);
        }

        int minScore = Integer.MAX_VALUE;
        ArrayList<ICardAdaptor> bestDeadwood = new ArrayList<>();

        // ----- Try rank sequence -----

        int start = cards.get(0).getRankNumber();
        int current = cards.get(0).getRankNumber();
        Suit currentSuit = cards.get(0).getSuit();

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getRankNumber() == current && cards.get(i).getSuit() == currentSuit) {
                cards.remove(i);
                i--;
                current += 1;
                if (current - start >= 3) {
                    DeadwoodResult result = minimumDeadwoodRec(cards);
                    if (result.score < minScore) {
                        minScore = result.score;
                        bestDeadwood = result.deadwood;
                    }
                }
            }
        }

        // ----- Try sets (same rank, different suits) -----
        int counter = 0;
        cards = new ArrayList<>(original);
        for (int i = 0; i < Math.min(4, cards.size()); i++) {
            if (cards.get(i).getRankNumber() != start) {
                break;
            } else {
                cards.remove(i);
                counter++;
                i--;
                if (counter >= 3) {
                    DeadwoodResult result = minimumDeadwoodRec(cards);
                    if (result.score < minScore) {
                        minScore = result.score;
                        bestDeadwood = result.deadwood;
                    }
                }
            }
        }

        // ----- Try skipping the first card -----
        original.remove(0);
        DeadwoodResult skipResult = minimumDeadwoodRec(original);
        int skipScore = skipResult.score + Math.min(start, 10);
        if (skipScore < minScore) {
            minScore = skipScore;
            bestDeadwood = new ArrayList<>(skipResult.deadwood);
            bestDeadwood.add(input.get(0)); // card we skipped contributes to deadwood
        }

        return new DeadwoodResult(minScore, bestDeadwood);
    }


}
