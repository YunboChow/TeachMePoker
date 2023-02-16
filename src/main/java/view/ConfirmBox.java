package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Window with text and buttons containing a message.
 * 
 * @author Lykke Levin, Niklas Hultin
 * @version 1.0
 *
 */

public class ConfirmBox {



  /**
   * Creates a window containing an informative message with only an OK-button.
   * @param title String title of the window from the classes that uses ConfirmBox. 
   * @param message String message to display in the window from the classes that uses ConfirmBox.
   */
  public static void display(String title, String message) {
    Stage window = new Stage();
    Font font = new Font("Tw Cen MT", 18);
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(150);
    window.setMaxWidth(600);
    window.setHeight(400);
    window.setOnCloseRequest(e -> window.close());

    Label label = new Label();
    label.setFont(font);
    label.setText(message);
    label.setWrapText(true);

    Button buttonOk = new Button("Ok");
    buttonOk.setFont(font);

    buttonOk.setOnAction(e -> {
      window.close();
    });

    VBox layout = new VBox(10);
    layout.setPadding(new Insets(10, 10, 10, 10));
    layout.getChildren().addAll(label, buttonOk);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }
  public static void display(String title, String message, String imagePath) {
    Stage window = new Stage();
    Font font = new Font("Tw Cen MT", 18);
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(150);
    window.setMaxWidth(600);
    window.setHeight(700);
    window.setOnCloseRequest(e -> window.close());

    Label label = new Label();
    label.setFont(font);
    label.setText(message);
    label.setWrapText(true);

    Image image = new Image(ConfirmBox.class.getResourceAsStream(imagePath));
    ImageView imageView = new ImageView();
    imageView.setImage(image);
    imageView.setX(10);
    imageView.setY(10);
    imageView.setFitWidth(500);
    imageView.setPreserveRatio(true);

    Button buttonOk = new Button("Ok");
    buttonOk.setFont(font);

    buttonOk.setOnAction(e -> {
      window.close();
    });

    VBox layout = new VBox(10);
    layout.setPadding(new Insets(10, 10, 10, 10));
    layout.getChildren().addAll(label,imageView,buttonOk);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }

  /**
   * Creates a window containing an question with Yes and No buttons.
   * @param title String title of the window from the classes that uses ConfirmBox.
   * @param message String message to display in the window from the classes that uses ConfirmBox.
   * @return The users answer (Yes = true, No = false)
   */
  public static boolean yesNoOption(String title, String message) {
    Stage window = new Stage();
    Font font = new Font("Tw Cen MT", 18);
    AtomicBoolean answer = new AtomicBoolean(false);

    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(150);
    window.setMaxWidth(600);
    window.setHeight(200);
    window.setOnCloseRequest(e -> window.close());

    Label label = new Label();
    label.setFont(font);
    label.setText(message);
    label.setWrapText(true);

    Button buttonYes = new Button("Ja");
    buttonYes.setFont(font);

    buttonYes.setOnAction(e -> {
      answer.set(true);
      window.close();
    });

    Button buttonNo = new Button("Nej");
    buttonNo.setFont(font);

    buttonNo.setOnAction(e -> {
      answer.set(false);
      window.close();
    });

    VBox layout = new VBox(10);
    layout.setPadding(new Insets(10, 10, 10, 10));
    layout.getChildren().addAll(label, buttonYes, buttonNo);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();

    return answer.get();
  }


}
