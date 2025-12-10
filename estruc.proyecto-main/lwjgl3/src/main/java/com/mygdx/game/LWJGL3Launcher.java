package com.mygdx.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class LWJGL3Launcher {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("CASTLEVANIA: Symphony of the Night - MODO JUEGO");
        System.out.println("=================================================");
        
        // Crear ventana de juego REAL
        crearVentanaJuego();
    }
    
    static void crearVentanaJuego() {
        JFrame ventana = new JFrame("CASTLEVANIA - JUGANDO");
        ventana.setSize(1024, 768);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        
        // Panel del juego
        JPanel juegoPanel = new JuegoPanel();
        ventana.add(juegoPanel);
        
        // Centrar ventana
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        
        System.out.println("✅ Ventana del juego creada");
        System.out.println("🎮 Controles: Flechas para mover, ESPACIO para atacar");
    }
    
    static class JuegoPanel extends JPanel implements KeyListener {
        private BufferedImage alucard;
        private BufferedImage dracula;
        private BufferedImage background;
        private BufferedImage esqueleto;
        private int alucardX = 100;
        private int alucardY = 300;
        private int draculaX = 700;
        private int draculaY = 300;
        private int esqueletoX = 500;
        private int esqueletoY = 200;
        private int vidaAlucard = 100;
        private int vidaDracula = 200;
        private boolean[] teclas = new boolean[256];
        private Timer gameTimer;
        
        public JuegoPanel() {
            setFocusable(true);
            addKeyListener(this);
            setBackground(Color.BLACK);
            
            // Cargar imágenes
            try {
                alucard = ImageIO.read(new File("core/assets/alucarddepie.png"));
                dracula = ImageIO.read(new File("core/assets/draculadepie.png"));
                background = ImageIO.read(new File("core/assets/Level1_bg.png"));
                esqueleto = ImageIO.read(new File("core/assets/eskeletocaminando.png"));
                System.out.println("✅ Imágenes cargadas exitosamente");
            } catch (Exception e) {
                System.out.println("⚠️  Error cargando imágenes: " + e.getMessage());
                // Crear imágenes placeholder
                alucard = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = alucard.createGraphics();
                g.setColor(Color.RED);
                g.fillRect(0, 0, 64, 64);
                g.dispose();
            }
            
            // Timer para el juego (60 FPS)
            gameTimer = new Timer(16, e -> {
                actualizarJuego();
                repaint();
            });
            gameTimer.start();
        }
        
        void actualizarJuego() {
            // Movimiento de Alucard
            if (teclas[KeyEvent.VK_LEFT]) alucardX -= 5;
            if (teclas[KeyEvent.VK_RIGHT]) alucardX += 5;
            if (teclas[KeyEvent.VK_UP]) alucardY -= 5;
            if (teclas[KeyEvent.VK_DOWN]) alucardY += 5;
            
            // Ataque con ESPACIO
            if (teclas[KeyEvent.VK_SPACE]) {
                // Verificar si golpea a Dracula
                if (Math.abs(alucardX - draculaX) < 50 && Math.abs(alucardY - draculaY) < 50) {
                    vidaDracula -= 5;
                    System.out.println("⚔️  Alucard ataca a Dracula! Vida: " + vidaDracula);
                }
                // Verificar si golpea al esqueleto
                if (Math.abs(alucardX - esqueletoX) < 50 && Math.abs(alucardY - esqueletoY) < 50) {
                    System.out.println("💀 Esqueleto eliminado!");
                    esqueletoX = -100; // Mover fuera de pantalla
                }
            }
            
            // Movimiento del esqueleto (si sigue vivo)
            if (esqueletoX > -50) {
                esqueletoX -= 2;
                if (esqueletoX < 0) esqueletoX = 1000;
            }
            
            // Movimiento de Dracula (IA simple)
            if (draculaX > alucardX) draculaX -= 1;
            if (draculaX < alucardX) draculaX += 1;
            if (draculaY > alucardY) draculaY -= 1;
            if (draculaY < alucardY) draculaY += 1;
            
            // Verificar si Alucard está muy cerca de Dracula
            if (Math.abs(alucardX - draculaX) < 30 && Math.abs(alucardY - draculaY) < 30) {
                vidaAlucard -= 1;
                System.out.println("🩸 Dracula ataca! Vida Alucard: " + vidaAlucard);
            }
            
            // Límites de pantalla
            alucardX = Math.max(0, Math.min(960, alucardX));
            alucardY = Math.max(0, Math.min(704, alucardY));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Dibujar fondo
            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(new Color(20, 10, 30));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
            
            // Dibujar personajes
            if (alucard != null) {
                g.drawImage(alucard, alucardX, alucardY, 64, 64, this);
            }
            
            if (dracula != null && vidaDracula > 0) {
                g.drawImage(dracula, draculaX, draculaY, 64, 64, this);
            }
            
            if (esqueleto != null && esqueletoX > -50) {
                g.drawImage(esqueleto, esqueletoX, esqueletoY, 64, 64, this);
            }
            
            // Dibujar barras de vida
            g.setColor(Color.RED);
            g.fillRect(20, 20, 200, 20);
            g.setColor(Color.GREEN);
            g.fillRect(20, 20, vidaAlucard * 2, 20);
            g.setColor(Color.WHITE);
            g.drawString("ALUCARD: " + vidaAlucard + "/100", 25, 35);
            
            if (vidaDracula > 0) {
                g.setColor(Color.RED);
                g.fillRect(20, 50, 200, 20);
                g.setColor(Color.BLUE);
                g.fillRect(20, 50, vidaDracula, 20);
                g.setColor(Color.WHITE);
                g.drawString("DRACULA: " + vidaDracula + "/200", 25, 65);
            } else {
                g.setColor(Color.YELLOW);
                g.drawString("🎉 DRACULA DERROTADO!", 300, 40);
            }
            
            // Instrucciones
            g.setColor(Color.WHITE);
            g.drawString("CONTROLES: FLECHAS = MOVER, ESPACIO = ATACAR", 300, getHeight() - 30);
            g.drawString("OBJETIVO: DERROTA A DRACULA Y LOS ESQUELETOS", 300, getHeight() - 10);
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            teclas[e.getKeyCode()] = true;
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            teclas[e.getKeyCode()] = false;
        }
        
        @Override
        public void keyTyped(KeyEvent e) {}
    }
}
