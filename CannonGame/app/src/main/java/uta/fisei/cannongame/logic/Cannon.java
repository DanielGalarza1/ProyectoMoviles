package uta.fisei.cannongame.logic;

// Importa las clases necesarias de Android
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import uta.fisei.cannongame.CannonView;
public class Cannon {
    // El radio de la base del cañón
    private int baseRadius;

    // La longitud del cañón
    private int barrelLength;

    // El punto final del cañón
    private Point barrelEnd = new Point();

    // El ángulo del cañón
    private double barrelAngle;

    // La bala del cañón
    private Cannonball cannonball;

    // Paint utilizado para dibujar el cañón
    private Paint paint = new Paint();

    // La vista que contiene el cañón
    private CannonView view;

    // Constructor de Cannon
    public Cannon(CannonView view, int baseRadius, int barrelLength, int barrelWidth) {
        this.view = view;
        this.baseRadius = baseRadius;
        this.barrelLength = barrelLength;

        // Establece el ancho del cañón
        paint.setStrokeWidth(barrelWidth);

        // El color del cañón es negro
        paint.setColor(Color.BLACK);

        // Alinea el cañón para que el cañón apunte hacia la derecha
        align(Math.PI / 2);
    }

    // aligns the Cannon's barrel to the given angle
    public void align(double barrelAngle) {
        this.barrelAngle = barrelAngle;
        barrelEnd.x = (int) (barrelLength * Math.sin(barrelAngle));
        barrelEnd.y = (int) (-barrelLength * Math.cos(barrelAngle)) +
                view.getScreenHeight() / 2;
    }

    // creates and fires Cannonball in the direction Cannon points
    public void fireCannonball() {
        // calculate the Cannonball velocity's x component
        int velocityX = (int) (CannonView.CANNONBALL_SPEED_PERCENT *
                view.getScreenWidth() * Math.sin(barrelAngle));

        // calculate the Cannonball velocity's y component
        int velocityY = (int) (CannonView.CANNONBALL_SPEED_PERCENT *
                view.getScreenWidth() * -Math.cos(barrelAngle));

        // calculate the Cannonball's radius
        int radius = (int) (view.getScreenHeight() * CannonView.CANNONBALL_RADIUS_PERCENT);

        // construct Cannonball and position it in the Cannon
        cannonball = new Cannonball(view, Color.BLACK,
                CannonView.CANNON_SOUND_ID, -radius,
                view.getScreenHeight() / 2 - radius, radius, velocityX,
                velocityY);

        // play fire Cannonball sound
        cannonball.playSound();
    }

    // draws the Cannon on the Canvas
    public void draw(Canvas canvas) {
        // draw cannon barrel
        canvas.drawLine(0, view.getScreenHeight() / 2, barrelEnd.x,
                barrelEnd.y, paint);

        // draw cannon base
        canvas.drawCircle(0, (int) view.getScreenHeight() / 2,
                (int) baseRadius, paint);
    }

    // returns the Cannonball that this Cannon fired
    public Cannonball getCannonball() {
        return cannonball;
    }

    // removes the Cannonball from the game
    public void removeCannonball() {
        cannonball = null;
    }

}
