package rummy;

import ch.aplu.jcardgame.*;
import rummy.actor.ActorAdaptorFactory;
import rummy.cardGame.CardGameAdaptor;
import rummy.hand.IHandAdaptor;
import rummy.location.ILocationAdaptor;
import rummy.location.LocationAdaptorFactory;
import rummy.player.Human;
import rummy.player.Player;
import rummy.score.IScoreCalculator;
import rummy.score.ScoreCalculatorFactory;
import rummy.score.ScoringResult;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@SuppressWarnings("serial")
public class Rummy extends CardGameAdaptor {

    private final Properties properties;
    private final GameLogger logger = new GameLogger();
    private final Dealer dealer;

    private final List<List<String>> playerAutoMovements = new ArrayList<>();
    private final int COMPUTER_PLAYER_INDEX = 0;
    private final int HUMAN_PLAYER_INDEX = 1;
    private Player[] players;

    private final String version = "1.0";
    public final int nbPlayers = 2;
    public int nbStartCards = 13;
    private int currentRound = 0;
    private int thinkingTime = 300;

    private int firstPlayerIndex = HUMAN_PLAYER_INDEX;

    private final ILocationAdaptor textLocation = LocationAdaptorFactory.fromCoordinates(350, 450);
    private int delayTime = 600;

    public void setStatus(String string) {
        setStatusText(string);
    }

    private final int[] scores = new int[nbPlayers];
    private final int[] autoIndexHands = new int[nbPlayers];
    private boolean isAuto = false;
    private ArrayList<Boolean> isAutoArr = new ArrayList<>();

    private boolean isEndingTurn = false;


    public void setTouchEnableIfNotNull(IHandAdaptor hand, boolean isEnabled) {
        if (hand != null) {
            hand.setTouchEnabled(isEnabled);
        }
    }


    private CardAction processNonAutoPlaying(int nextPlayer, IHandAdaptor hand) {
        CardAction action = players[nextPlayer].makeMove(dealer.getDiscard(), dealer.getPack());
        logger.addCardPlayedToLog(nextPlayer, players[nextPlayer].getSelectedCard(),
                players[nextPlayer].getDrawnCard(), null);
        return action;
    }

    private boolean playARound() {
        int nextPlayer = firstPlayerIndex;
        logger.addRoundInfoToLog(currentRound);
        logger.addPlayerCardsToLog(nbPlayers, dealer.getHands());
        int i = 0;
        boolean isContinue = true;
        setupPlayerAutoMovements();
        autoIndexHands[0] = 0;
        autoIndexHands[1] = 0;
        while (isContinue) {

            logger.addTurnInfoToLog(i);
            for (int j = 0; j < nbPlayers; j++) {
                IHandAdaptor hand = dealer.getHands()[nextPlayer];
                boolean flag = false;
                if (isAutoArr.get(nextPlayer)) {
                    CardAction action = players[nextPlayer].makeAutoMove(nextPlayer, playerAutoMovements, autoIndexHands);
                    if (action != CardAction.NONE) {
                        IScoreCalculator calculator = ScoreCalculatorFactory.getFor(action, nextPlayer, 0);
                        ScoringResult result = calculator.calculate(players[0].getHand(), players[1].getHand());
                        scores[result.winnerIndex] += result.points;
                        firstPlayerIndex = result.winnerIndex;
                        isContinue = false;
                        break;
                    }
                    flag = true;
                }

                if (!isAutoArr.get(nextPlayer) && !flag) {
                    CardAction action = processNonAutoPlaying(nextPlayer, hand);
                    if (action != CardAction.NONE) {
                        IScoreCalculator calculator = ScoreCalculatorFactory.getFor(action, nextPlayer, 0);
                        ScoringResult result = calculator.calculate(players[0].getHand(), players[1].getHand());
                        scores[result.winnerIndex] += result.points;
                        firstPlayerIndex = result.winnerIndex;
                        isContinue = false;
                        break;
                    }
                }

                if (dealer.getPack().isEmpty()) {
                    setStatus("Stockpile is exhausted. Calculating players' scores now.");
                    IScoreCalculator calculator = ScoreCalculatorFactory.getFor(CardAction.NONE, nextPlayer, 0);
                    ScoringResult result = calculator.calculate(players[0].getHand(), players[1].getHand());
                    scores[result.winnerIndex] += result.points;
                    firstPlayerIndex = result.winnerIndex;
                    isContinue = false;
                }
                nextPlayer = (nextPlayer + 1) % nbPlayers;
            }
            i++;
        }
        logger.addEndOfRoundToLog(currentRound, scores);
        if (scores[0] >= 100 || scores[1] >= 100){
            return false;
        }
        return true;
    }

    private void setupPlayerAutoMovements() {
        playerAutoMovements.clear();
        String roundString = "rounds." + currentRound;
        String player0AutoMovement = properties.getProperty(roundString + ".players.0.cardsPlayed");
        String player1AutoMovement = properties.getProperty(roundString + ".players.1.cardsPlayed");
        String[] playerMovements = new String[]{"", ""};
        if (player0AutoMovement != null) {
            playerMovements[0] = player0AutoMovement;
        }

        if (player1AutoMovement != null) {
            playerMovements[1] = player1AutoMovement;
        }

        for (int i = 0; i < playerMovements.length; i++) {
            String movementString = playerMovements[i];
            List<String> movements = Arrays.asList(movementString.split(","));
            playerAutoMovements.add(movements);
        }
    }


    public String runApp() {
        setTitle("Pinochle  (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
        setStatusText("Initializing...");
        dealer.initScores(scores);
        dealer.initScore(scores);

        currentRound = 0;
        boolean isContinue = true;
        while(isContinue) {
            for (Integer s : scores) {
                System.out.println("Score: " + s);
            }
            dealer.initRound(currentRound, players);
            System.out.println("Round: " + currentRound);
            isContinue = playARound();
            currentRound++;
        }

        for (int i = 0; i < nbPlayers; i++) dealer.updateScore(i, scores);
        int maxScore = 0;
        for (int i = 0; i < nbPlayers; i++) if (scores[i] > maxScore) maxScore = scores[i];
        List<Integer> winners = new ArrayList<Integer>();
        for (int i = 0; i < nbPlayers; i++) if (scores[i] == maxScore) winners.add(i);
        String winText;
        if (winners.size() == 1) {
            winText = "Game over. Winner is player: " +
                    winners.iterator().next();
        } else {
            winText = "Game Over. Drawn winners are players: " +
                    String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toList()));
        }
        addActor(ActorAdaptorFactory.fromFile("sprites/gameover.gif"), textLocation);
        setStatusText(winText);
        refresh();
        logger.addEndOfGameToLog(winners);

        return logger.getLogResult();
    }

    public Rummy(Properties properties) {
        super(700, 700, 30);
        this.properties = properties;
        isAuto = Boolean.parseBoolean(properties.getProperty("isAuto"));
        isAutoArr.add(isAuto);
        isAutoArr.add(isAuto);
        thinkingTime = Integer.parseInt(properties.getProperty("thinkingTime", "200"));
        delayTime = Integer.parseInt(properties.getProperty("delayTime", "50"));
        nbStartCards = Integer.parseInt(properties.getProperty("number_cards", "13"));
        this.dealer = new Dealer(this);
        this.players = GameComponentFactory.getInstance().createPlayers(properties, nbPlayers, this);
        GameComponentFactory.getInstance().setUpButtons(properties, this);
        firstPlayerIndex = HUMAN_PLAYER_INDEX;
    }

    public Properties getProperties() {
        return this.properties;
    }
    public int getNbStartCards() {
        return nbStartCards;
    }

    public Dealer getDealer() {
        return dealer;
    }
    public boolean isEndingTurn() {
        return isEndingTurn;
    }
    public void setEndingTurn(boolean endingTurn) {
        isEndingTurn = endingTurn;
    }
    public void delay() {
        CardGame.delay(delayTime);
    }

    public Human getHumanPlayer(){
        return (Human) players[HUMAN_PLAYER_INDEX];
    }
    public GameLogger getLogger(){
        return logger;
    }

    public void setIsAutoArr(int nextPlayer,boolean isAuto) {
        this.isAutoArr.set(nextPlayer, isAuto);
    }

    public void setIsAuto(boolean isAuto) {
        this.isAuto = isAuto;
    }

}
