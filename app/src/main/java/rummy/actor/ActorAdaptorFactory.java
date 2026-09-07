package rummy.actor;

import ch.aplu.jgamegrid.Actor;

public class ActorAdaptorFactory {
    public static IActorAdaptor fromActor(Actor actor) {
        return new ActorAdaptor(actor);
    }

    public static IActorAdaptor fromFile(String filename) {
        return new ActorAdaptor(filename);
    }
}
