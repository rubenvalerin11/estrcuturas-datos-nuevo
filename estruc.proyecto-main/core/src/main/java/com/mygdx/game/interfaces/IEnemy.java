package com.mygdx.game.interfaces;

public interface IEnemy {
    void atacar();
    void recibirDaño(int cantidad);
    boolean estaVivo();
    int getVida();
    int getPuntos();
}
