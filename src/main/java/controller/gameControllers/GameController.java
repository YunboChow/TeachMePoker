package controller.gameControllers;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import controller.SceneController;
import controller.aiControllers.Ai;
import controller.SPController;
import controller.Sound;
import javafx.scene.control.*;
import javafx.scene.text.*;
import model.Card;
import model.Scenes;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.swing.*;

/**
 * @version 1.0
 * @author Amin Harirchian, Vedrana Zeba, Lykke Levin, Rikard Almgren
 * @version 1.1
 * @author Anthon Haväng, Jens Bjerre, Erik Larsson
 */

public class GameController {


    @FXML
    private ImageView btCheck, btCall, btFold, btRaise, btAllIn;
    @FXML
    private Slider slider;
    @FXML
    private Label lbPlayerAction, lbPotValue, lbAllIn;
    @FXML
    private Pane powerBarArea, playerCardsArea, tableCardArea;
    @FXML
    private Label adviceLabel, helpLabel, userName, raiseLabel;
    @FXML
    private ImageView imgRoundStatus;
    @FXML
    private Pane paneRounds;

    @FXML
    private ImageView imgPlayerOneCards;
    @FXML
    private ImageView imgPlayerTwoCards;
    @FXML
    private ImageView imgPlayerThreeCards;
    @FXML
    private ImageView imgPlayerFourCards;
    @FXML
    private ImageView imgPlayerFiveCards;

    @FXML
    private Label labelPlayerOneName;
    @FXML
    private Label labelPlayerTwoName;
    @FXML
    private Label labelPlayerThreeName;
    @FXML
    private Label labelPlayerFourName;
    @FXML
    private Label labelPlayerFiveName;

    @FXML
    private Label labelPlayerOnePot;
    @FXML
    private Label labelPlayerTwoPot;
    @FXML
    private Label labelPlayerThreePot;
    @FXML
    private Label labelPlayerFourPot;
    @FXML
    private Label labelPlayerFivePot;

    @FXML
    private Label labelPlayerOneAction;
    @FXML
    private Label labelPlayerTwoAction;
    @FXML
    private Label labelPlayerThreeAction;
    @FXML
    private Label labelPlayerFourAction;
    @FXML
    private Label labelPlayerFiveAction;

    @FXML
    private ImageView imgCard1;
    @FXML
    private ImageView imgCard2;
    @FXML
    private ImageView imgCard3;
    @FXML
    private ImageView imgCard4;
    @FXML
    private ImageView imgCard5;
    @FXML
    private ImageView imgCard6;
    @FXML
    private ImageView imgCard7;

    @FXML
    private ImageView ivBigBlind;
    @FXML
    private ImageView ivSmallBlind;
    @FXML
    private ImageView ivDealer;

    //logg
    @FXML
    public ScrollPane logScrollPane;
    @FXML
    private TextFlow logTextFlow;
    @FXML
    public Text logText;

    @FXML
    public ImageView ivSound;
    @FXML
    public MenuItem miNewGame;
    @FXML
    public MenuItem miClose;
    @FXML
    public MenuItem miSettings;
    @FXML
    public MenuItem miAbout;
    @FXML
    public MenuItem miTutorial;

    @FXML
    public Pane panePot;
    @FXML
    public Label subPotOne;
    @FXML
    public Label subPotTwo;
    @FXML
    public Label subPotThree;
    @FXML
    public Label subPotFour;
    @FXML
    public Label subPotFive;
    @FXML
    public Label subPotSix;
    @FXML
    public Label mainPot;
    @FXML
    private ImageView handsWonImage;
    @FXML
    private Label handsWonLabel;
    @FXML
    private TextField handsWonField;

    private WinnerBox winnerBox;
    private int powerBarValue = 0;
    private Image image;
    private ArrayList<Card> cards = new ArrayList<Card>();
    private Hand hand;
    private int tablePotValue;
    private int playerPot = 0;
    private int alreadyPaid = 0;
    private ImageView imgPowerBar = new ImageView();
    private SPController spController;
    private boolean playerMadeDecision = false;
    private boolean isReady = false;
    private String decision;
    private Card card1;
    private Card card2;
    private int handStrength;
    private LinkedList<Ai> aiPlayers;
    private Label[][] collectionOfLabelsAi;
    private ImageView[] collectionOfCardsAi;
    private ImageView[] collectionOfCardsTable;
    private int[][] aiPositions;
    private int highCard;
    private int prevPlayerActive;
    private String winnerHand = " ";
    private int AllInViability = 0;
    private Label[] collectionOfPots;
    private int handsWon;
    private int matchesWon;

    private HashSet<Integer> unchangableImages = new HashSet<>();


    /**
     * Method for initializing FXML
     */
    public void initialize() {

        // Groups together labels for each AI-position.
        this.collectionOfLabelsAi =
                new Label[][]{{labelPlayerOneName, labelPlayerOnePot, labelPlayerOneAction},
                        {labelPlayerTwoName, labelPlayerTwoPot, labelPlayerTwoAction},
                        {labelPlayerThreeName, labelPlayerThreePot, labelPlayerThreeAction},
                        {labelPlayerFourName, labelPlayerFourPot, labelPlayerFourAction},
                        {labelPlayerFiveName, labelPlayerFivePot, labelPlayerFiveAction}};


        // Placeholders for the AI (based on their position). Shows their
        // cardbacks/no cards or
        // highlighted cards (AI-frame).
        this.collectionOfPots = new Label[6];

        this.collectionOfCardsAi = new ImageView[]{imgPlayerOneCards, imgPlayerTwoCards,
                imgPlayerThreeCards, imgPlayerFourCards, imgPlayerFiveCards};

        // Used to place AI-players into the right position depending on the
        // chosen number of AI:s.
        this.aiPositions = new int[][]{{2}, {0, 2, 4}, {0, 1, 2, 3, 4, 5}};

        // Table cards placeholders.
        this.collectionOfCardsTable =
                new ImageView[]{imgCard3, imgCard4, imgCard5, imgCard6, imgCard7};

        // Used by method: inactivateAllAiCardGlows and aiAction.
        this.prevPlayerActive = -1;

    }


    /**
     * Used to show labels and AI-frame.
     *
     * @param position Position on the screen (0-4).
     */
    public void setShowUIAiBar(int position) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                collectionOfLabelsAi[position][0].setVisible(true);
                collectionOfLabelsAi[position][1].setVisible(true);
                collectionOfLabelsAi[position][2].setVisible(true);
                collectionOfCardsAi[position].setVisible(true);
            }
        });

    }


    /**
     * Used to change AI-label "name" based on position.
     *
     * @param position Position on the screen (0-4).
     * @param name     The label for the AI's name.
     */
    public void setLabelUIAiBarName(int position, String name) {

        collectionOfLabelsAi[position][0].setText(name);
    }


    /**
     * Used to change AI-label "pot" based on position.
     *
     * @param position Position on the screen (0-4).
     * @param pot      The label for the AI's pot.
     * Amended by: Anthon Haväng, Erik Larsson, Jens Bjerre, replaced § with $
     */
    public void setLabelUIAiBarPot(int position, String pot) {

        collectionOfLabelsAi[position][1].setText("$" + pot);
    }


    /**
     * Used to change AI-label "action" based on position.
     *
     * @param position Position on the screen (0-4).
     * @param action   The label for the AI's action.
     */
    public void setLabelUIAiBarAction(int position, String action) {

        collectionOfLabelsAi[position][2].setText(action);

    }


    /**
     * Changes the AI-frame based on position and state.
     *
     * @param position Position on the screen (0-4).
     * @param state    The state can either be inactive (folded/lost), idle (waiting for it's turn),
     *                 active (currently it's turn).
     */
    public void setUIAiStatus(int position, String state) {
        String resource = "resources/images/"; // 122, 158
        //if(collectionOfCardsAi[position].getImage().getUrl().equals(Paths.get(resource + "aiBarWithCardsOut.png").toUri().toString())){
          //  return;
        //}
        if(unchangableImages.contains(position)){
            return;
        }

        Image hideCards = new Image(Paths.get(resource + "aiBarWithoutCards.png").toUri().toString(),
                122, 158, true, true);
        Image showCards = new Image(Paths.get(resource + "aiBarWithCards.png").toUri().toString(), 122,
                158, true, true);
        Image showActiveCards =
                new Image(Paths.get(resource + "aiBarWithCardsCurrentPlayer.png").toUri().toString(), 122,
                        158, true, true);
        Image showOutCards =
                new Image(Paths.get(resource + "aiBarWithCardsOut.png").toUri().toString(), 122, 158,
                        true, true);

        if (state.equals("inactive")) {
            collectionOfCardsAi[position].setImage(hideCards);
        } else if (state.equals("idle")) {
            collectionOfCardsAi[position].setImage(showCards);
        } else if (state.equals("active")) {
            collectionOfCardsAi[position].setImage(showActiveCards);
        } else if (state.equals("out")){
            collectionOfCardsAi[position].setImage(showOutCards);
            unchangableImages.add(position);
        }
    }


    /**
     * Sets the SPController for this gameController
     *
     * @param spController an instance of the class SPController
     */
    public void setSPController(SPController spController) {

        this.spController = spController;
        spController.setGameController(this);
    }


    /**
     * Disables all buttons and shows player-frame's action as check.
     */
    public void playerCheck() {

        disableButtons();
        this.decision = "check";
        lbPlayerAction.setText("check");
        playerMadeDecision = true;
        updatePlayerValues("Passa");
        Sound.playSound("check");
    }


    /**
     * Disables all buttons and shows player-frame's action as fold.
     */
    public void playerFold() {

        disableButtons();
        this.decision = "fold";
        lbPlayerAction.setText("fold");
        playerMadeDecision = true;
        updatePlayerValues("La sig");
        Sound.playSound("fold");
    }


    /**
     * Disables all buttons and shows player-frame's action as call, and the called amount. Calculates
     * and withdraws amount from player-pot.
     * Amended by: Anthon Haväng, Erik Larsson, Jens Bjerre: replaced § with $.
     */
    public void playerCall() {

        disableButtons();
        /*
         * Player's pot - (Current maxbet - already paid (prev rounds)) THE PLAYER'S POT
         */
        this.playerPot -= (spController.getCurrentMaxBet() - alreadyPaid);
        /*
         * Already paid + (Current maxbet - already paid) = WHAT THE PLAYER HAS ALREADY PAID
         */
        this.alreadyPaid += (spController.getCurrentMaxBet() - alreadyPaid);
        this.decision = "call," + Integer.toString(alreadyPaid);
        playerMadeDecision = true;
        Sound.playSound("chipSingle");
        updatePlayerValues("Syna, $" + Integer.toString(alreadyPaid));
    }


    /**
     * Disables all buttons and shows player-frame's action as raise, and the raised amount.
     * Calculates and withdraws amount from player-pot and adjusts already paid.
     * Amended by: Anthon Haväng, Erik Larsson, Jens Bjerre:
     */
    public void playerRaise() {

        disableButtons();
        /*
         * If the player hasn't matched the current maxbet
         */
        if (spController.getCurrentMaxBet() != alreadyPaid) {}

            int raisedBet = (int) (slider.getValue());
            this.playerPot -= raisedBet;
            /*
             * (raised amount + the amount the player has to match(if the player has to match)) = THE
             * PLAYER'S POT
             */
            this.decision = "raise," + (raisedBet + spController.getCurrentMaxBet()); // Chosen
            // raised
            // amount

            playerMadeDecision = true;
            Sound.playSound("chipMulti");

            updatePlayerValues("Höja, $" + raisedBet);

            try {
                if (playerPot == 0) { // Checks if the player has gone all in.
                    updatePlayerValues("All-In, $" + raisedBet);
                    this.decision = "allin," + (raisedBet) + "," + alreadyPaid;
                    this.alreadyPaid += raisedBet;
                    slider.setDisable(true);
                    //showAllIn();
                    disableButtons();
                } else {
                    updatePlayerValues("Höja, $" + raisedBet);
                    this.alreadyPaid += raisedBet;

                    /*
                     * Already paid + (raised amount + the amount the player has to match(if the player has to
                     * match)) = WHAT THE PLAYER HAS ALREADY PAID
                     */
                }
            } catch (Exception e) {
                e.printStackTrace();
        }
    }

    /**
     * Puts player fully all-in no matter the circumstance
     * @author: Anthon Haväng, Erik Larsson, Jens Bjerre
     * Functionality for making the "all in-move".
     */
    public void playerAllIn() {
        int currentAllIn = this.playerPot;

        disableButtons();
        slider.setValue(this.playerPot);
        playerMadeDecision = true;
        this.decision = "allin," + currentAllIn + "," + this.alreadyPaid;
        this.alreadyPaid += this.playerPot;
        this.playerPot -= currentAllIn;
        updatePlayerValues("All in, $" + currentAllIn);
        slider.setDisable(true);
        disableButtons();
        Sound.playSound("allin");
    }

    /**
     * Updates player-frame's labels (action and player pot) based on action.
     *
     * @param action Call, Check, Raise or Fold
     */
    public void updatePlayerValues(String action) {

        lbPotValue.setText("$" + (playerPot));
        lbPlayerAction.setText(action);
        setSliderValues();
    }


    /**
     * DEPRECATED. Never successfully implemented.
     */
    public void saveGame() {
    }


    /**
     * Sets the slider's min and max values based on the player-pot. Sets minimum sliderValue based on
     * BigBlind.
     */
    public void setSliderValues() {

        int calcWithdraw = 0;
        if (spController.getCurrentMaxBet() != alreadyPaid) { // If the player hasn't matched the current max bet
            calcWithdraw = spController.getCurrentMaxBet() - alreadyPaid; // Calculates amount for the player to match max bet
        }

        slider.setMax(playerPot);
        if (calcWithdraw > spController.getBigBlind()) {
            slider.setMin(calcWithdraw);
        } else if (spController.getBigBlind() <= playerPot) { // Sets minimum value required in order to raise.
            slider.setMin(spController.getBigBlind());
        } else {
            slider.setMin(0);
        }

        if ((slider.getMax() - slider.getMin()) > 4) {
            slider.setMajorTickUnit((slider.getMax() - slider.getMin()) / 4);
        } else {
            slider.setMajorTickUnit(25);
        }
        slider.setMinorTickCount(4);
    }


    /**
     * Triggers when the player uses the slider to choose raise amount.
     */
    public void sliderChange() {

        slider.valueProperty().addListener(e -> {
            raiseLabel.setText(String.valueOf((int) slider.getValue()));

        });
    }


    /**
     * Creates a new ruleController.
     *
     * @throws IOException
     */


    /**
     * Method which returns the potValue for the table.
     *
     * @return tablePotValue the potValue for the table.
     */
    public double getPotValue() {

        return tablePotValue;
    }


    /**
     * Sets the player's name.
     *
     * @param name Used to sets the players name on the UI.
     */
    public void setUsername(String name) {
        userName.setText(name);
    }


    /**
     * Returns the players name
     *
     * @return userName the players name.
     */
    public String getUsername() {

        return userName.getText();
    }

    /**
     * Set slider active
     */
    public void activeSlider() {

        slider.setDisable(false);
    }


    /**
     * Clears AI action and updates the new and current AI-pot at the end of the round.
     *
     * @param ai Which AI to update values for.
     */
    public void endOfRound(int ai) {

        Platform.runLater(new Runnable() {

            private volatile boolean shutdown;


            @Override
            public void run() {

                while (!shutdown) {
                    setLabelUIAiBarPot(ai, Integer.toString(aiPlayers.get(ai).aiPot()));
                    setLabelUIAiBarAction(ai, "");
                    shutdown = true;
                }
            }
        });
    }


    /**
     * Sets the starting hand pre-flop for the player.
     *
     * @param card1 First playercard in the hand.
     * @param card2 Second playercard in the hand.
     */
    public void setStartingHand(Card card1, Card card2) {

        isReady = false;
        Platform.runLater(() -> {
            clearFlopTurnRiver(); // Clears the table cards
        });

        Platform.runLater(() -> {
            for (int i = 0; i < 5; i++) { // Resets AI labels and removes all
                // previous glow-effects.
                setUIAiStatus(i, "idle");
                setLabelUIAiBarAction(i, "");
            }
        });

        cards.clear(); // Clears previous hand
        this.card1 = card1;
        this.card2 = card2;

        highCard = card1.getCardValue();
        if (card2.getCardValue() > highCard) {
            highCard = card2.getCardValue();
        }
        cards.add(card1); // Adds two cards to hand.
        cards.add(card2);
        this.hand = new Hand(cards);

        isReady = true;
        checkHand();
        handHelp();
    }


    /**
     * Checks the player's hand and gives tips and highlights cards based on the method
     * getHighlightedCards (important during pre-flop situation).
     */
    public void checkHand() {

        Platform.runLater(() -> {

            hand.reCalc(spController.getAllKnownCards());
            playerCardsArea.requestLayout();
            playerCardsArea.getChildren().clear();
            String cardOne =
                    "resources/images/" + card1.getCardValue() + card1.getCardSuit().charAt(0) + ".png";
            String cardTwo =
                    "resources/images/" + card2.getCardValue() + card2.getCardSuit().charAt(0) + ".png";

            if (hand.getHighlightedCards()
                    .contains((card1.getCardValue()) + "," + card1.getCardSuit().charAt(0))) {
                cardOne =
                        "resources/images/" + card1.getCardValue() + card1.getCardSuit().charAt(0) + "O.png";
            }

            if (hand.getHighlightedCards()
                    .contains((card2.getCardValue()) + "," + card2.getCardSuit().charAt(0))) {
                cardTwo =
                        "resources/images/" + card2.getCardValue() + card2.getCardSuit().charAt(0) + "O.png";
            }

            ImageView imgCard1;
            ImageView imgCard2;

            Image image = new Image(Paths.get(cardOne).toUri().toString(), 114, 148, true, true);
            imgCard1 = new ImageView(image);
            playerCardsArea.getChildren().add(imgCard1);
            imgCard1.setX(0);
            imgCard1.setY(0);

            image = new Image(Paths.get(cardTwo).toUri().toString(), 114, 148, true, true);
            imgCard2 = new ImageView(image);
            playerCardsArea.getChildren().add(imgCard2);
            imgCard2.setX(105);
            imgCard2.setY(0);
            updatePlayerValues("");
        });
    }


    /**
     * Uses the getHighlightedCards to highlight and show cards on the table.
     *
     * @param setOfCards Set of cards shown on the table.
     */
    public int setFlopTurnRiver(Card[] setOfCards) {

        this.cards = new ArrayList<Card>(); // Clears the cards list
        cards.add(card1); // Adds card one and card two (player's cards in the hand)
        cards.add(card2);

        for (Card c : setOfCards) {
            cards.add(c); // Adds cards from flop/turn/river
        }

        this.hand = new Hand(spController.getAllKnownCards());
        hand.reCalc(spController.getAllKnownCards()); // Recalculates so the "new" set of cards gets highlighted

        Platform.runLater(() -> {
            tableCardArea.getChildren().clear(); // Clears if there's cards on the table (UI)
            tableCardArea.requestLayout();

            int xCord = 0;
            for (int i = 0; i < setOfCards.length; i++) { // Loops through all cards and highlights the correct ones and
                // places them on the table (UI)
                String baseCard = "";
                if (hand.getHighlightedCards().contains((setOfCards[i].getCardValue()) + ","
                        + setOfCards[i].getCardSuit().charAt(0))) {
                    baseCard = "resources/images/" + setOfCards[i].getCardValue()
                            + setOfCards[i].getCardSuit().charAt(0) + "O.png";
                } else {
                    baseCard = "resources/images/" + setOfCards[i].getCardValue()
                            + setOfCards[i].getCardSuit().charAt(0) + ".png";
                }
                if (i == 1) {
                    xCord = 110; // First card
                } else if (i > 1) {
                    xCord += 105;
                }
                Image imageTemp = new Image(Paths.get(baseCard).toUri().toString(), 114, 148, true, true);

                collectionOfCardsTable[i] = new ImageView(imageTemp);
                tableCardArea.getChildren().add(collectionOfCardsTable[i]);
                collectionOfCardsTable[i].setX(xCord);
                collectionOfCardsTable[i].setY(0);
            }
        });
        handHelp();
        checkHand();

        return 1;
    }


    /**
     * Clears the cards on the table.
     */
    public void clearFlopTurnRiver() {

        Platform.runLater(() -> {
            tableCardArea.getChildren().clear();
        });
    }


    /**
     * Method which makes the player the smallblind.
     *
     * @param i the amount to pay
     */
    public void playerSmallBlind(int i) {

        this.alreadyPaid += i;
        this.playerPot -= i;
        Platform.runLater(() -> {

            ivSmallBlind.relocate(520, 425);

        });
        updatePots(new int[1][1], spController.getPotSize());

    }


    /**
     * Method which makes the player the bigBlind
     *
     * @param i the amount to pay
     */
    public void playerBigBlind(int i) {

        this.alreadyPaid += i;
        this.playerPot -= i;
        Platform.runLater(() -> {
            ivBigBlind.relocate(520, 425);

        });
        updatePots(new int[1][1], spController.getPotSize());
    }


    /**
     * Returns the amount of money that the player has already bet
     *
     * @return The amount of money that the player has already bet
     */
    public int getPlayerAlreadyPaid() {

        return this.alreadyPaid;
    }


    /**
     * Method which sets the player as dealer
     *
     * @param i not used.
     */
    public void playerIsDealer(int i) {

        if ((int) ivBigBlind.getLayoutX() == 520 || (int) ivSmallBlind.getLayoutX() == 520) {
            ivDealer.setLayoutX(500);
            ivDealer.setLayoutY(425);
        } else {
            ivDealer.setLayoutX(520);
            ivDealer.setLayoutY(425);
        }
    }


    /**
     * Method which fetches the advice for the player and displays it in the bottom left pane
     */
    public void handHelp() {

        String powerBarWeakHand = "resources/images/weakHand.png";
        String powerBarMediumWeakHand = "resources/images/mediumWeakHand.png";
        String powerBarMediumStrongHand = "resources/images/mediumStrongHand.png";
        String powerBarStrongHand = "resources/images/StrongHand.png";

        Platform.runLater(() -> {

            String helpText = hand.theHelp();
            helpLabel.setText("Du har: \n" + helpText);
            String adviceText = hand.theAdvice();
            adviceLabel.setText("Råd: \n" + adviceText);

            powerBarValue = hand.toPowerBar();

            if (powerBarValue == 1) {
                powerBarArea.getChildren().remove(imgPowerBar);
                image = new Image(Paths.get(powerBarWeakHand).toUri().toString(), 120, 166, true, true);
                imgPowerBar = new ImageView(image);
                powerBarArea.getChildren().add(imgPowerBar);
                imgPowerBar.setX(15);
                imgPowerBar.setY(0);

            } else if (powerBarValue == 2) {
                powerBarArea.getChildren().remove(imgPowerBar);
                image =
                        new Image(Paths.get(powerBarMediumWeakHand).toUri().toString(), 120, 166, true, true);
                imgPowerBar = new ImageView(image);
                powerBarArea.getChildren().add(imgPowerBar);
                imgPowerBar.setX(15);
                imgPowerBar.setY(0);

            } else if (powerBarValue == 3) {
                powerBarArea.getChildren().remove(imgPowerBar);
                image =
                        new Image(Paths.get(powerBarMediumStrongHand).toUri().toString(), 120, 166, true, true);
                imgPowerBar = new ImageView(image);
                powerBarArea.getChildren().add(imgPowerBar);
                imgPowerBar.setX(15);
                imgPowerBar.setY(0);

            } else if (powerBarValue == 4) {
                powerBarArea.getChildren().remove(imgPowerBar);
                image = new Image(Paths.get(powerBarStrongHand).toUri().toString(), 120, 166, true, true);
                imgPowerBar = new ImageView(image);
                powerBarArea.getChildren().add(imgPowerBar);
                imgPowerBar.setX(15);
                imgPowerBar.setY(0);

            }
            this.handStrength = hand.getHandStrenght();

        });

    }

    //Cornelia: logg-test


    public void addLogMessage(String logMessage) {
        Platform.runLater(() -> {
            Text newLogText = new Text(logMessage + "\n");
            newLogText.setFont(Font.font("Tw Cen MT", FontWeight.SEMI_BOLD, 16));
            logTextFlow.getChildren().add(newLogText);
            logScrollPane.setVvalue(1.0);
        });
    }


    /**
     * Returns the players decision.
     *
     * @return The players decision.
     */
    public String getPlayerDecision() {

        return decision;
    }


    /**
     * Method which controls the players decision
     *
     * @return The players decision
     */
    public String askForPlayerDecision() throws InterruptedException{
        System.out.println("Current Thread" + Thread.currentThread().getName());
        handleButtons();
        playerMadeDecision = false;
        while (!playerMadeDecision) {
                spController.sleep(100);
        }
        return decision;
    }


    /**
     * Method which resets the players cards, amount paid and decision.
     *
     * @param resetDecision the new decision
     */
    public void playerReset(String resetDecision) {

        decision = resetDecision;
        alreadyPaid = 0;
        cards = new ArrayList<Card>();
    }


    /**
     * Sets the new player-pot.
     *
     * @param playerPot The value to add/remove from the player-pot.
     */
    public void setPlayerPot(int playerPot) {
        this.playerPot = playerPot;
    }

    /**
     * Resets player pot
     *
     * @param playerPot The value to reset the playerPot to.
     */
    public void resetPlayerPot(int playerPot) {
        this.playerPot = playerPot;
    }

    /**
     * Shows/hides player-buttons based on allowed actions.
     */
    public void handleButtons() {

        if (alreadyPaid == spController.getCurrentMaxBet()) {
            // show check, hide all other
            btCheck.setVisible(true);
            btCall.setVisible(false);
            btRaise.setVisible(true);
            btFold.setVisible(true);
            btAllIn.setVisible(true);
        } else {
            if (alreadyPaid < spController.getCurrentMaxBet()
                    && (playerPot + alreadyPaid) >= spController.getCurrentMaxBet()) {
                // hide check, show call
                btCheck.setVisible(false);
                btCall.setVisible(true);
                btFold.setVisible(true);
                btAllIn.setVisible(true);
            } else {
                // hide call, hide check
                btCheck.setVisible(false);
                btCall.setVisible(false);
                btFold.setVisible(true);
                btAllIn.setVisible(true);
            }

            if ((spController.getCurrentMaxBet() - alreadyPaid) + spController.getBigBlind() <= playerPot
                    && playerPot != 0) {
                // show raise and all-in-button
                btRaise.setVisible(true);
                btAllIn.setVisible(true);
            } else {
                // hide raise and all-in-button
                btRaise.setVisible(false);
                btAllIn.setVisible(false);
            }
        }
        inactivateAllAiCardGlows();
    }


    /**
     * Disables all player-buttons.
     */
    public void disableButtons() {

        btCall.setVisible(false);
        btRaise.setVisible(false);
        btCheck.setVisible(false);
        btFold.setVisible(false);
        btAllIn.setVisible(false);
    }


    /**
     * Method which returns the players handStrength as an integer
     *
     * @return the handStrength
     */
    public int getHandStrength() {

        return handStrength;
    }


    /**
     * Method which returns the players pot
     *
     * @return the playerpot
     */
    public int getPlayerPot() {

        return playerPot;
    }


    /**
     * Method which dims an AI player
     *
     * @param AI an AI player
     */
    public void removeAiPlayer(int AI) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                collectionOfLabelsAi[AI][0].setVisible(false);
                collectionOfLabelsAi[AI][1].setVisible(false);
                collectionOfLabelsAi[AI][2].setVisible(false);
                collectionOfCardsAi[AI].setVisible(false);
            }
        });
    }


    /**
     * Places the AI-players in the correct position depending on chosen number of players.
     *
     * @param aiPlayers     All the AI-players that are active.
     * @param notFirstRound
     * @param deadAIIndex
     */
    public void setAiPlayers(LinkedList<Ai> aiPlayers, boolean notFirstRound, int deadAIIndex) {
        for(int i = 0; i<5; i++) {
            removeAiPlayer(i);
        }
        this.aiPlayers = aiPlayers;
        int totalAI = spController.getFixedNumberOfAIs();
        if (!notFirstRound) {
            if (totalAI == 1) {
                setShowUIAiBar(2);
            } else if (totalAI == 3) {
                setShowUIAiBar(0);
                setShowUIAiBar(2);
                setShowUIAiBar(4);
            } else if (totalAI == 5) {
                setShowUIAiBar(0);
                setShowUIAiBar(1);
                setShowUIAiBar(2);
                setShowUIAiBar(3);
                setShowUIAiBar(4);
            }
        } else if (notFirstRound) {
            endOfRound(deadAIIndex);
        }
    }


    /**
     * Updates AI-frame based on currentAI-position and decision with the method setUIAiStatus.
     *
     * @param currentAI Chosen AI to update AI-frame
     * @param decision  Check, call, fold, raise or lost
     */
    public void aiAction(int currentAI, String decision) {
        try {
            int setAINr = spController.getFixedNumberOfAIs();

            int setOfPlayers = 0; // Is used for choosing the correct set of
            // positioning (see
            // aiPositions[][])

            // Decides (based on chosen AI-players) which position to place the AI
            // at
            if (setAINr == 1) {
                setOfPlayers = 0;
            } else if (setAINr == 3) {
                setOfPlayers = 1;
            } else if (setAINr == 5) {
                setOfPlayers = 2;
            }

            int currentAIPosition = aiPositions[setOfPlayers][currentAI];

            if (prevPlayerActive != -1) { // If there does exists a previous active
                // AI-player
                setUIAiStatus(prevPlayerActive, "idle"); // Resets the previous
                // player's image from
                // glowing(active) to
                // non-glowning(idle)
            }

            Ai ai = aiPlayers.get(currentAI);

            if (decision.contains("fold") || decision.contains("lost") || decision.isEmpty()) {
                if(decision.contains("fold") || decision.isEmpty()){
                    setUIAiStatus(currentAIPosition, "inactive");
                } else {
                    setUIAiStatus(currentAIPosition, "out");
                }
            } else {
                setUIAiStatus(currentAIPosition, "active");
                this.prevPlayerActive = currentAIPosition;
            }

            Platform.runLater(new Runnable() {

                private volatile boolean shutdown;


                @Override
                public void run() {

                    /**
                     * Sets name, pot and action for the AI's (UI)
                     */
                    while (!shutdown) {
                        setLabelUIAiBarName(currentAIPosition, ai.getName());
                        setLabelUIAiBarPot(currentAIPosition, Integer.toString(ai.aiPot()));
                        setLabelUIAiBarAction(currentAIPosition, getFormattedDecision(decision));
                        shutdown = true;
                    }
                }
            });
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Error while processing. Probably cause: Shutdown of current game");
        }
    }


    /**
     * Formats action label for AI.
     *
     * @param decision fold/lost/check/call/raise/all-in/Dealer/SmallBlind/BigBlind
     * @return Formatted decision
     * Amended by: Anthon Haväng, Erik Larsson, Jens Bjerre: simplified a long else if-else if section to a simple
     * switch-case. Automatically convert from else-if to switch-case by OpenAI.
     */
    public String getFormattedDecision(String decision) {

        String actionText = "Error";

        switch (decision) {
            case "fold"     -> actionText = "La sig";
            case "lost"     -> actionText = "Förlorat";
            case "check"    -> actionText = "Passa";
            case "call"     -> actionText = "Syna";
            case "raise"    -> {
                String[] decisionAi = decision.split(",");
                actionText = "Raise, $" + decisionAi[1];
            }
            case "all-in"       -> actionText = "All-In";
            case "Dealer"       -> actionText = "Dealer";
            case "SmallBlind"   -> actionText = "Liten mörk, $" + spController.getSmallBlind();
            case "BigBlind"     -> actionText = "Stor mörk, $" + spController.getBigBlind();
        }
        return actionText;
    }


    /**
     * This metod makes sure that during the players turn, the previous AI is considered idle
     */
    public void inactivateAllAiCardGlows() {

        if (prevPlayerActive != -1) {
            setUIAiStatus(prevPlayerActive, "idle");
            this.prevPlayerActive = -1;
        }
    }


    /**
     * Method which returns if the UI is ready
     *
     * @return isReady are we ready?
     */
    public boolean getIsReady() {

        return isReady;
    }


    /**
     * Method which creates a popup to inform the player that s/he lost.
     */
    public void playerLost() {

        Platform.runLater(() -> {
            winnerBox = new WinnerBox();
            winnerBox.displayWinner("Förlust",
                    "Tyvärr, du förlorade och dina pengar är slut. Bättre lycka nästa gång!", 5,
                    winnerHand);
            SceneController.switchScene(Scenes.MainMenu);
        });
    }

    public void playerWon() {

        Platform.runLater(() -> {
            winnerBox = new WinnerBox();
            winnerBox.displayWinner("Grattis",
                    "Wallah du mycket djurig och vann detta spelet!!!!!!", 5,
                    winnerHand);
            SceneController.switchScene(Scenes.MainMenu);
        });
    }




    /**
     * Method which returns the players highCard
     *
     * @return highCard
     */
    public int getGetHighCard() {

        return highCard;
    }


    public void setBlindsMarker(int dealer, int smallBlindPlayer, int bigBlindPlayer) {

        int[][] markerPos = new int[5][2];
        Platform.runLater(() -> {

            // set MarkerPos TEST
            markerPos[0][0] = 300;
            markerPos[0][1] = 360;

            markerPos[1][0] = 375;
            markerPos[1][1] = 172;

            markerPos[2][0] = 745;
            markerPos[2][1] = 172;

            markerPos[3][0] = 1010;
            markerPos[3][1] = 220;

            markerPos[4][0] = 1010;
            markerPos[4][1] = 360;

            if (dealer <= 4) {
                ivDealer.relocate(markerPos[dealer][0], markerPos[dealer][1]);
            }
            if (smallBlindPlayer <= 4) {
                ivSmallBlind.relocate(markerPos[smallBlindPlayer][0], markerPos[smallBlindPlayer][1]);
            }
            if (bigBlindPlayer <= 4) {
                ivBigBlind.relocate(markerPos[bigBlindPlayer][0], markerPos[bigBlindPlayer][1]);
            }
        });
    }


    /**
     * Shows current round.
     *
     * @param round int between 0-3 ("roundPreFlop", "roundFlop", "roundTurn", "roundRiver").
     */
    public void roundStatus(int round) {

        String[] roundStatus = new String[]{"roundPreFlop", "roundFlop", "roundTurn", "roundRiver"};

        Platform.runLater(() -> {
            paneRounds.getChildren().remove(imgRoundStatus);
            Image tempImage =
                    new Image(Paths.get("resources/images/" + roundStatus[round] + ".png").toUri().toString(),
                            175, 56, true, true);
            imgRoundStatus = new ImageView(tempImage);
            imgRoundStatus.setImage(tempImage);
            imgRoundStatus.setPreserveRatio(false);
            paneRounds.getChildren().add(imgRoundStatus);
        });
    }

    public void updateHandsWon(int wins){
        handsWonLabel.setText("Rundor vunna: " + wins);
        System.out.println("I updated hands won with " + wins);
    }


    /**
     * Creates a winnerWindow that displays the winner of the round.
     *
     * @param winner Name of the winner from spController.
     * @param hand   Int number from spController that represent the value of the winning hand.
     * Amended by: Anthon Haväng, Erik Larsson, Jens Bjerre: simplified a long else if-else if section to a simple
     * switch-case. Automatically convert from else-if to switch-case by OpenAI.
     */
    public void setWinnerLabel(String winner, int hand) {
        //TODO: remove
        System.out.println("setWinnerLabel körs");

        synchronized (this) {
            switch (hand) {
                case 0 -> winnerHand = "högsta kort";
                case 1 -> winnerHand = "ett par";
                case 2 -> winnerHand = "två par";
                case 3 -> winnerHand = "triss";
                case 4 -> winnerHand = "straight";
                case 5 -> winnerHand = "flush";
                case 6 -> winnerHand = "full house";
                case 7 -> winnerHand = "four of a kind";
                case 8 -> winnerHand = "straight flush";
                case 99 -> winnerHand = "Du vann när resten av spelarna foldade!";
                case 98 -> winnerHand = "när resterande spelare foldade.";
                case 97 -> winnerHand = "Du förlorade!";
            }

            if (!winner.equals(getUsername()) && (hand < 10)) {
                Platform.runLater(() -> {
                    winnerBox = new WinnerBox();
                    winnerBox.displayWinner("Rundans vinnare", winner, 2, winnerHand);
                });
            } else if (winner.equals(getUsername()) && (hand < 10)) {
                Platform.runLater(() -> {
                    Sound.playSound("coinSound");
                    winnerBox = new WinnerBox();
                    winnerBox.displayWinner("Rundans vinnare", winner, 1, winnerHand);
                    handsWon++;
                    updateHandsWon(handsWon);
                    //TODO: remove
                    System.out.println("Matches won: " + matchesWon + "\nRounds won: " + handsWon);
                });
            } else if (winner.equals(getUsername()) && (hand > 10)) {
                Platform.runLater(() -> {
                    Sound.playSound("coinSound");
                    winnerBox = new WinnerBox();
                    winnerBox.displayWinner("Rundans vinnare", winner, 3, winnerHand);
                    handsWon++;
                    updateHandsWon(handsWon);
                    //TODO: remove
                    System.out.println("Matches won: " + matchesWon + "\nRounds won: " + handsWon);
                });
            } else if (!winner.equals(getUsername()) && (hand > 10)) {
                Platform.runLater(() -> {
                    winnerBox = new WinnerBox();
                    winnerBox.displayWinner("Rundans vinnare", winner, 4, winnerHand);

                });
            }
        }
    }


    /**
     * Method which returns the player viabilitylevel for potSplits
     *
     * @return AllInViability viabilityLevel
     */
    public int getAllInViability() {

        return AllInViability;
    }


    /**
     * Method which sets a viability level
     *
     * @param allInViability
     */
    public void setAllInViability(int allInViability) {

        if (allInViability < AllInViability) {
            AllInViability = allInViability;
        }
    }


    /**
     * Method which updates the various pots in the UI
     *
     * @param potSplits an Array of subPots during All-ins
     * @param tablePot  the main tablePot
     */
    public void updatePots(int[][] potSplits, int tablePot) {

        if (spController.getFixedNumberOfAIs() == 5) {
            this.collectionOfPots = new Label[]{subPotOne, subPotTwo, subPotThree, subPotFour, subPotFive, subPotSix};
        } else if (spController.getFixedNumberOfAIs() == 3) {
            this.collectionOfPots = new Label[]{subPotOne, subPotTwo, subPotThree, subPotFour};
        } else if (spController.getFixedNumberOfAIs() == 1) {
            this.collectionOfPots = new Label[]{subPotOne, subPotTwo};
        }
        Platform.runLater(() -> {
            String[] potOrder = {"Sub-Pot One: ", "Sub-Pot Two: ", "Sub-Pot Three: ", "Sub-Pot Four: ",
                    "Sub-Pot Five: ", "Sub-Pot Six: "};
            for (int i = 0; i < collectionOfPots.length; i++) {
                if (potSplits[i][0] > 0) {
                    collectionOfPots[i].setText(potOrder[i] + "$" + potSplits[i][0]);
                    collectionOfPots[i].setVisible(true);
                    collectionOfPots[i].setLayoutX(10);
                    collectionOfPots[i].setLayoutY(30 * (i + 1) + 70);
                } else {
                    collectionOfPots[i].setVisible(false);
                }
            }
            mainPot.setText("Bords-Pot: $" + tablePot);
            mainPot.setLayoutX(295.0);
            mainPot.setLayoutY(290.0);
            mainPot.setVisible(true);
        });
    }

    public void resetUnchangeableImages(){
        unchangableImages.clear();
    }


    public Slider getSlider() {
        return slider;
    }

    public Label getLbPlayerAction() {
        return lbPlayerAction;
    }

    public Label getHandsWonLabel() {
        return handsWonLabel;
    }

    public Image getImage() {
        return image;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Hand getHand() {
        return hand;
    }

    public Card getCard1() {
        return card1;
    }
}
