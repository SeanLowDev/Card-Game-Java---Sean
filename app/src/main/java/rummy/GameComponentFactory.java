package rummy;

import java.util.Properties;

import rummy.button.ButtonAdaptorFactory;
import rummy.button.IButtonAdaptor;
import rummy.buttonListener.CustomButtonListener;
import rummy.location.ILocationAdaptor;
import rummy.location.LocationAdaptorFactory;
import rummy.player.*;

import static rummy.ButtonEvent.*;

public class GameComponentFactory {
    private final int COMPUTER_PLAYER_INDEX = 0;
    private final int HUMAN_PLAYER_INDEX = 1;

    private final String CLASSIC = "classic";
    private final String GIN = "gin";

    private final IButtonAdaptor rummyActor = ButtonAdaptorFactory.fromFile("sprites/rummy.gif", false);
    private final ILocationAdaptor rummyLocation = LocationAdaptorFactory.fromCoordinates(80, 650);

    private final IButtonAdaptor ginActor = ButtonAdaptorFactory.fromFile("sprites/gin.gif", false);
    private final ILocationAdaptor ginLocation = LocationAdaptorFactory.fromCoordinates(80, 650);

    private final IButtonAdaptor endTurnActor = ButtonAdaptorFactory.fromFile("sprites/end.gif", false);
    private final ILocationAdaptor endTurnLocation = LocationAdaptorFactory.fromCoordinates(80, 610);

    private final IButtonAdaptor knockActor = ButtonAdaptorFactory.fromFile("sprites/knock.gif", false);
    private final ILocationAdaptor knockLocation = LocationAdaptorFactory.fromCoordinates(80, 690);

    private static GameComponentFactory instance;

    private Human human;

    public static GameComponentFactory getInstance() {
        if (instance == null) {
            instance = new GameComponentFactory();
        }
        return instance;
    }

    public Player[] createPlayers(Properties properties, int nbPlayers, Rummy rummy) {
        boolean computer_smart = Boolean.parseBoolean(properties.getProperty("computer_smart"));
        Player[] players = new Player[nbPlayers];
        String mode = properties.getProperty("mode");
        if (mode.equals(GIN)){
            players[HUMAN_PLAYER_INDEX] = new HumanGin(rummy);
        }else{
            players[HUMAN_PLAYER_INDEX] = new Human(rummy);
        }


        human = (Human) players[HUMAN_PLAYER_INDEX];
        if (computer_smart) {
            if (mode.equals(GIN)){

                players[COMPUTER_PLAYER_INDEX] = new SmartComputerGin(rummy);
            }else{
                players[COMPUTER_PLAYER_INDEX] = new SmartComputer(rummy);
            }
        } else {
            players[COMPUTER_PLAYER_INDEX] = new Computer(rummy);
        }
        return players;
    }

    public void setUpButtons(Properties properties, Rummy rummy) {
        // add END button to rummy
        addButton(endTurnActor, endTurnLocation, rummy, END);

        String mode = properties.getProperty("mode");
        if (mode.equals(CLASSIC)) {
            addButton(rummyActor, rummyLocation, rummy, RUMMY);

        } else if (mode.equals(GIN)) {
            addButton(knockActor, knockLocation, rummy, KNOCK);
            addButton(ginActor, ginLocation, rummy, ButtonEvent.GIN);

        }

    }

    public void addButton(IButtonAdaptor button, ILocationAdaptor location, Rummy rummy, ButtonEvent event) {
        rummy.addActor(button, location);
        button.addButtonListener(new CustomButtonListener() {
            @Override
            public void buttonPressed(IButtonAdaptor ggButton) {
                button.buttonPublish(event);
            }
            @Override
            public void buttonReleased(IButtonAdaptor ggButton) {
            }
            @Override
            public void buttonClicked(IButtonAdaptor ggButton) {
            }
        });
        button.addObserver(human);
    }

    public IButtonAdaptor getEndTurnActor(){
        return endTurnActor;
    }

}
