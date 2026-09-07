package rummy.location;
import ch.aplu.jgamegrid.Location;

public class LocationAdaptor implements ILocationAdaptor{
    private final Location location;

    public LocationAdaptor(int x, int y) {
        this.location = new Location(x, y);
    }

    public LocationAdaptor(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public int getX() {
        return location.x;
    }

    public int getY() {
        return location.y;
    }
}
