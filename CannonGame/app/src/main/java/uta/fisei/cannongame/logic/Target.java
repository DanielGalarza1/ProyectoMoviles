package uta.fisei.cannongame.logic;

import uta.fisei.cannongame.CannonView;

public class Target extends GameElement{
    // La recompensa por acertar en este Target
    private int hitReward;

    // Constructor de Target
    public Target(CannonView view, int color, int hitReward, int x, int y, int width, int length, float velocityY) {
        // Llama al constructor de la clase base (GameElement)
        super(view, color, CannonView.TARGET_SOUND_ID, x, y, width, length, velocityY);

        // Inicializa la recompensa por acertar para este Target
        this.hitReward = hitReward;
    }

    // Método que devuelve la recompensa por acertar para este Target
    public int getHitReward() {
        return hitReward;
    }
}
