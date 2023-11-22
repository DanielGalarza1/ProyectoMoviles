package uta.fisei.cannongame.logic;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import uta.fisei.cannongame.CannonView;
public class GameElement {
    // La vista que contiene este GameElement
    protected CannonView view;

    // Paint para dibujar este GameElement
    protected Paint paint = new Paint();

    // Los límites rectangulares de este GameElement
    protected Rect shape;

    // La velocidad vertical de este GameElement
    private float velocityY;

    // El sonido asociado con este GameElement
    private int soundId;

    // Constructor público
    public GameElement(CannonView view, int color, int soundId, int x, int y, int width, int length, float velocityY) {
        this.view = view;
        paint.setColor(color);
        shape = new Rect(x, y, x + width, y + length); // Establece los límites
        this.soundId = soundId;
        this.velocityY = velocityY;
    }

    // Actualiza la posición del GameElement y verifica las colisiones con las paredes
    public void update(double interval) {
        // Actualiza la posición vertical
        shape.offset(0, (int) (velocityY * interval));

        // Si el GameElement colisiona con la pared, invierte la dirección
        if ((shape.top < 0 && velocityY < 0) || (shape.bottom > view.getScreenHeight() && velocityY > 0)) {
            velocityY *= -1; // Invierte la velocidad vertical del GameElement
        }
    }

    // Dibuja este GameElement en el Canvas proporcionado
    public void draw(Canvas canvas) {
        canvas.drawRect(shape, paint);
    }

    // Reproduce el sonido que corresponde a este tipo de GameElement
    public void playSound() {
        view.playSound(soundId);
    }
}