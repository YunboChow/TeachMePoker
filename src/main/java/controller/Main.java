package controller;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.ConfirmBox;
import java.awt.*;

/**
 * controller.Main method to start the program.
 *
 * @author Lykke Levin
 * @version 1.0
 * @author Anthon Haväng
 * @version 2.0
 */

public class Main extends Application {
     public static Main main = new Main();
     public static Stage window;
     public SceneController cs = new SceneController();

     /**
      * Called when the application is launched.
      */
     public void start(Stage primaryStage) throws Exception {
          window = primaryStage;
          //Application Icon
          window.getIcons().add(new Image(getClass().getResourceAsStream("/images/appIcon.png")));
          //Taskbar Icon
          if (Taskbar.isTaskbarSupported()) {
               var taskbar = Taskbar.getTaskbar();

               if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                    final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                    var dockIcon = defaultToolkit.getImage(getClass().getResource("/images/dockIcon.png"));
                    taskbar.setIconImage(dockIcon);
               }

          }
          window.setTitle("TeachMePoker");
          window.setResizable(false);
          window.setOnCloseRequest(e ->{
               e.consume();
               closeProgram();
          });
          cs.prepGame();
          window.setScene(cs.firstScene());
          window.show();

     }

     /**
      * Launches the application.
      * @param args
      */
     public static void main(String[] args) {
          launch(args);

     }

     /**
      * Shows pop-up to confirm closing. Closes the window and exits the program if confirmed.
      */
     public void closeProgram() {
          if (ConfirmBox.yesNoOption("Avsluta?", "Ingenting sparas när du avslutar spelet. Är du säker " +
                    "på att du vill avsluta Teach Me Poker nu?")) {
               window.close();
               System.exit(0);
          }
     }

     /**
      * @author: Anthon Haväng
      * @return a static object of itself.
      */
     public static Main getMain() {
          return Main.main;
     }
}
