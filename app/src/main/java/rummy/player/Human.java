package rummy.player;

import rummy.*;
import rummy.card.ICardAdaptor;
import rummy.hand.IHandAdaptor;

import static rummy.ButtonEvent.END;
import static rummy.ButtonEvent.RUMMY;
import static rummy.HandEvent.DRAWN;
import static rummy.HandEvent.SELECTED;

public class Human extends Player implements ButtonObserver, HandObserver {
    private final int HUMAN_PLAYER_INDEX = 1;

    private CardAction action;

    public Human(Rummy rummy){
        super(rummy);
    }

    public void handUpdate(HandEvent handEvent, ICardAdaptor card){
        if (handEvent == SELECTED){
            selected = card;
        } else if (handEvent == DRAWN){
            drawnCard = card;
        }
    }

    public void buttonUpdate(ButtonEvent buttonEvent) {
        if (buttonEvent == RUMMY){
            if (MeldHandler.minimumDeadwood(hand) == 0) {
                action = CardAction.RUMMY;
            }

        }else if (buttonEvent == END){
            action = CardAction.NONE;
        }
    }

    public CardAction makeMove(IHandAdaptor discard, IHandAdaptor stockpile){
        if (!discard.isEmpty()) {
            rummy.setStatus("Player " + HUMAN_PLAYER_INDEX + " is playing. Please double click on a pile to draw");
            waitingForHumanToSelectPile(stockpile, discard);
        } else {
            rummy.setStatus("Player " + HUMAN_PLAYER_INDEX + " is playing first. Please double click on the stockpile to draw");
            waitingForHumanToSelectPile(stockpile, null);
        }
        drawCardToHand(drawnCard);

        rummy.setStatus("Player " + HUMAN_PLAYER_INDEX + " is playing. Please double click on a card in hand to select");

        waitingForHumanToSelectCard();
        discardCardFromHand(selected);

        rummy.setStatus("Player " + HUMAN_PLAYER_INDEX + " is playing. Please click on end turn of declare rummy/gin/knock");
        return attemptedEnd();
    }

    public CardAction attemptedEnd(){
        waitingForHumanToEndTurn();
        return action;
    }

    private void waitingForHumanToSelectCard() {
        hand.setTouchEnabled(true);
        selected = null;
        while (null == selected) rummy.delay();
        hand.setTouchEnabled(false);
    }

    private void waitingForHumanToSelectPile(IHandAdaptor pile1, IHandAdaptor pile2) {
        rummy.setTouchEnableIfNotNull(pile1, true);
        rummy.setTouchEnableIfNotNull(pile2, true);
        drawnCard = null;
        while (null == drawnCard) rummy.delay();
    }

    private void waitingForHumanToEndTurn() {
        GameComponentFactory.getInstance().getEndTurnActor().setMouseTouchEnabled(true);
        rummy.setEndingTurn(false);
        action = null;
        while (action == null) {
            rummy.delay();
        }
        GameComponentFactory.getInstance().getEndTurnActor().setMouseTouchEnabled(false);
    }

    public void setAction(CardAction action){
        this.action = action;
    }
}
