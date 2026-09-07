package rummy.targetArea;

import ch.aplu.jcardgame.TargetArea;
import rummy.location.ILocationAdaptor;
import rummy.location.LocationAdaptor;

public class TargetAreaAdaptor implements ITargetAreaAdaptor{
    TargetArea targetArea;
    public TargetAreaAdaptor(TargetArea targetArea){
        this.targetArea = targetArea;
    }
    public TargetAreaAdaptor(ILocationAdaptor location){
        this.targetArea = new TargetArea(((LocationAdaptor)location).getLocation());
    }

    public TargetArea getTargetArea(){
        return targetArea;
    }
}
