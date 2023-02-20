package controller.gameControllers;

import controller.SPController;
import javafx.application.Application;
import controller.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;



class GameControllerTest {
     Pane mainroot;
     Stage mainStage;
     static GameController gameController;
     static SPController spController;

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

     @Test
     static void startMainProgram() {
          try {
               Thread.sleep(2000);
          } catch (InterruptedException e) {
               throw new RuntimeException(e);
          }
          Main main = Main.getMain();
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
                    spController = new SPController();
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
      * @author: Anthon Haväng
      */
     @Test
     void playerAllInTest(){
          gameController.setPlayerPot(1000);
          gameController.playerAllIn();
          Assertions.assertEquals(0, gameController.getPlayerPot());
     }
}
