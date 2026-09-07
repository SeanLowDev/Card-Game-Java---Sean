package rummy.player;

import rummy.*;
import rummy.card.ICardAdaptor;
import rummy.deck.DeckAdaptorFactory;
import rummy.hand.HandAdaptorFactory;
import rummy.hand.IHandAdaptor;
import rummy.strategy.DecisionStrategyFactory;
import rummy.strategy.IDecisionStrategy;

import java.util.*;

public class SmartComputer extends Player{

    @Override
    public CardAction makeMove(IHandAdaptor discard, IHandAdaptor stockpile) {
        IDecisionStrategy criterion = DecisionStrategyFactory.getInstance().getDecision();

        if (step1(discard, stockpile, criterion)) {
            step3(discard, stockpile, criterion);
        }else{
            if (step2(discard, stockpile, criterion)) {
                step3(discard, stockpile, criterion);
            }
        }
        return attemptedEnd();
    }

    public boolean step1(IHandAdaptor discard, IHandAdaptor stockpile, IDecisionStrategy criterion){
        ICardAdaptor topCard = null;
        if (discard.getNumberOfCards() > 0) {
            topCard = discard.getCardList().get(discard.getNumberOfCards() - 1);
        }
        boolean flag = false;
        // Step 1
        if (topCard != null && criterion.decide(hand, topCard) >= 1){
            hand.insert(topCard, false);
            discard.remove(topCard, false);
            drawnCard = topCard;
            return true;
        }
        return false;
    }

    public boolean step2(IHandAdaptor discard, IHandAdaptor stockpile, IDecisionStrategy criterion){
        ICardAdaptor topCard = stockpile.getCardList().get(stockpile.getNumberOfCards() - 1);
        if (criterion.decide(hand, topCard) == 0){
            stockpile.remove(topCard, false);
            discard.insert(topCard, false);
            stockpile.draw();
            discard.draw();
            drawnCard = topCard;
            selected = topCard;
            return false;
        }else{
            hand.insert(topCard, false);
            stockpile.remove(topCard, false);
            drawnCard = topCard;
            return true;
        }
    }

    public void step3(IHandAdaptor discard, IHandAdaptor stockpile, IDecisionStrategy criterion){
        ArrayList<ICardAdaptor> deadwood =  MeldHandler.getDeadwoodCards(hand);
        int minVal = Integer.MAX_VALUE;
        ICardAdaptor bestCard = null;
        for (int i = 0; i < deadwood.size(); i++){
            IHandAdaptor tempHand = HandAdaptorFactory.fromDeck(DeckAdaptorFactory.create(Suit.values(), Rank.values(), "cover"));
            for (int j = 0; j < deadwood.size(); j++){
                if (i != j){
                    tempHand.insert(deadwood.get(j), false);
                }
            }
            int newVal = criterion.decide(tempHand, deadwood.get(i));
            if (minVal > newVal){
                minVal = newVal;
                bestCard = deadwood.get(i);
            }else if (minVal == newVal){
                // Finding the lowest frequency suits
                HashMap<Suit, Integer> suitCount = new HashMap<>();
                for (Suit suit : Suit.values()){
                    suitCount.put(suit, 0);
                }
                for (ICardAdaptor card : deadwood){
                    suitCount.put(card.getSuit(), suitCount.get(card.getSuit()) + 1);
                }

                if (suitCount.get(bestCard.getSuit()) > suitCount.get(deadwood.get(i).getSuit())){
                    bestCard = deadwood.get(i);
                } else if (Objects.equals(suitCount.get(bestCard.getSuit()), suitCount.get(deadwood.get(i).getSuit()))){
                    if (bestCard.getRankNumber() < deadwood.get(i).getRankNumber()){
                        bestCard = deadwood.get(i);
                    }
                }
            }
        }

        if (bestCard != null){
            hand.remove(bestCard, false);
            discard.insert(bestCard, false);
            hand.draw();
            discard.draw();
            selected = bestCard;
        }
    }

    public CardAction attemptedEnd(){
        if (MeldHandler.getDeadwoodCards(hand).size() == 0){
            return CardAction.RUMMY;
        }
        return CardAction.NONE;

    }

    public SmartComputer(Rummy rummy){
        super(rummy);
    }
}
