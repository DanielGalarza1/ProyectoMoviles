package uta.fisei.cannongame.logic;

import uta.fisei.cannongame.CannonView;

public class Blocker extends GameElement{

    // La penalización por error para este Blocker
    private int missPenalty;

    // Constructor de Blocker
    public Blocker(CannonView view, int color, int missPenalty, int x, int y, int width, int length, float velocityY) {
        // Llama al constructor de la clase base (GameElement)
        super(view, color, CannonView.BLOCKER_SOUND_ID, x, y, width, length, velocityY);

        // Inicializa la penalización por error para este Blocker
        this.missPenalty = missPenalty;
    }

    // Método que devuelve la penalización por error para este Blocker
    public int getMissPenalty() {
        return missPenalty;
    }
}
