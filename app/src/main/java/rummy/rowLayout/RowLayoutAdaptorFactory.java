package rummy.rowLayout;

import ch.aplu.jcardgame.RowLayout;
import rummy.cardGame.ICardGameAdaptor;
import rummy.hand.IHandAdaptor;
import rummy.location.ILocationAdaptor;
import rummy.targetArea.TargetAreaAdaptor;

public class RowLayoutAdaptorFactory {
    public static IRowLayoutAdaptor fromRowLayout(RowLayout rowLayout) {
        return new RowLayoutAdaptor(rowLayout);
    }

    public static IRowLayoutAdaptor fromLocation(ILocationAdaptor location, int pileWidth) {
        return new RowLayoutAdaptor(location, pileWidth);
    }

    public static IRowLayoutAdaptor[] createLayouts(
            IHandAdaptor[] hands,
            ILocationAdaptor[] handLocations,
            int handWidth,
            ICardGameAdaptor game,
            ILocationAdaptor playingLocation
    ) {
        int nbPlayers = hands.length;
        IRowLayoutAdaptor[] layouts = new IRowLayoutAdaptor[nbPlayers];

        for (int i = 0; i < nbPlayers; i++) {
            layouts[i] = new RowLayoutAdaptor(handLocations[i], handWidth);
            layouts[i].setRotationAngle(i);
            hands[i].setView(game, layouts[i]);
            hands[i].setTargetArea(new TargetAreaAdaptor(playingLocation));
            hands[i].draw();
        }

        return layouts;
    }
}
