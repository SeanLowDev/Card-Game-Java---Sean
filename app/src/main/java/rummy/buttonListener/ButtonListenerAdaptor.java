package rummy.buttonListener;

import ch.aplu.jgamegrid.GGButtonListener;
import rummy.button.ButtonAdaptor;

public class ButtonListenerAdaptor implements GGButtonListener {
    private final CustomButtonListener listener;

    public ButtonListenerAdaptor(CustomButtonListener listener) {
        this.listener = listener;
    }

    @Override
    public void buttonPressed(ch.aplu.jgamegrid.GGButton button) {
        listener.buttonPressed(new ButtonAdaptor(button));
    }

    @Override
    public void buttonReleased(ch.aplu.jgamegrid.GGButton button) {
        listener.buttonReleased(new ButtonAdaptor(button));
    }

    @Override
    public void buttonClicked(ch.aplu.jgamegrid.GGButton button) {
        listener.buttonClicked(new ButtonAdaptor(button));
    }
}
