package rummy.button;

import ch.aplu.jgamegrid.GGButton;

public class ButtonAdaptorFactory {
    public static IButtonAdaptor fromButton(GGButton button) {
        return new ButtonAdaptor(button);
    }

    public static IButtonAdaptor fromFile(String filename, boolean isRollover) {
        return new ButtonAdaptor(filename, isRollover);
    }
}
