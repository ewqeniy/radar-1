package src;

import javafx.scene.shape.Rectangle;

import java.util.Random;
import java.util.concurrent.Executors;

public class Target {
    public double angle = 0;
    public double x = 0;
    public double y = 0;
    private double sin = 0;
    private double cos = 0;
    private int signX = 1;
    private int signY = 1;
    private Rectangle target;
    private double radius;
    private double size;
    private double movingMode = 0;
    double xStart;
    double yStart;

    public Target(int angle, Rectangle target, double center, double distance, double size) {
        angle = -angle;
        this.angle = angle;
        this.target = target;
        this.radius = center;
        this.size = size * 0.95;
        sin = Math.sin(AngleUtils.gradusToRodian(angle));
        cos = Math.cos(AngleUtils.gradusToRodian(angle));
        xStart = radius + distance * cos;
        yStart = radius + distance * sin;
        movingMode = new Random().nextInt(2);
        //redrawTarget();
        startTarget();
    }

    public void startTarget() {
        Executors.newFixedThreadPool(1).submit(() -> {
            try {
                Thread.sleep(1000);

                for (int i = 0; i < 100; i++) {
                    if (angle <= -0 && angle >= -90) {
                        signX = -1;
                        signY = +1;
                    }
                    if (angle < -90 && angle >= -180) {
                        signX = +1;
                        signY = +1;
                    }
                    if (angle < -180 && angle >= -270) {
                        signX = +1;
                        signY = -1;
                    }
                    if (angle < -270 && angle > -360) {
                        signX = -1;
                        signY = -1;
                    }
                    redrawTarget();
                    Thread.sleep(1000);
                    if((x>radius-radius*0.1 && x<radius+radius*0.1 && y<radius+radius*0.1 && y>radius-radius*0.1)
                    || AngleUtils.getDistance(x, y, radius) >= radius*0.93) {
                        target.setVisible(false);
                        return;
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void redrawTarget() {

        target.setVisible(true);

        if (x == 0) { x = xStart; }
        if (y == 0) { y = yStart; }

        System.out.println("1:::::" + x + "     "+ y);

        if (movingMode == 0) { // перемещаемся к центру
            double oldX = x;
            if (angle == 270) { y += 5; } else
                if (angle == 90) { y -= 5; } else
                    { x += 5 * signX; y = (((x - radius) * (y - radius)) / (oldX - radius)) + radius; }
        } else { // перемещаемся вдоль
            if ( angle <= -0 && angle >= -90 ) { y += 5; }
            if ( angle < -90 && angle >= -180 ) { x += 5; }
            if ( angle < -180 && angle >= -270 ) { y -= 5; }
            if ( angle < -270 && angle > -360 ) { x -= 5; }
        }


        target.relocate(x, y);

        target.setWidth(size * 0.03);
        target.setHeight(size * 0.03);
    }



}
