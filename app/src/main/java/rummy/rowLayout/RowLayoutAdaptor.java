package rummy.rowLayout;

import ch.aplu.jcardgame.HandLayout;
import ch.aplu.jcardgame.RowLayout;
import rummy.location.ILocationAdaptor;
import rummy.location.LocationAdaptor;

public class RowLayoutAdaptor extends HandLayout implements IRowLayoutAdaptor{
    RowLayout rowLayout;
    public RowLayoutAdaptor(RowLayout rowLayout){
        this.rowLayout = rowLayout;
    }
    public RowLayoutAdaptor(ILocationAdaptor location, int pileWidth){
        this.rowLayout = new RowLayout(((LocationAdaptor)location).getLocation(), pileWidth);
    }

    public void setRotationAngle(double rotationAngle){
        rowLayout.setRotationAngle(rotationAngle);
    }

    public RowLayout getRowLayout() {
        return rowLayout;
    }
}
