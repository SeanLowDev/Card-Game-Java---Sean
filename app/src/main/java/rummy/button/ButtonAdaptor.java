package rummy.button;

import ch.aplu.jgamegrid.GGButton;
import rummy.actor.ActorAdaptor;
import rummy.ButtonEvent;
import rummy.buttonListener.ButtonListenerAdaptor;
import rummy.buttonListener.CustomButtonListener;
import rummy.ButtonObserver;
import rummy.ButtonSubject;

public class ButtonAdaptor extends ActorAdaptor implements IButtonAdaptor{
    GGButton button;
    ButtonSubject subject;

    public void addObserver(ButtonObserver observer){
        subject.addObserver(observer);
    }

    public void removeObserver(ButtonObserver observer){
        subject.removeObserver(observer);
    }

    public void buttonPublish(ButtonEvent buttonEvent){
        subject.buttonPublish(buttonEvent);
    }


    public ButtonAdaptor(GGButton button) {
        super(button);
        this.button = button;
        subject = new ButtonSubject();
    }
    public ButtonAdaptor(String filename, boolean isRollover) {
        super(new GGButton(filename, isRollover));
        this.button = (GGButton) getActor();
        subject = new ButtonSubject();
    }

    public void addButtonListener(CustomButtonListener listener) {
        button.addButtonListener(new ButtonListenerAdaptor(listener));
    }


    public void setMouseTouchEnabled(boolean enabled) {
        button.setMouseTouchEnabled(enabled);
    }
}
