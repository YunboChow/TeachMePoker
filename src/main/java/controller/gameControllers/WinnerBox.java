package controller.gameControllers;


import java.nio.file.Paths;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Box that shows the winner of round.
 * <p>
 * version 1.0
 *
 * @author Lykke Levin
 * version 1.1
 * @author Anthon Haväng, Jens Bjerre, Erik Larsson
 */
public class WinnerBox {

     public boolean answer = false;
     public Stage window = new Stage();
     public Font font = new Font("Tw Cen MT", 18);
     private ImageView back = new ImageView(Paths.get("resources/images/background.png").toUri().toString());
     private ImageView btnOk = new ImageView(Paths.get("resources/images/okButton.png").toUri().toString());


     /**
      * Creates a window containting messages of who won or lost.
      *
      * @param title        String title of the window from the method that uses WinnerBox.
      * @param message      String message to display in the window from the method that uses ConfirmBox.
      * @param nr           Int to check which message should be displayed.
      * @param handStrength String to print the handstrength the player or AI won with.
      * @return answer Boolean that returns an answer.
      * Amended by: Anthon Haväng, Erik Larsson, Jens Bjerre: simplified a long else if-else if section to a simple
      * switch-case. Automatically convert from else-if to switch-case by OpenAI.
      */
     public boolean displayWinner(String title, String message, int nr, String handStrength) {

          String aiWin = "Rundan vanns av " + message + " som hade " + handStrength;
          String playerWin = "Grattis " + message + ", du vann den här rundan! Du vann med " + handStrength;
          String playerWinAIFold = "Grattis " + message + ". " + handStrength;
          String aiWinOthersFold = "Rundan vanns av " + message + " " + handStrength;

          window.initModality(Modality.APPLICATION_MODAL);
          window.setTitle(title);
          window.setWidth(400);
          window.setHeight(200);
          window.setOnCloseRequest(e -> closeProgram());

          Pane pane = new Pane();

          Label messageText = new Label();
          messageText.setFont(font);
          messageText.setTextFill(Color.WHITE);
          messageText.setWrapText(true);

          switch (nr) {
               case 1 -> messageText.setText(playerWin);
               case 2 -> messageText.setText(aiWin);
               case 3 -> messageText.setText(playerWinAIFold);
               case 4 -> messageText.setText(aiWinOthersFold);
               case 5 -> messageText.setText(message);
          }
          btnOk.setOnMouseReleased(e -> {
               answer = true;
               closeProgram();
          });

          back.setFitHeight(window.getHeight());
          back.setFitWidth(window.getWidth());
          messageText.setPrefSize(200, 100);
          messageText.setLayoutX(100);
          messageText.setLayoutY(10);
          btnOk.setFitHeight(35);
          btnOk.setFitWidth(35);
          btnOk.setLayoutX(175);
          btnOk.setLayoutY(110);
          pane.getChildren().addAll(back, messageText, btnOk);

          Scene scene = new Scene(pane);
          window.setScene(scene);
          window.showAndWait();
          return answer;

     }

     /**
      * Closes the window.
      */
     public void closeProgram() {
          window.close();
     }

}
