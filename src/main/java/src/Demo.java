package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Demo extends Application {
    private Locator locatorPanel = new Locator();
    private Controller controllerPanel = new Controller((Double angle) -> locatorPanel.setAngle(angle));

    public Demo() throws Exception {
    }

    @Override public void init() {
        locatorPanel.setPrefSize(500, 500);
        controllerPanel.setPrefSize(150, 150);
        BorderPane.setMargin(controllerPanel, new Insets(500, 0, 0, 0));
    }

    @Override public void start(Stage stage) {
        BorderPane pane = new BorderPane();
        pane.setCenter(locatorPanel);
        pane.setRight(controllerPanel);
        pane.setBackground(new Background(new BackgroundFill(Color.web("#2e2e2e"), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setPadding(new Insets(30));

        Scene scene = new Scene(pane, 1000, 1000);

        stage.setScene(scene);
        stage.show();
    }

    @Override public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
