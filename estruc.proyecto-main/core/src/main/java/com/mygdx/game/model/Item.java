package com.mygdx.game.model;

public abstract class Item {

    private int id;
    private String nombre;
    private int poder;      // daño, curación, etc.
    private int valor;      // precio en tienda

    public Item(int id, String nombre, int poder, int valor) {
        this.id = id;
        this.nombre = nombre;
        this.poder = poder;
        this.valor = valor;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getPoder() { return poder; }
    public int getValor() { return valor; }

    public abstract void usar(); // polimórfico según tipo de item
}
