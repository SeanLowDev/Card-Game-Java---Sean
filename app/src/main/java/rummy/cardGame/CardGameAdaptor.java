package rummy.cardGame;

import ch.aplu.jcardgame.CardGame;
import rummy.actor.ActorAdaptor;
import rummy.actor.IActorAdaptor;
import rummy.card.ICardAdaptor;
import rummy.color.ColorAdaptor;
import rummy.color.IColorAdaptor;
import rummy.hand.IHandAdaptor;
import rummy.location.ILocationAdaptor;
import rummy.location.LocationAdaptor;

public class CardGameAdaptor implements ICardGameAdaptor {
    private CardGame game;

    public CardGameAdaptor(int width, int height, int statusHeight) {
        this.game = new CardGame(width, height, statusHeight);
    }

    // Window settings
    public void setTitle(String title) {
        game.setTitle(title);
    }

    // Status text
    public void setStatusText(String text) {
        game.setStatusText(text);
    }

    public void setStatus(String text) {
        setStatusText(text);
    }

    // Delay
    public static void delay(int time) {
        CardGame.delay(time);
    }

    public void addActor(IActorAdaptor actor, ILocationAdaptor loc) {
        game.addActor(((ActorAdaptor) actor).getActor(), ((LocationAdaptor)loc).getLocation());
    }

    public void refresh() {
        game.refresh();
    }

    // HandAdaptor helpers
    public void drawHand(IHandAdaptor hand) {
        hand.draw();
    }

    public void insertCardToHand(IHandAdaptor hand, ICardAdaptor card, boolean visible) {
        hand.insert(card, visible);
    }

    public IColorAdaptor getbgColor(){
        return new ColorAdaptor(game.bgColor);
    }

    public CardGame getCardGame(){
        return game;
    }

    public void removeActor(IActorAdaptor actor){
        game.removeActor(((ActorAdaptor)actor).getActor());
    }

}
