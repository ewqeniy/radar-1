package src;

import javafx.beans.DefaultProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.function.Consumer;

@DefaultProperty("children")
public class Controller extends Region {
    private              double                   size;
    private              double                   width;
    private              double                   height;
    private Circle background = new Circle();
    private              Circle                   foreground = new Circle();
    private Rectangle indicator = new Rectangle();
    private Pane pane;
    private Rotate rotate = new Rotate();
    private              double                   _angle = 250;
    private Paint _backgroundPaint;
    private              Paint                    _foregroundPaint;
    private              Paint                    _indicatorPaint;
    private InnerShadow innerShadow;
    private EventHandler<MouseEvent> mouseFilter;
    private Consumer<Double> setAngle;

    // ******************** Constructors **************************************
    public Controller(Consumer<Double> setAngle) {
        getStylesheets().add(Locator.class.getResource("radar.css").toExternalForm());
        this.setAngle = setAngle;
        _backgroundPaint = Color.rgb(32, 32, 32);
        _foregroundPaint = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.rgb(61, 61, 61)),
                new Stop(0.5, Color.rgb(50, 50, 50)),
                new Stop(1.0, Color.rgb(42, 42, 42)));
        _indicatorPaint  = Color.rgb(159, 159, 159);


        mouseFilter = evt -> {
            EventType<? extends MouseEvent> type = evt.getEventType();
            if (type.equals(MouseEvent.MOUSE_DRAGGED)) {
                double tmpAngle = getAngleFromXY(evt.getX() + size * 0.5, evt.getY() + size * 0.5, size * 0.5, size * 0.5, 0);
                setAngle(tmpAngle);
            }
        };

        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
    private void initGraphics() {

        getStyleClass().add("angle-picker");

        rotate.setAngle(_angle);
        setAngle.accept(_angle);

        // мишура (верхний градиент)
        innerShadow = new InnerShadow(BlurType.TWO_PASS_BOX, Color.rgb(255, 255, 255, 0.3), 1, 0.0, 0, 0.5);

        background.setFill(_backgroundPaint);
        background.setMouseTransparent(true);

        foreground.setFill(_foregroundPaint);
        foreground.setEffect(innerShadow);

        indicator.getTransforms().add(rotate);
        indicator.setMouseTransparent(true);

        pane = new Pane(background, foreground, indicator);

        getChildren().setAll(pane);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
        foreground.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseFilter);
        foreground.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseFilter);
    }

    public void setAngle(final double angle) {
        _angle = angle % 360.0;
        rotate.setAngle(_angle);
        setAngle.accept(_angle);
    }

    private double getAngleFromXY(final double x, final double y, final double centerX, final double centerY, final double angleOffset) {
        // For ANGLE_OFFSET =  0 -> Angle of 0 is at 3 o'clock
        // For ANGLE_OFFSET = 90  ->Angle of 0 is at 12 o'clock
        double deltaX = x - centerX;
        double deltaY = y - centerY;
        double radius = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
        double nx     = deltaX / radius;
        double ny     = deltaY / radius;
        double theta  = Math.atan2(ny, nx);
        theta         = Double.compare(theta, 0.0) >= 0 ? Math.toDegrees(theta) : Math.toDegrees((theta)) + 360.0;
        double angle  = (theta + angleOffset) % 360;
        return angle;
    }


    // ******************** Resizing ******************************************
    private void resize() {
        width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size   = width < height ? width : height;

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
//            rotate.setPivotX(indicator.getX() - size * 0.5);
//            rotate.setPivotY(size * 0.5 - indicator.getHeight()*0.5);
            rotate.setPivotX(-size * 0.37);
            rotate.setPivotY(0);

            indicator.setWidth(size * 0.1);
            indicator.setHeight(size * 0.01587302);
            indicator.relocate(size * 0.87, size * 0.5);
            //
            redraw();
        }
    }

    private void redraw() {
        background.setFill(_backgroundPaint);
        foreground.setFill(_foregroundPaint);
        indicator.setFill(_indicatorPaint);
    }
}

