package rummy.player;

import rummy.card.ICardAdaptor;
import rummy.CardAction;
import rummy.GameUtils;
import rummy.hand.IHandAdaptor;
import rummy.Rummy;

import java.util.List;

public abstract class Player{
    public IHandAdaptor hand;
    public Rummy rummy;

    public ICardAdaptor selected; // card selected to discard
    public ICardAdaptor drawnCard; // card drawn from pile

    public abstract CardAction makeMove(IHandAdaptor discard, IHandAdaptor stockpile);

    public abstract CardAction attemptedEnd();

    public Player(Rummy rummy){
        this.rummy = rummy;
    }

    public void setHand(IHandAdaptor hand){
        this.hand = hand;
    }

    public void discardCardFromHand(ICardAdaptor card) {
        card.removeFromHand(false);
        rummy.getDealer().getDiscard().insert(card, false);
        rummy.getDealer().getDiscard().draw();
        hand.draw();
    }

    public void drawCardToHand(ICardAdaptor drawnCard) {
        hand.insert(drawnCard, false);
        GameUtils.sortHand(hand);
        hand.draw();
    }

    public ICardAdaptor processTopCardFromPile(IHandAdaptor pile, IHandAdaptor hand) {
        rummy.delay();
        ICardAdaptor card = GameUtils.dealTopCard(pile);
        pile.remove(card, false);
        pile.draw();
        hand.insert(card, false);
        GameUtils.sortHand(hand);
        hand.draw();
        return card;
    }

    public CardAction makeAutoMove(int nextPlayer, List<List<String>> playerAutoMovements, int[] autoIndexHands){
        int nextPlayerAutoIndex = autoIndexHands[nextPlayer];
        List<String> nextPlayerMovement = playerAutoMovements.get(nextPlayer);
        String nextMovement = "";
        boolean hasRunAuto = false;


        if (nextPlayerMovement.size() > nextPlayerAutoIndex) {
            nextMovement = nextPlayerMovement.get(nextPlayerAutoIndex);
            if (!nextMovement.isEmpty()) {
                hasRunAuto = true;
                nextPlayerAutoIndex++;

                autoIndexHands[nextPlayer] = nextPlayerAutoIndex;
                rummy.setStatus("Player " + nextPlayer + " is playing");
                List<CardAction> cardActions = GameUtils.getActionFromAutoMovement(nextMovement);
                CardAction cardAction = cardActions.get(0);
                if (cardAction == CardAction.DISCARD) {
                    drawnCard = processTopCardFromPile(rummy.getDealer().getDiscard(), hand);
                    selected = GameUtils.getCardElementFromAutoMovement(hand, nextMovement);
                    discardCardFromHand(selected);
                } else if (cardAction == CardAction.STOCKPILE) {
                    drawnCard = processTopCardFromPile(rummy.getDealer().getPack(), hand);
                    selected = GameUtils.getCardElementFromAutoMovement(hand, nextMovement);
                    discardCardFromHand(selected);
                }else{
                    System.out.println(cardAction);
                }


                rummy.delay();
                if (cardActions.size() > 1) {
                    CardAction lastAction = cardActions.get(1);
                    if (lastAction == CardAction.RUMMY) {
                        rummy.setStatus("Player " + nextPlayer + " is rummy...");
                        rummy.getLogger().addCardPlayedToLog(nextPlayer, selected, drawnCard, lastAction.name());
                        return lastAction;
                    } else if (lastAction == CardAction.GIN) {
                        rummy.setStatus("Player " + nextPlayer + " is ginning...");
                        rummy.getLogger().addCardPlayedToLog(nextPlayer, selected, drawnCard, lastAction.name());
                        return lastAction;
                    } else if (lastAction == CardAction.KNOCK) {
                        rummy.setStatus("Player " + nextPlayer + " is knocking...");
                        rummy.getLogger().addCardPlayedToLog(nextPlayer, selected, drawnCard, lastAction.name());
                        return lastAction;
                    }
                } else {
                    rummy.getLogger().addCardPlayedToLog(nextPlayer, selected, drawnCard, null);
                }
                rummy.delay();
            }

        }

        if (!hasRunAuto) {
            CardAction action = makeMove(rummy.getDealer().getDiscard(), rummy.getDealer().getPack());
            rummy.setIsAutoArr(nextPlayer,false);
            rummy.getLogger().addCardPlayedToLog(nextPlayer, selected, drawnCard, action.name());
            return action;
        }
        return CardAction.NONE;
    }

    public ICardAdaptor getDrawnCard(){
        return drawnCard;
    }
    public ICardAdaptor getSelectedCard(){
        return selected;
    }
    public IHandAdaptor getHand(){
        return hand;
    }
}
