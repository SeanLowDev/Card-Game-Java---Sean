package rummy;

import rummy.card.ICardAdaptor;

public interface HandObserver {
    public abstract void handUpdate(HandEvent handEvent, ICardAdaptor cardAdaptor);
}
