package com.mygdx.game.model;

public class NodoHabilidad {

    private String nombre;
    private boolean desbloqueada;
    private NodoHabilidad izquierda;
    private NodoHabilidad derecha;

    public NodoHabilidad(String nombre) {
        this.nombre = nombre;
    }

    public void setIzquierda(NodoHabilidad izquierda) { this.izquierda = izquierda; }
    public void setDerecha(NodoHabilidad derecha) { this.derecha = derecha; }

    public static void desbloquearHabilidades(NodoHabilidad raiz, int nivel) {
        if (raiz == null) return;
        if (nivel <= 0) return;

        raiz.desbloqueada = true;
        desbloquearHabilidades(raiz.izquierda, nivel - 1);
        desbloquearHabilidades(raiz.derecha, nivel - 1);
    }
}
