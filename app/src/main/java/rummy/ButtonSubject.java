package rummy;

import java.util.ArrayList;

public class ButtonSubject {
    private ArrayList<ButtonObserver> observers;

    public ButtonSubject() {
        observers = new ArrayList<>();
    }

    public void addObserver(ButtonObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(ButtonObserver observer) {
        observers.remove(observer);
    }
    public void buttonPublish(ButtonEvent buttonEvent){
        for (ButtonObserver observer : observers) {
            observer.buttonUpdate(buttonEvent);
        }
    }
}
