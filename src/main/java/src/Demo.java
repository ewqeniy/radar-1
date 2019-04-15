package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Demo extends Application {
    private Locator locatorPanel = new Locator();
    private Controller controllerPanel = new Controller((Double angle) -> locatorPanel.setAngle(angle));
    private Button kpBtn = new Button();
    private Label kpLabel = new Label("     КП");
    private static final String RED_PRESSED = "-fx-background-color: #FF7465; -fx-border-radius: 1; -fx-border-color: yellow;";
    private Button autoBtn = new Button();
    private Label autoLabel = new Label("АВТОНОМНО");
    private static final String RED_NOT_PRESSED = "-fx-background-color: #FF0000; -fx-border-radius: 1; -fx-border-color: black;";
    private Button scBtn = new Button("СЦ");
    private static final String BLUE_PRESSED = "-fx-background-color: #5970FF; -fx-border-radius: 1; -fx-border-color: yellow;";
    private Button ruBtn = new Button();
    private Label ruLable = new Label("      РУ");
    private static final String BLUE_NOT_PRESSED = "-fx-background-color: #0100FF; -fx-border-radius: 1; -fx-border-color: black;";
    private boolean kpPressed = false;
    private boolean autoPressed = false;
    private boolean scPressed = false;
    private boolean ruPressed = false;


    private BottomTable bottomTable = new BottomTable(locatorPanel::addNewTarget);

    public Demo() throws Exception {
    }

    @Override
    public void init() {
        kpBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            locatorPanel.onKpClicked();
            kpPressed = !kpPressed;
            if (!kpPressed) {
                kpBtn.setStyle(RED_NOT_PRESSED);
            } else kpBtn.setStyle(RED_PRESSED);
        });
        autoBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            //locatorPanel.onAutoClicked();
            autoPressed = !autoPressed;
            if (!autoPressed) {
                autoBtn.setStyle(BLUE_NOT_PRESSED);
            } else autoBtn.setStyle(BLUE_PRESSED);
        });
        scBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            locatorPanel.onScClicked();
        });
        ruBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            locatorPanel.onRuClicked();
            ruPressed = !ruPressed;
            if (!ruPressed) {
                ruBtn.setStyle(RED_NOT_PRESSED);
            } else ruBtn.setStyle(RED_PRESSED);
        });
        locatorPanel.setPrefSize(400, 400);
        controllerPanel.setPrefSize(150, 150);
        bottomTable.setPrefSize(500, 150);

        BorderPane.setMargin(controllerPanel, new Insets(500, 0, 0, 0));
    }

    @Override
    public void start(Stage stage) {
        kpBtn.setPrefSize(50, 50);
        kpBtn.setStyle(RED_NOT_PRESSED);
        autoBtn.setPrefSize(50, 50);
        autoBtn.setStyle(BLUE_NOT_PRESSED);
        ruBtn.setPrefSize(50, 50);
        ruBtn.setStyle(RED_NOT_PRESSED);
        BorderPane pane = new BorderPane();
        pane.setCenter(locatorPanel);
        pane.setRight(getRightPane());
        pane.setBottom(bottomTable);

        pane.setBackground(new Background(new BackgroundFill(Color.web("#BEC85D"), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setPadding(new Insets(30));

        Scene scene = new Scene(pane, 1300, 1000);
        scene.getStylesheets().add(getClass().getResource("btn.css").toExternalForm());
        stage.setScene(scene);
        //TODO check on computers in university and thinking about correct way
//        stage.setResizable(false);
        stage.show();
    }

    private Pane getRightPane() {
        BorderPane.setMargin(kpBtn, new Insets(0, 100, 0, 0));
        BorderPane topPane = new BorderPane();
        BorderPane topLeftPane = new BorderPane();
        topLeftPane.setTop(kpBtn);
        topLeftPane.setBottom(kpLabel);
        BorderPane topRightPane = new BorderPane();
        topRightPane.setTop(autoBtn);
        topRightPane.setBottom(autoLabel);
        topPane.setLeft(topLeftPane);
        topPane.setRight(topRightPane);
        BorderPane bottomPane = new BorderPane();
        BorderPane bottomLeftPane = new BorderPane();
        bottomLeftPane.setTop(scBtn);
        BorderPane bottomRightPane = new BorderPane();
        bottomRightPane.setBottom(ruLable);
        bottomRightPane.setTop(ruBtn);
        bottomPane.setLeft(scBtn);
        bottomPane.setRight(bottomRightPane);

        BorderPane paneForReturn = new BorderPane();
        paneForReturn.setTop(topPane);
        paneForReturn.setBottom(bottomPane);
        BorderPane.setMargin(controllerPanel, new Insets(100, 0, 100, 0));
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
