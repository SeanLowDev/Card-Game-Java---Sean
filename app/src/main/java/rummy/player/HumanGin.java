package rummy.player;

import rummy.ButtonEvent;
import rummy.CardAction;
import rummy.MeldHandler;
import rummy.Rummy;

import static rummy.ButtonEvent.*;

public class HumanGin extends Human{

    public void buttonUpdate(ButtonEvent buttonEvent) {
        if (buttonEvent == GIN){
            if (MeldHandler.minimumDeadwood(hand) == 0) {
                setAction(CardAction.RUMMY);
            }

        }else if (buttonEvent == END){
            setAction(CardAction.NONE);
        }else if (buttonEvent == KNOCK){
            setAction(CardAction.KNOCK);
        }
    }

    public HumanGin(Rummy rummy){
        super(rummy);
    }
}
