package rummy.buttonListener;

import rummy.button.IButtonAdaptor;

public interface CustomButtonListener {
    void buttonPressed(IButtonAdaptor button);
    void buttonReleased(IButtonAdaptor button);
    void buttonClicked(IButtonAdaptor button);
}
