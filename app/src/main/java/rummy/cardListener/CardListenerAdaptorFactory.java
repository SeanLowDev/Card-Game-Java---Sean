package rummy.cardListener;

public class CardListenerAdaptorFactory {
    public static CardListenerAdaptor fromCustomListener(CustomCardListener listener) {
        return new CardListenerAdaptor(listener);
    }
}
