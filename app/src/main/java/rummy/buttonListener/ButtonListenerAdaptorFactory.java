package rummy.buttonListener;

public class ButtonListenerAdaptorFactory {
    public static ButtonListenerAdaptor fromCustomListener(CustomButtonListener listener) {
        return new ButtonListenerAdaptor(listener);
    }
}
