package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.model.*;
import com.mygdx.game.player.Player;

public class GameController {

    // ============================================================
    // LISTA ENLAZADA DE ENEMIGOS
    // ============================================================
    private static class Node {
        Enemy e;
        Node next;
        Node(Enemy e) { this.e = e; }
    }

    private Node head;
    private int count;

    // ============================================================
    // REFERENCIAS IMPORTANTES
    // ============================================================
    private final Player player;
    private LevelManager lm;

    private BossDracula bossRef;     // fase 1
    private BossDracula2 bossRef2;   // fase 2 (no existe aún, pero dejamos el soporte)

    // ============================================================
    // CONSTRUCTOR
    // ============================================================
    public GameController(Player p) {
        this.player = p;
        this.lm = new LevelManager();
    }

    public LevelManager getLevelManager() { return lm; }

    // ============================================================
    // CAMBIO DE NIVEL
    // ============================================================
    public void onLevelChanged() {
        clear();        // limpiar enemigos del nivel anterior
        bossRef = null;
        bossRef2 = null;

        switch (lm.getLevel()) {

            case 1:
                // Tutorial → no hay enemigos
                break;

            case 2:
                // Esqueletos dinámicos (spawnean en update)
                break;

            case 3:
                // Minotauro miniboss
                add(new Minotauro(700, 80));
                break;

            case 4:
                // Drácula fase 1
                bossRef = new BossDracula(600, 80);
                add(bossRef);
                break;

            case 5:
                // Drácula fase 2 (cuando termine F1)
                if (bossRef2 == null)
                    bossRef2 = new BossDracula2(600, 80);
                add(bossRef2);
                break;
        }
    }

    // ============================================================
    // LISTA ENLAZADA: AGREGAR Y LIMPIAR
    // ============================================================
    private void add(Enemy e) {
        Node n = new Node(e);
        n.next = head;
        head = n;
        count++;
    }

    private void clear() {
        Node c = head;
        while (c != null) {
            c.e.dispose();
            c = c.next;
        }
        head = null;
        count = 0;
    }

    // ============================================================
    // SALUD DEL JEFE PARA GameScreen
    // ============================================================
    public float getBossHealthPercent() {
        if (bossRef != null)
            return (float) bossRef.getVida() / bossRef.getVidaMaxima();
        if (bossRef2 != null)
            return (float) bossRef2.getVida() / bossRef2.getVidaMaxima();
        return 0;
    }

    // ============================================================
    // ACTUALIZAR
    // ============================================================
    public void update(float delta) {

        int lvl = lm.getLevel();

        // -------------------------------
        // NIVEL 2 → SPAWNEAR ESQUELETOS
        // -------------------------------
        if (lvl == 2) {
            if (Math.random() < 0.02) {   // 2% chance por frame
                add(new Skeleton(1100, 80));
            }
        }

        Node c = head;
        Node prev = null;

        while (c != null) {
            Enemy e = c.e;

            // movimiento y AI
            e.update(delta, player.getX(), player.getY());

            // si muere → eliminar
            if (!e.estaVivo()) {

                // para lvl 4 → pasar a fase 2
                if (e == bossRef && lvl == 4) {
                    lm.gotoPhase2();  // método que agregaremos luego
                    onLevelChanged(); // cargar fase 2
                }

                lm.enemyKilled();
                count--;

                if (prev == null) head = c.next;
                else prev.next = c.next;

                c = (prev == null) ? head : prev.next;
                continue;
            }

            // ----------- COLISION DAÑO AL JUGADOR -----------
            Rectangle rP = player.getBounds();
            Rectangle rE = e.getBounds();

            if (rP.overlaps(rE)) {
                player.receiveDamage(e.getDano());
            }

            // ----------- ATAQUES DEL JUGADOR -----------
            Rectangle atk = player.getAttackBounds();
            if (atk.width > 0 && atk.overlaps(rE)) {
                e.recibirDano(player.getWeaponDamage());
            }

            prev = c;
            c = c.next;
        }
    }

    // ============================================================
    // RENDER
    // ============================================================
    public void render(SpriteBatch batch) {
        Node c = head;
        while (c != null) {
            c.e.render(batch);
            c = c.next;
        }
    }

    // ============================================================
    // CONTROL DE JEFE
    // ============================================================
    public boolean isBossDefeated() {
        if (lm.getLevel() == 4 && bossRef != null)
            return !bossRef.estaVivo();

        if (lm.getLevel() == 5 && bossRef2 != null)
            return !bossRef2.estaVivo();

        return false;
    }

    // ============================================================
    // LIMPIAR TODO
    // ============================================================
    public void dispose() { clear(); }
}
