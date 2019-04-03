package src;

import javafx.beans.DefaultProperty;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.Random;


@DefaultProperty("children")
public class Locator extends Region {
    private double size;
    private double radius;
    private double sin;
    private double cos;
    private double width;
    private double height;
    private Circle background = new Circle();
    private Circle foreground = new Circle();
    private Rectangle indicator = new Rectangle();
    private Rectangle indicatorTop = new Rectangle();
    private Rectangle indicatorBottom = new Rectangle();
    private Rectangle upperRect = new Rectangle();
    private Rectangle target = new Rectangle();
    private Pane pane;
    private Rotate rotate = new Rotate();
    private Rotate rotateTop = new Rotate();
    private Rotate rotateBottom = new Rotate();
    private double _angle = 0.0;
    private Paint _backgroundPaint;
    private Paint _foregroundPaint;
    private Paint _indicatorPaint;
    private InnerShadow innerShadow;
//    private Rectangle rect1 = new Rectangle();
//    private Rectangle rect2 = new Rectangle();
//    private Rectangle rect3 = new Rectangle();

    private int angleForTarget = 0;
    private int topAngle = 52;
    private int bottomAngle = 360 - topAngle;
    private double x = 0;
    private double y = 0;
    private int signY = 1;
    private int signX = 1;

    private boolean scTurnOn = false;

    // ******************** Constructors **************************************
    public Locator() throws Exception {
        getStylesheets().add(Locator.class.getResource("radar.css").toExternalForm());
        _backgroundPaint = Color.rgb(32, 32, 32);
        _foregroundPaint = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.rgb(61, 61, 61)),
                new Stop(0.5, Color.rgb(50, 50, 50)),
                new Stop(1.0, Color.rgb(42, 42, 42)));
        _indicatorPaint = Color.rgb(159, 159, 159);

        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
    private void initGraphics() {

        getStyleClass().add("angle-picker");

        rotate.setAngle(0);
        rotateTop.setAngle(topAngle);
        rotateBottom.setAngle(bottomAngle);

        // мишура (верхний градиент)
        innerShadow = new InnerShadow(BlurType.TWO_PASS_BOX, Color.rgb(255, 255, 255, 0.3), 1, 0.0, 0, 0.5);

        background.setFill(_backgroundPaint);
        background.setMouseTransparent(true);

        foreground.setFill(_foregroundPaint);
        foreground.setEffect(innerShadow);

        upperRect.setFill(_indicatorPaint);

        indicator.getTransforms().add(rotate);
        indicator.setMouseTransparent(true);

        indicatorTop.getTransforms().add(rotateTop);
        indicatorTop.setMouseTransparent(true);

        indicatorBottom.getTransforms().add(rotateBottom);
        indicatorBottom.setMouseTransparent(true);

        target.setVisible(false);

        pane = new Pane(background, foreground, indicator, indicatorTop, indicatorBottom, upperRect, target);


        ////////////////////////////////////////////////////////
//        int w = 10;
//        rect1 = new Rectangle(w, w);
//        rect2 = new Rectangle(w, w);
//        rect3 = new Rectangle(w, w);
//        pane.getChildren().addAll(rect1, rect2, rect3);
        ////////////////////////////////////////////////////////

        getChildren().setAll(pane);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
    }


    public void setAngle(final Double angle) {
        _angle = angle % 360.0;
        rotate.setAngle(_angle);
        rotateTop.setAngle(_angle + topAngle);
        rotateBottom.setAngle(_angle + bottomAngle);
    }

    public void onKpClicked() {
        int angle = new Random().nextInt(360);
        new Target(angle, target, radius, size);
    }

    public void onAutoClicked() {
    }


    public void onScClicked() {
        scTurnOn = true;
    }


    public void onRuClicked() {

    }

    // ******************** Resizing ******************************************
    private void resize() {
        width = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size = width < height ? width : height;
        radius = size * 0.5;

        if (width > 0 && height > 0) {
            pane.setMaxSize(size, size);
            pane.setPrefSize(size, size);
            pane.relocate((getWidth() - size) * 0.5, (getHeight() - size) * 0.5);

            innerShadow.setRadius(size * 0.0212766);
            innerShadow.setOffsetY(size * 0.0106383);

            background.setRadius(size * 0.5);
            background.relocate(0, 0);

            foreground.setRadius(size * 0.4787234);
            foreground.relocate(size * 0.0212766, size * 0.0212766);


            //
            rotate.setPivotX(-size * 0.37);
            rotate.setPivotY(0);

            indicator.setWidth(size * 0.1);
            indicator.setHeight(size * 0.01587302);
            indicator.relocate(size * 0.87, size * 0.5);
            //

            //
            rotateTop.setPivotX(indicatorTop.getX() - size * 0.1);
            rotateTop.setPivotY(indicatorTop.getHeight() * 0.5);

            indicatorTop.setWidth(size * 0.37);
            indicatorTop.setHeight(size * 0.01587302);
            indicatorTop.relocate(size * 0.6, size * 0.5);
            //

            //
            rotateBottom.setPivotX(indicatorBottom.getX() - size * 0.1);
            rotateBottom.setPivotY(indicatorBottom.getHeight() * 0.5);

            indicatorBottom.setWidth(size * 0.37);
            indicatorBottom.setHeight(size * 0.01587302);
            indicatorBottom.relocate(size * 0.6, size * 0.5);
            //

            upperRect.setWidth(size * 0.01);
            upperRect.setHeight(size * 0.1);
            upperRect.relocate(size * 0.5, size * 0.02);

            redraw();
        }
    }

    private void redraw() {
        background.setFill(_backgroundPaint);
        foreground.setFill(_foregroundPaint);
        indicator.setFill(_indicatorPaint);
        indicatorTop.setFill(_indicatorPaint);
        indicatorBottom.setFill(_indicatorPaint);
    }
}
