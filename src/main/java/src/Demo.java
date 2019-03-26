package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Demo extends Application {

    private Double angle = 0.0;

    private Locator locatorPanel = new Locator(angle);
    private Controller controllerPanel = new Controller((Double angle) -> locatorPanel.setAngle(angle));
    private Button button1 = new Button("-");
    private Button button2 = new Button("+");

    @Override
    public void init() {
        locatorPanel.setPrefSize(500, 500);
        controllerPanel.setPrefSize(150, 150);
        button1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            angle = angle - 5;
            locatorPanel.setAngle(angle);
        });
        button2.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            angle = angle + 5;
            locatorPanel.setAngle(angle);
        });
    }

    @Override
    public void start(Stage stage) {
        BorderPane pane = new BorderPane();
        pane.setCenter(locatorPanel);
        pane.setRight(controllerPanel);
        pane.setTop(button2);
        pane.setBottom(button1);
        pane.setBackground(new Background(new BackgroundFill(Color.web("#2e2e2e"), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setPadding(new Insets(30));

        Scene scene = new Scene(pane, 1000, 1000);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
