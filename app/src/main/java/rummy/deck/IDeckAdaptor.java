package rummy.deck;

import rummy.hand.IHandAdaptor;

public interface IDeckAdaptor {
    IHandAdaptor toHand(boolean shuffle);
}
