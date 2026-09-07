package rummy.player;

import rummy.*;
import rummy.card.ICardAdaptor;
import rummy.hand.IHandAdaptor;

import java.util.Random;

public class Computer extends Player {
    private final int COMPUTER_PLAYER_INDEX = 0;

    public void buttonUpdate(ButtonEvent buttonEvent) {
        //
    }

    public void handUpdate(HandEvent handEvent, ICardAdaptor card) {
        //
    }

    public CardAction makeMove(IHandAdaptor discard, IHandAdaptor stockpile){
        rummy.setStatusText("Player " + COMPUTER_PLAYER_INDEX + " thinking...");
        if (!discard.isEmpty()) {
            boolean isPickingDiscard = new Random().nextBoolean();
            if (isPickingDiscard) {
                rummy.setStatusText("Player " + COMPUTER_PLAYER_INDEX + " is picking a card from discard pile...");
                drawnCard = processTopCardFromPile(discard, hand);
            } else {
                rummy.setStatusText("Player " + COMPUTER_PLAYER_INDEX + " is picking a card from stockpile...");
                drawnCard = processTopCardFromPile(stockpile, hand);
            }
        } else {
            rummy.setStatusText("Player " + COMPUTER_PLAYER_INDEX + " is picking a card from stockpile...");
            drawnCard = processTopCardFromPile(stockpile, hand);
        }
        rummy.delay();
        selected = GameUtils.getRandomCard(hand);
        discardCardFromHand(selected);
        return attemptedEnd();
    }

    public CardAction attemptedEnd(){
        if (MeldHandler.minimumDeadwood(hand) == 0){
            return CardAction.RUMMY;
        }
        return CardAction.NONE;
    }

    public Computer(Rummy rummy){
        super(rummy);
    }
}
