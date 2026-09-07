package rummy.hand;

import ch.aplu.jcardgame.*;
import rummy.card.ICardAdaptor;
import rummy.card.LocalCardAdaptor;
import rummy.cardGame.CardGameAdaptor;
import rummy.cardGame.ICardGameAdaptor;
import rummy.cardListener.CardListenerAdaptor;
import rummy.deck.DeckAdaptor;
import rummy.cardListener.CustomCardListener;
import rummy.deck.IDeckAdaptor;
import rummy.HandEvent;
import rummy.HandObserver;
import rummy.HandSubject;
import rummy.rowLayout.IRowLayoutAdaptor;
import rummy.rowLayout.RowLayoutAdaptor;
import rummy.targetArea.ITargetAreaAdaptor;
import rummy.targetArea.TargetAreaAdaptor;

import java.util.ArrayList;

import static ch.aplu.jcardgame.Hand.SortType.RANKPRIORITY;
import static ch.aplu.jcardgame.Hand.SortType.SUITPRIORITY;

public class HandAdaptor implements IHandAdaptor {
    Hand hand;

    HandSubject handSubject;

    public void addObserver(HandObserver handObserver){
        handSubject.addObserver(handObserver);
    }
    public void removeObserver(HandObserver handObserver){
        handSubject.removeObserver(handObserver);
    }
    public void handPublish(HandEvent handEvent, ICardAdaptor card){
        handSubject.handPublish(handEvent, card);
    }

    public HandAdaptor(IDeckAdaptor deck){
        this.hand = new Hand( ((DeckAdaptor)deck).getUnderlying());
        handSubject = new HandSubject();
    }

    public HandAdaptor(Hand hand){
        this.hand = hand;
        handSubject = new HandSubject();
    }

    public void sort(String priority){
        if (priority.equals("RANK")){
            hand.sort(RANKPRIORITY, false);
        }else if (priority.equals("SUIT")){
            hand.sort(SUITPRIORITY, false);
        }
    }

    public int getNumberOfCards(){
        return hand.getNumberOfCards();
    }

    public void insert(ICardAdaptor card, boolean doDraw){
        hand.insert(((LocalCardAdaptor)card).getCard(), doDraw);
    }
    public void remove(ICardAdaptor card, boolean doDraw){
        hand.remove(((LocalCardAdaptor) card).getCard(), doDraw);
    }

    public ArrayList<ICardAdaptor> getCardList(){

        ArrayList<Card> tempCards = hand.getCardList();
        ArrayList<ICardAdaptor> localCardAdaptors = new ArrayList<ICardAdaptor>();
        for (Card card : tempCards){
            localCardAdaptors.add(new LocalCardAdaptor(card));
        }

        return localCardAdaptors;
    }

    public void draw(){
        hand.draw();
    }
    public void setTouchEnabled(boolean touchEnabled){
        hand.setTouchEnabled(touchEnabled);
    }
    public void setView(ICardGameAdaptor cardGame, IRowLayoutAdaptor layout){
        hand.setView(((CardGameAdaptor)cardGame).getCardGame(), ((RowLayoutAdaptor)layout).getRowLayout());
    }
    public void setTargetArea(ITargetAreaAdaptor targetArea){
        hand.setTargetArea(((TargetAreaAdaptor) targetArea).getTargetArea());
    }
    public boolean isEmpty(){
        return hand.isEmpty();
    }

    public void addCardListener(CustomCardListener listener) {
        // Internally wrap it into the library type
        hand.addCardListener(new CardListenerAdaptor(listener));
    }

    public ICardAdaptor shuffle(boolean doDraw){
            return new LocalCardAdaptor(hand.shuffle(doDraw));
    }

}
