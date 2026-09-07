package rummy;
import rummy.actor.IActorAdaptor;
import rummy.card.ICardAdaptor;
import rummy.color.ColorAdaptor;
import rummy.color.ColorAdaptorFactory;
import rummy.deck.DeckAdaptorFactory;
import rummy.deck.IDeckAdaptor;
import rummy.font.FontAdaptor;
import rummy.font.FontAdaptorFactory;
import rummy.font.IFontAdaptor;
import rummy.cardListener.CustomCardListener;
import rummy.hand.HandAdaptorFactory;
import rummy.hand.IHandAdaptor;
import rummy.location.ILocationAdaptor;
import rummy.location.LocationAdaptorFactory;
import rummy.player.Player;
import rummy.rowLayout.IRowLayoutAdaptor;
import rummy.rowLayout.RowLayoutAdaptor;
import rummy.rowLayout.RowLayoutAdaptorFactory;
import rummy.textActor.ITextActorAdaptor;
import rummy.textActor.TextActorAdaptorFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class Dealer {
    public final int nbPlayers = 2;
    private final Rummy rummy;
    private final Properties properties;

    private final int COMPUTER_PLAYER_INDEX = 0;
    private final int HUMAN_PLAYER_INDEX = 1;

    private final IDeckAdaptor deck = DeckAdaptorFactory.create(Suit.values(), Rank.values(), "cover");
    private IHandAdaptor playingArea;
    private final int nbStartCards;

    private IHandAdaptor pack;
    private IHandAdaptor discard;
    private IHandAdaptor[] hands;

    private final ITextActorAdaptor[] scoreActors = {null, null};
    private final ITextActorAdaptor[] pileNameActors = {null, null, null, null};
    private ITextActorAdaptor packNameActor;
    private ITextActorAdaptor discardNameActor;

    IFontAdaptor bigFont = FontAdaptorFactory.create("Arial", FontAdaptor.BOLD, 36);
    IFontAdaptor smallFont = FontAdaptorFactory.create("Arial", FontAdaptor.BOLD, 18);

    private final ILocationAdaptor[] scoreLocations = {
            LocationAdaptorFactory.fromCoordinates(25, 25),
            LocationAdaptorFactory.fromCoordinates(575, 675),
    };

    private final ILocationAdaptor[] pileNameLocations = {
            LocationAdaptorFactory.fromCoordinates(25, 50),
            LocationAdaptorFactory.fromCoordinates(575, 625),
    };

    private final ILocationAdaptor[] handLocations = {
            LocationAdaptorFactory.fromCoordinates(350, 75),
            LocationAdaptorFactory.fromCoordinates(350, 625),
    };

    private final ILocationAdaptor playingLocation = LocationAdaptorFactory.fromCoordinates(350, 350);
    private final ILocationAdaptor packLocation = LocationAdaptorFactory.fromCoordinates(75, 350);
    private final ILocationAdaptor discardLocation = LocationAdaptorFactory.fromCoordinates(625, 350);
    private final ILocationAdaptor packNameLocation = LocationAdaptorFactory.fromCoordinates(30, 280);
    private final ILocationAdaptor discardNameLocation = LocationAdaptorFactory.fromCoordinates(560, 280);

    private final int handWidth = 400;
    private final int pileWidth = 40;
    private final int cardWidth = 40;

    public Dealer(Rummy rummy) {
        this.rummy = rummy;
        this.properties = rummy.getProperties();
        this.nbStartCards = rummy.getNbStartCards();
    }

    /**
     * Score Section
     */

    public void initScore(int[] scores) {
        for (int i = 0; i < nbPlayers; i++) {
            String text = "[P" + i + ": " + scores[i] + "]";
            scoreActors[i] = TextActorAdaptorFactory.create(text, ColorAdaptorFactory.white(), rummy.getbgColor(), bigFont);
            rummy.addActor((IActorAdaptor) scoreActors[i], scoreLocations[i]);
        }

        pileNameActors[0] = TextActorAdaptorFactory.create("Computer", ColorAdaptorFactory.white(), rummy.getbgColor(), smallFont);
        rummy.addActor((IActorAdaptor)pileNameActors[0], pileNameLocations[0]);

        pileNameActors[1] = TextActorAdaptorFactory.create("Human", ColorAdaptorFactory.white(), rummy.getbgColor(), smallFont);
        rummy.addActor((IActorAdaptor)pileNameActors[1], pileNameLocations[1]);
    }

    public void updateScore(int player, int[] scores) {
        rummy.removeActor((IActorAdaptor)scoreActors[player]);
        int displayScore = Math.max(scores[player], 0);
        String text = "P" + player + "[" + String.valueOf(displayScore) + "]";
        scoreActors[player] = TextActorAdaptorFactory.create(text, ColorAdaptorFactory.white(), rummy.getbgColor(), bigFont);
        rummy.addActor((IActorAdaptor)scoreActors[player], scoreLocations[player]);
    }

    public void initScores(int[] scores) {
        Arrays.fill(scores, 0);
    }

    private void setupPiles() {
        discard = HandAdaptorFactory.fromDeck(deck);
        IRowLayoutAdaptor discardLayout = new RowLayoutAdaptor(discardLocation, pileWidth);
        discardLayout.setRotationAngle(270);
        discard.setView(rummy, discardLayout);
        discard.draw();
        discardNameActor = TextActorAdaptorFactory.create("Discard Pile", ColorAdaptorFactory.white(), rummy.getbgColor(), smallFont);
        rummy.addActor((IActorAdaptor)discardNameActor, discardNameLocation);

        IRowLayoutAdaptor packLayout = new RowLayoutAdaptor(packLocation, pileWidth);
        packLayout.setRotationAngle(90);
        pack.setView(rummy, packLayout);
        pack.draw();
        packNameActor = TextActorAdaptorFactory.create("Stockpile", ColorAdaptor.WHITE, rummy.getbgColor(), smallFont);
        rummy.addActor((IActorAdaptor) packNameActor, packNameLocation);

        discard.addCardListener(new CustomCardListener() {
            @Override
            public void leftDoubleClicked(ICardAdaptor card) {
                discard.handPublish(HandEvent.DRAWN, card);
                discard.remove(card, true);
                discard.setTouchEnabled(false);
                discard.draw();
            }
        });
        discard.addObserver(rummy.getHumanPlayer());

        pack.addCardListener(new CustomCardListener() {
            @Override
            public void leftDoubleClicked(ICardAdaptor card) {
                discard.handPublish(HandEvent.DRAWN, card);
                pack.remove(card, true);
                pack.setTouchEnabled(false);
                pack.draw();
            }
        });

        pack.addObserver(rummy.getHumanPlayer());
    }

    public void initRound(int currentRound, Player[] players) {
        hands = HandAdaptorFactory.createHands(deck, nbPlayers);
        setPlayerHands(players);

        dealingOut(hands, currentRound);

        for (int i = 0; i < nbPlayers; i++) {
            GameUtils.sortHand(hands[i]);
        }

        arrangeStockpile(currentRound);

        playingArea = HandAdaptorFactory.fromDeck(deck);

        playingArea.setView(rummy, RowLayoutAdaptorFactory.fromLocation(playingLocation, (playingArea.getNumberOfCards() + 3) * cardWidth));
        playingArea.draw();

        // Set up human player for interaction
        CustomCardListener cardListener = new CustomCardListener()  // Human Player plays card
        {
            public void leftDoubleClicked(ICardAdaptor card) {
                hands[HUMAN_PLAYER_INDEX].handPublish(HandEvent.SELECTED, card);
                hands[HUMAN_PLAYER_INDEX].setTouchEnabled(false);
            }
        };

        hands[HUMAN_PLAYER_INDEX].addCardListener(cardListener);
        hands[HUMAN_PLAYER_INDEX].addObserver(rummy.getHumanPlayer());

        IRowLayoutAdaptor[] layouts = RowLayoutAdaptorFactory.createLayouts(hands, handLocations, handWidth,
                rummy, playingLocation);

        setupPiles();
    }

    private void arrangeStockpile(int currentRound) {
        String roundString = "rounds." + currentRound;
        String stockpileKey = roundString + ".stockpile.cards";
        pack.shuffle(false);
        String topCardsValue = properties.getProperty(stockpileKey);
        if (topCardsValue == null) {
            return;
        }
        String[] topCards = topCardsValue.split(",");
        for (int i = topCards.length - 1; i >= 0; i--) {
            String topCard = topCards[i];
            if (topCard.length() <= 1) {
                continue;
            }
            ICardAdaptor card = GameUtils.getCardFromList(pack.getCardList(), topCard);
            List<ICardAdaptor> cardList = pack.getCardList();
            if (card != null) {
                card.removeFromHand(false);
                pack.insert(card, false);
            }
        }
    }

    private void dealingOut(IHandAdaptor[] hands, int currentRound) {
        pack = deck.toHand(false);
        String roundString = "rounds." + currentRound;
        for (int i = 0; i < nbPlayers; i++) {
            String initialCardsKey = roundString + ".players." + i + ".initialcards";
            String initialCardsValue = properties.getProperty(initialCardsKey);
            if (initialCardsValue == null) {
                continue;
            }
            String[] initialCards = initialCardsValue.split(",");
            for (String initialCard : initialCards) {
                if (initialCard.length() <= 1) {
                    continue;
                }
                ICardAdaptor card = GameUtils.getCardFromList(pack.getCardList(), initialCard);
                if (card != null) {
                    card.removeFromHand(false);
                    hands[i].insert(card, false);
                }
            }
        }

        for (int i = 0; i < nbPlayers; i++) {
            int cardsToDealt = nbStartCards - hands[i].getNumberOfCards();
            for (int j = 0; j < cardsToDealt; j++) {
                if (pack.isEmpty()) return;
                ICardAdaptor dealt = GameUtils.randomCard(pack.getCardList());
                dealt.removeFromHand(false);
                hands[i].insert(dealt, false);
            }
        }
    }

    public IHandAdaptor getPack(){
        return pack;
    }
    public IHandAdaptor[] getHands(){
        return hands;
    }
    public IHandAdaptor getDiscard(){
        return discard;
    }

    private void setPlayerHands(Player[] players) {
        for (int i = 0; i < nbPlayers; i++) {
            players[i].setHand(hands[i]);
        }
    }

}
