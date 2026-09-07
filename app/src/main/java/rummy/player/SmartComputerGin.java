package rummy.player;

import rummy.CardAction;
import rummy.MeldHandler;
import rummy.Rummy;

public class SmartComputerGin extends SmartComputer{

    public SmartComputerGin(Rummy rummy){
        super(rummy);

    }

    @Override
    public CardAction attemptedEnd(){
        if (MeldHandler.getDeadwoodCards(hand).size() == 0){
            return CardAction.GIN;
        }else if (MeldHandler.minimumDeadwood(hand) <= 7){
            return CardAction.KNOCK;
        }
        return CardAction.NONE;

    }
}
