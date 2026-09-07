package rummy.targetArea;

import ch.aplu.jcardgame.TargetArea;
import rummy.location.ILocationAdaptor;

public class TargetAreaAdaptorFactory {
    public static ITargetAreaAdaptor fromTargetArea(TargetArea targetArea) {
        return new TargetAreaAdaptor(targetArea);
    }

    public static ITargetAreaAdaptor fromLocation(ILocationAdaptor location) {
        return new TargetAreaAdaptor(location);
    }
}
