package rummy.location;

import ch.aplu.jgamegrid.Location;

public class LocationAdaptorFactory {
    public static ILocationAdaptor fromCoordinates(int x, int y) {
        return new LocationAdaptor(x, y);
    }

    public static ILocationAdaptor fromLocation(Location location) {
        return new LocationAdaptor(location);
    }
}
