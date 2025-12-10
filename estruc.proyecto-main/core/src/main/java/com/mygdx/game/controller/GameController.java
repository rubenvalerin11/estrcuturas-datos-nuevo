package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.model.*;
import com.mygdx.game.player.Player;

public class GameController {

    private static class Node {
        Enemy e;
        Node next;
        Node(Enemy e){ this.e=e; }
    }

    private Node head;
    private int count;

    private final Player player;
    private LevelManager lm;

    private BossDracula bossRef;

    public GameController(Player p) {
        this.player = p;
        this.lm = new LevelManager();
    }

    public LevelManager getLevelManager() { return lm; }

    public void onLevelChanged() {
        clear();
        bossRef = null;

        switch (lm.getLevel()) {
            case 1: break;
            case 2: break; // esqueletos dinámicos
            case 3:
                add(new Minotauro(700, 80));
                break;
            case 4:
                bossRef = new BossDracula(600, 80);
                add(bossRef);
                break;
        }
    }

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

    public float getBossHealthPercent() {
        if (bossRef == null) return 0;
        return (float) bossRef.getVida() / bossRef.getVidaMaxima();
    }

    public void update(float delta) {

        int lvl = lm.getLevel();

        if (lvl == 2) {
            if (Math.random() < 0.02) add(new Skeleton(1000,80));
        }

        Node c = head;
        Node prev = null;

        while (c != null) {
            Enemy e = c.e;
            e.update(delta, player.getX(), player.getY());

            if (!e.estaVivo()) {
                lm.enemyKilled();
                count--;
                if (prev == null) head = c.next;
                else prev.next = c.next;

                c = (prev == null) ? head : prev.next;
                continue;
            }

            Rectangle rP = player.getBounds();
            Rectangle rE = e.getBounds();

            if (rP.overlaps(rE)) player.receiveDamage(e.getDano());

            Rectangle atk = player.getAttackBounds();
            if (atk.width > 0 && atk.overlaps(rE)) e.recibirDano(20);

            prev = c;
            c = c.next;
        }
    }

    public void render(SpriteBatch batch) {
        Node c = head;
        while (c != null) {
            c.e.render(batch);
            c = c.next;
        }
    }

    public void dispose() { clear(); }
}
