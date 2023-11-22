package uta.fisei.cannongame.logic;

import android.graphics.Canvas;
import android.graphics.Rect;

import uta.fisei.cannongame.CannonView;
public class Cannonball extends GameElement {

    private float velocityX; // Componente x de la velocidad de la bala del cañón
    private boolean onScreen; // Indica si la bala del cañón está en la pantalla

    // Constructor de Cannonball
    public Cannonball(CannonView view, int color, int soundId, int x,
                      int y, int radius, float velocityX, float velocityY) {
        // Llama al constructor de la clase padre (GameElement)
        super(view, color, soundId, x, y, 2 * radius, 2 * radius, velocityY);

        // Inicializa las variables específicas de Cannonball
        this.velocityX = velocityX;
        onScreen = true;
    }
    // Método para obtener el radio de la bala del cañón
    private int getRadius() {
        return (shape.right - shape.left) / 2;
    }

    // Método para verificar si la bala del cañón colisiona con el elemento de juego dado
    public boolean collidesWith(GameElement element) {
        return (Rect.intersects(shape, element.shape) && velocityX > 0);
    }

    // Método para verificar si la bala del cañón está en la pantalla
    public boolean isOnScreen() {
        return onScreen;
    }

    // Método para invertir la velocidad horizontal de la bala del cañón
    public void reverseVelocityX() {
        velocityX *= -1;
    }

    // Método para actualizar la posición de la bala del cañón
    @Override
    public void update(double interval) {
        super.update(interval); // Actualiza la posición vertical de la bala del cañón

        // Actualiza la posición horizontal
        shape.offset((int) (velocityX * interval), 0);

        // Si la bala del cañón se sale de la pantalla
        if (shape.top < 0 || shape.left < 0 ||
                shape.bottom > view.getScreenHeight() ||
                shape.right > view.getScreenWidth()) {
            onScreen = false; // Marca para ser eliminada
        }
    }

    // Método para dibujar la bala del cañón en el lienzo dado
    @Override
    public void draw(Canvas canvas) {
        // Dibuja un círculo en el lienzo utilizando las coordenadas y el radio de la bala del cañón
        canvas.drawCircle(shape.left + getRadius(),
                shape.top + getRadius(), getRadius(), paint);
    }


}