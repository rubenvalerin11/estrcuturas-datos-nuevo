
    package com.mygdx.game.algorithms;

import java.util.Random;

public class Pathfinding {

    private static final int[][] DIRECCIONES = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    public static void generarLaberinto(int x, int y, boolean[][] visitado) {
        visitado[x][y] = true;
        Random random = new Random();

        // Mezclamos las direcciones para tener laberintos distintos
        for (int i = 0; i < DIRECCIONES.length; i++) {
            int r = random.nextInt(DIRECCIONES.length);
            int[] temp = DIRECCIONES[i];
            DIRECCIONES[i] = DIRECCIONES[r];
            DIRECCIONES[r] = temp;
        }

        for (int[] dir : DIRECCIONES) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (nx >= 0 && ny >= 0 && nx < visitado.length && ny < visitado[0].length && !visitado[nx][ny]) {
                // Aquí iría la lógica para abrir paredes, etc.
                generarLaberinto(nx, ny, visitado); // recursión
            }
        }
    }
}
