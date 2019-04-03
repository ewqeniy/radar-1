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
    private Locator locatorPanel = new Locator();
    private Controller controllerPanel = new Controller((Double angle) -> locatorPanel.setAngle(angle));
    private Button kpBtn = new Button("КП");
    private Button autoBtn = new Button("Авто");
    private Button scBtn = new Button("СЦ");
    private Button ruBtn = new Button("РУ");



    public Demo() throws Exception {
    }

    @Override
    public void init() {
        kpBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> { locatorPanel.onKpClicked(); });
        autoBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> { locatorPanel.onAutoClicked(); });
        scBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> { locatorPanel.onScClicked(); });
        ruBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> { locatorPanel.onRuClicked(); });
        locatorPanel.setPrefSize(500, 500);
        controllerPanel.setPrefSize(150, 150);
        BorderPane.setMargin(controllerPanel, new Insets(500, 0, 0, 0));
    }

    @Override
    public void start(Stage stage) {
        BorderPane pane = new BorderPane();
        pane.setCenter(locatorPanel);
        pane.setRight(getRightPane());

        pane.setBackground(new Background(new BackgroundFill(Color.web("#2e2e2e"), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setPadding(new Insets(30));

        Scene scene = new Scene(pane, 1300, 1000);

        stage.setScene(scene);
        stage.show();
    }

    private Pane getRightPane() {
        BorderPane.setMargin(kpBtn, new Insets(0, 100, 0, 0));
        BorderPane topPane = new BorderPane();
        topPane.setLeft(kpBtn); topPane.setRight(autoBtn);
        BorderPane bottomPane = new BorderPane();
        bottomPane.setLeft(scBtn); bottomPane.setRight(ruBtn);

        BorderPane paneForReturn = new BorderPane();
        paneForReturn.setTop(topPane);
        paneForReturn.setBottom(bottomPane);
        BorderPane.setMargin(controllerPanel, new Insets(100, 0, 100,  0));
        paneForReturn.setCenter(controllerPanel);
        return paneForReturn;
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
