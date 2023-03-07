package controller.gameControllers;

import controller.SPController;
import controller.Main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Card;
import model.Deck;
import model.Suit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Anthon Haväng
 * Test cases for controller.gameControllers.GameController.java
 */
class GameControllerTest {
     private static GameController gameController;
     private static SPController spController;

     /**
      * -------------- TEST ENVIRONMENT SETUP --------------
      */

     /**
      * @author Anthon Haväng
      */
     @BeforeEach
     void prepSPController() {
          spController = new SPController();
          gameController.setSPController(spController);
     }

     /**
      * @author Anthon Haväng
      */
     @BeforeAll
     static void initJFXRuntime() {
          new Thread(() -> Application.launch(Main.class)).start();
     }

     /**
      * @author Anthon Haväng
      */
     @BeforeEach
     void prepTests() {
          Platform.runLater(() -> {
               try {
                    FXMLLoader loaderGameState = new FXMLLoader(GameController.class.getResource("/GameState.fxml"));
                    Pane gameState = loaderGameState.load();
                    gameController = loaderGameState.getController();
               } catch (IOException e) {
                    e.printStackTrace();
               }
          });
          try {
               Thread.sleep(2000);
          } catch (InterruptedException e) {
               throw new RuntimeException(e);
          }
     }

     /**
      * -------------- TEST CASES BELOW --------------
      */

     /**
      * @author Anthon Haväng
      * Krav: KF-3.4.3.4
      */
     @Test
     void playerAllInTest(){
          gameController.setPlayerPot(1000);
          gameController.playerAllIn();
          Assertions.assertEquals(0, gameController.getPlayerPot());
     }

     /**
      * @author Anthon Haväng & Erik Larsson
      */
     @Test
     void handsWonTest() {
          gameController.updateHandsWon(10);
          Assertions.assertEquals("Hands won: 10", gameController.getHandsWonLabel().getText());
     }

     /**
      * @author Anthon Haväng
      */
     @Test
     void playerRaiseTest(){
          gameController.getSlider().setValue(500);
          gameController.setPlayerPot(1);
          gameController.playerRaise();
          Assertions.assertEquals(500, gameController.getPlayerAlreadyPaid());
     }

     /**
      * @author Anthon Haväng
      */
     @Test
     void playerRaiseWithAllInTest(){
          gameController.getSlider().setValue(500);
          gameController.setPlayerPot(0);
          gameController.playerRaise();
          Assertions.assertEquals(500, gameController.getPlayerAlreadyPaid());
     }

     /**
      * @author Anthon Haväng & Erik Larsson
      */
     @Test
     void playerFoldTest(){
          gameController.playerFold();
          Assertions.assertEquals("Fold", gameController.getLbPlayerAction().getText());
     }

     /**
      * @author Anthon Haväng & Erik Larsson
      */
     @Test
     void playerCallTest(){
          gameController.playerCall();
          Assertions.assertEquals("Call, $0", gameController.getLbPlayerAction().getText());
     }

     /**
      * @author Anthon Haväng & Erik Larsson
      */
     @Test
     void playerCheckTest(){
          gameController.playerCheck();
          Assertions.assertEquals("Check", gameController.getLbPlayerAction().getText());
     }

     /**
      * @author Anthon Haväng & Erik Larsson
      */
     @Test
     void setStartingHandTest(){

          Card[] cards = new Card[2];

          cards[0] = new Card(Suit.HEARTS, 10, new ImageIcon());
          cards[1] = new Card(Suit.CLUBS, 9, new ImageIcon());

          gameController.setStartingHand(cards[0], cards[1]);

          Assertions.assertEquals(gameController.getCards().get(0), cards[0]);
          Assertions.assertEquals(gameController.getCards().get(1), cards[1]);
     }

     @Test
     void setFlopTurnRiverTest(){
          ArrayList<Card> cards = new ArrayList<>();
          cards.add(new Card(Suit.HEARTS, 10, new ImageIcon()));
          cards.add(new Card(Suit.CLUBS, 9, new ImageIcon()));

          SPController spController1 = new SPController();
          spController1.setAllKnownCards(cards);

          Card[] card1 = new Card[2];
          card1[0] = cards.get(0);
          card1[1] = cards.get(1);

          Assertions.assertEquals(1,gameController.setFlopTurnRiver(card1));
     }

     //Test of clearing the cards on the table
     @Test
     void clearFlopTurnRiver(){
          // TODO: 2023-03-02 set up the game så att det kommer toll ett stadie där vi kan tömma den.

          gameController.clearFlopTurnRiver();
     }

     // TODO: 2023-03-02 Vi kan skapa ett test där den testar handHelp metoden.
     //Kan göra flera stycken här, där vi testar, stark, mediumStark, mediumSvag, Svag
     //Kolla hur man sätter spelarens kort.


     @Test
     void handHelpStrong() {
          Deck deck = new Deck();
          Card card1 = deck.getCardByIndex(0);
          Card card2 = deck.getCardByIndex(13);
          System.out.println(card1.getCardSuit() + " " + card1.getCardValue());
          System.out.println(card2.getCardSuit() + " " + card2.getCardValue());

          gameController.setStartingHand(card1, card2);
          sleep(4000);
          String powerBarWeakHand = "resources/images/weakHand.png";
          String powerBarMediumWeakHand = "resources/images/mediumWeakHand.png";
          String powerBarMediumStrongHand = "resources/images/mediumStrongHand.png";
          String powerBarStrongHand = "resources/images/StrongHand.png";

          Image image = new Image(Paths.get(powerBarStrongHand).toUri().toString());
          Assertions.assertEquals(image.getUrl(), gameController.getImage().getUrl());
     }
     @Test
     void handHelpMediumStrong() {
          Deck deck = new Deck();
          Card card1 = deck.getCardByIndex(0);
          Card card2 = deck.getCardByIndex(1);
          System.out.println(card1.getCardSuit() + " " + card1.getCardValue());
          System.out.println(card2.getCardSuit() + " " + card2.getCardValue());

          gameController.setStartingHand(card1, card2);
          sleep(4000);
          String powerBarWeakHand = "resources/images/weakHand.png";
          String powerBarMediumWeakHand = "resources/images/mediumWeakHand.png";
          String powerBarMediumStrongHand = "resources/images/mediumStrongHand.png";
          String powerBarStrongHand = "resources/images/StrongHand.png";

          Image image = new Image(Paths.get(powerBarMediumStrongHand).toUri().toString());
          Assertions.assertEquals(image.getUrl(), gameController.getImage().getUrl());
     }
     @Test
     void handHelpMediumWeak() {
          Deck deck = new Deck();
          Card card1 = deck.getCardByIndex(0);
          Card card2 = deck.getCardByIndex(17);
          System.out.println(card1.getCardSuit() + " " + card1.getCardValue());
          System.out.println(card2.getCardSuit() + " " + card2.getCardValue());

          gameController.setStartingHand(card1, card2);
          sleep(4000);
          String powerBarWeakHand = "resources/images/weakHand.png";
          String powerBarMediumWeakHand = "resources/images/mediumWeakHand.png";
          String powerBarMediumStrongHand = "resources/images/mediumStrongHand.png";
          String powerBarStrongHand = "resources/images/StrongHand.png";

          Image image = new Image(Paths.get(powerBarMediumWeakHand).toUri().toString());
          Assertions.assertEquals(image.getUrl(), gameController.getImage().getUrl());
     }
     @Test
     void handHelpWeak() {
          Deck deck = new Deck();
          Card card1 = deck.getCardByIndex(0);
          Card card2 = deck.getCardByIndex(44);
          System.out.println(card1.getCardSuit() + " " + card1.getCardValue());
          System.out.println(card2.getCardSuit() + " " + card2.getCardValue());

          gameController.setStartingHand(card1, card2);
          sleep(4000);
          String powerBarWeakHand = "resources/images/weakHand.png";
          String powerBarMediumWeakHand = "resources/images/mediumWeakHand.png";
          String powerBarMediumStrongHand = "resources/images/mediumStrongHand.png";
          String powerBarStrongHand = "resources/images/StrongHand.png";

          Image image = new Image(Paths.get(powerBarWeakHand).toUri().toString());
          Assertions.assertEquals(image.getUrl(), gameController.getImage().getUrl());
     }

     @Test
     void addLogMessage(){
          gameController.addLogMessage("ADDING LOG MESSAGE");
          TextFlow textFlow = gameController.getLogTextFlow();
          sleep(3500);
          Assertions.assertEquals("ADDING LOG MESSAGE\n", ((Text)(textFlow.getChildren().get(0))).getText());
     }

     @Test
     void testesttest() {
          FxRobot fxRobot = new FxRobot();
     }

     void sleep(int ms){
          try{
               Thread.sleep(ms);
          } catch (Exception e){}
     }
}
