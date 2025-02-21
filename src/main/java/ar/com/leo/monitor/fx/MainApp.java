package ar.com.leo.monitor.fx;

import java.io.IOException;

import ar.com.leo.monitor.fx.controller.MonitorController;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.stage.StageStyle;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);

        final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        // set Stage boundaries to visible bounds of the main screen
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth()); // 1536
        stage.setHeight(primaryScreenBounds.getHeight()); // 864
        stage.setMaximized(true);
        stage.toFront();
        stage.setAlwaysOnTop(true);
//        stage.setFullScreen(true);
//        stage.setResizable(false);
        try {
            System.out.println("Parámetros: " + getParameters().getRaw());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Monitor.fxml"));
            loader.setControllerFactory(controllerClass -> new MonitorController(getParameters().getRaw()));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
//            LOG.error(e);
            e.printStackTrace();
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // start JavaFX
        launch(args);
    }
}
