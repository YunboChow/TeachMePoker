package controller.gameControllers;

import controller.gameControllers.GameController;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import view.Main;

import static org.junit.jupiter.api.Assertions.*;


public class GameControllerTest {
     GameController gameController = new GameController();
     static Stage window;
     @Before
     void startUp() throws Exception {
          Main main = new Main();
          main.start(window);
     }


     @Test
     void playerAllInVerifyPot(){
          gameController.initialize();
          gameController.setPlayerPot(1000);
          gameController.playerAllIn();
          assertEquals(gameController.getPlayerPot(), 0);
     }
}
