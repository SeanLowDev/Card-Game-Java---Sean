package rummy;

import rummy.card.ICardAdaptor;

import java.util.ArrayList;

public class HandSubject {
    private ArrayList<HandObserver> observers;

    public HandSubject(){
        observers = new ArrayList<>();
    }

    public void addObserver(HandObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(HandObserver observer) {
        observers.remove(observer);
    }

    public void handPublish(HandEvent handEvent, ICardAdaptor card) {
        for (HandObserver observer : observers) {
            observer.handUpdate(handEvent, card);
        }
    }
}
