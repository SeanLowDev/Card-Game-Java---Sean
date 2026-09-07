package rummy.cardGame;

import rummy.actor.IActorAdaptor;
import rummy.card.ICardAdaptor;
import rummy.color.IColorAdaptor;
import rummy.hand.IHandAdaptor;
import rummy.location.ILocationAdaptor;

public interface ICardGameAdaptor{
    // Window settings
    void setTitle(String title);

    // Status text
    void setStatusText(String text);
    void setStatus(String text);

    // Delay
    static void delay(int time) {} // Static methods in interfaces are allowed in Java 8+, but can’t be overridden

    // Actor handling
    void addActor(IActorAdaptor actor, ILocationAdaptor loc);
    void removeActor(IActorAdaptor actor);

    // Display
    void refresh();

    // Hand operations
    void drawHand(IHandAdaptor hand);
    void insertCardToHand(IHandAdaptor hand, ICardAdaptor card, boolean visible);

    // Background color
    IColorAdaptor getbgColor();
}
