package rummy.cardGame;

public class CardGameAdaptorFactory {
    public static ICardGameAdaptor create(int width, int height, int statusHeight) {
        return new CardGameAdaptor(width, height, statusHeight);
    }
}
