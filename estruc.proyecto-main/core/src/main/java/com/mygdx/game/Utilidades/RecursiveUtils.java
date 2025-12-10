package com.mygdx.game.utils;

public class RecursiveUtils {

    // Recursividad para Fibonacci (usado en esqueletos del nivel 2)
    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // Recursividad para calcular factorial
    public static int factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    // Recursividad para buscar en profundidad (pathfinding simple)
    public static boolean buscarCamino(int[][] laberinto, int x, int y, int objetivoX, int objetivoY) {
        if (x < 0 || y < 0 || x >= laberinto.length || y >= laberinto[0].length) {
            return false;
        }
        if (laberinto[x][y] == 1) { // Pared
            return false;
        }
        if (x == objetivoX && y == objetivoY) {
            return true;
        }

        laberinto[x][y] = 1; // Marcar como visitado

        // Buscar recursivamente en las 4 direcciones
        if (buscarCamino(laberinto, x + 1, y, objetivoX, objetivoY) ||
            buscarCamino(laberinto, x - 1, y, objetivoX, objetivoY) ||
            buscarCamino(laberinto, x, y + 1, objetivoX, objetivoY) ||
            buscarCamino(laberinto, x, y - 1, objetivoX, objetivoY)) {
            return true;
        }

        laberinto[x][y] = 0; // Desmarcar
        return false;
    }
}
