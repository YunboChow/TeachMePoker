package controller.gameControllers;

import controller.SPController;
import controller.Main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author: Anthon Haväng
 * Test cases for controller.gameControllers.GameController.java
 */
class GameControllerTest {
     private static GameController gameController;
     private static SPController spController;

     /**
      * -------------- TEST ENVIRONMENT SETUP --------------
      */

     /**
      * @author: Anthon Haväng
      */
     @BeforeEach
     void prepSPController() {
          spController = new SPController();
          gameController.setSPController(spController);
     }

     /**
      * @author: Anthon Haväng
      */
     @BeforeAll
     static void initJFXRuntime() {
          new Thread(() -> Application.launch(Main.class)).start();
     }

     /**
      * @author: Anthon Haväng
      */
     @BeforeEach
     @Test
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
      * @author: Anthon Haväng
      * Krav: KF-3.4.3.4
      */
     @Test
     void playerAllInTest(){
          gameController.setPlayerPot(1000);
          gameController.playerAllIn();
          Assertions.assertEquals(0, gameController.getPlayerPot());
     }
}
