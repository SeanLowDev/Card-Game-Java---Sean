package rummy.hand;

import rummy.card.ICardAdaptor;
import rummy.cardGame.ICardGameAdaptor;
import rummy.cardListener.CustomCardListener;
import rummy.HandEvent;
import rummy.HandObserver;
import rummy.rowLayout.IRowLayoutAdaptor;
import rummy.targetArea.ITargetAreaAdaptor;

import java.util.ArrayList;

public interface IHandAdaptor {
    // Observer Logic
    public void addObserver(HandObserver handObserver);

    public void removeObserver(HandObserver handObserver);

    public void handPublish(HandEvent handEvent, ICardAdaptor card);

    // Sorting
    void sort(String priority);

    // Card operations
    int getNumberOfCards();
    void insert(ICardAdaptor card, boolean doDraw);
    void remove(ICardAdaptor card, boolean doDraw);
    ArrayList<ICardAdaptor> getCardList();
    boolean isEmpty();
    ICardAdaptor shuffle(boolean doDraw);

    // Display and interactivity
    void draw();
    void setTouchEnabled(boolean touchEnabled);
    void setView(ICardGameAdaptor cardGame, IRowLayoutAdaptor layout);
    void setTargetArea(ITargetAreaAdaptor targetArea);

    // Event handling
    void addCardListener(CustomCardListener listener);
}
