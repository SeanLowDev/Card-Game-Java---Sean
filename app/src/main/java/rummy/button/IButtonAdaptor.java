package rummy.button;

import rummy.actor.IActorAdaptor;
import rummy.ButtonEvent;
import rummy.buttonListener.CustomButtonListener;
import rummy.ButtonObserver;

public interface IButtonAdaptor extends IActorAdaptor {
    public void addObserver(ButtonObserver observer);

    public void removeObserver(ButtonObserver observer);

    public void buttonPublish(ButtonEvent buttonEvent);
    void addButtonListener(CustomButtonListener listener);
    void setMouseTouchEnabled(boolean enabled);
}
