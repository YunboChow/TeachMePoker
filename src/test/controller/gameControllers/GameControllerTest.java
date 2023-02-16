package test.controller.gameControllers;

import controller.gameControllers.GameController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GameControllerTest {
     GameController gameController = new GameController();

     @Test
     void playerAllInVerifyPot(){
          gameController.initialize();
          gameController.setPlayerPot(1000);
          gameController.playerAllIn();
          assertEquals(gameController.getPlayerPot(), 0);
     }


}
