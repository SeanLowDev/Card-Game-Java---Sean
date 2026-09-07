package rummy.actor;

import ch.aplu.jgamegrid.Actor;

public class ActorAdaptor implements IActorAdaptor{
    Actor actor;
    public ActorAdaptor(Actor actor) {
        this.actor = actor;
    }
    public ActorAdaptor(String filename) {
        this.actor = new Actor(filename);
    }

    public Actor getActor() {
        return actor;
    }

}
