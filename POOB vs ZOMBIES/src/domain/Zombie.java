package domain;

import presentation.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Zombie{
    protected int x;
    protected int y;
    protected String nombre;
    protected int puntosDeVida;
    protected int daño;
    protected int costo;
    private boolean estaComiendo;
    private JLabel zombieLabel;
    private JPanel gamePanel;
    private Timer moveTimer;
    private Timer attackTimer;
    private GameBoard gameboard;

    public Zombie(int x, int y, String nombre, int puntosDeVida, int daño, int costo) {
        this.x = x;
        this.y = y;
        this.nombre = nombre;
        this.puntosDeVida = 1;
        this.daño = 1;
        this.costo = costo;
    }


    public String getNombre() {
        return nombre;
    }

    public int getPuntosDeVida() {
        return puntosDeVida;
    }

    public int getDaño() {
        return daño;
    }

    public int getCosto() {
        return costo;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void recibirDaño(int daño) {
        puntosDeVida -= daño;
        if (puntosDeVida < 0) {
            puntosDeVida = 0;
        }
    }

    public boolean estaVivo() {
        return puntosDeVida > 0;
    }

    public int morder() {
        return daño;
    }

    public boolean estaComiendo() {
        return estaComiendo;
    }

    public void cambiarEstadoComer(boolean estado) {
        this.estaComiendo = estado;
    }

    public Rectangle getBounds() {
        // Ajustar el tamaño según el zombie
        return new Rectangle(x, y, 50, 50);
    }

    public void mover() {
        // Movimiento predeterminado de zombies hacia la izquierda
        this.x -= 2; // Cambiar la velocidad según el tipo
    }

    public static Zombie getZombie(String temp) {
        if (temp.equalsIgnoreCase("basicZombie")) {
            return new ZombieBasico(0,0);
        } else if (temp.equalsIgnoreCase("bucketheadZombie")) {
            return new ZombieBuckethead(0,0);
        } else if (temp.equalsIgnoreCase("coneheadZombie")) {
            return new ZombieConehead(0,0);
        } else if (temp.equalsIgnoreCase("brainstain")) {
            return new brainstain(0,0);
        } else {
            return null;
        }
    }

    public void startMoving() {

        moveTimer = new Timer(100, new ActionListener() {
            int x = getX();

            @Override
            public void actionPerformed(ActionEvent e) {
                x -= 5;
                if (x + zombieLabel.getWidth() < 0) {
                    ((Timer) e.getSource()).stop();
                    gamePanel.remove(zombieLabel);
                    gamePanel.revalidate();
                    gamePanel.repaint();
                } else {
                    zombieLabel.setLocation(x, zombieLabel.getY());
                    checkCollisionWithPlants();
                }
            }
        });
        moveTimer.start();
    }

    private void checkCollisionWithPlants() {
        java.util.List<Planta> plants = gameboard.getPlantas();
        int zombieX = zombieLabel.getX();
        int zombieY = zombieLabel.getY();
        int collisionThreshold = 50;

        for (Planta plant : plants) {
            int plantX = plant.getX();
            int plantY = plant.getY();
            if (Math.abs(zombieY - plantY) < 20 &&
                    Math.abs(zombieX - plantX) < collisionThreshold) {
                moveTimer.stop();
                startAttacking(plant);
                break;
            }
        }
    }

    private void startAttacking(Planta plant) {
        attackTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plant.setVida(plant.getVida() - 100);
                if (plant.getVida() <= 0) {
                    ((Timer) e.getSource()).stop();
                    JLabel plantLabel = plant.getLabel();
                    if (plantLabel != null && plantLabel.getParent() != null) {
                        Container parent = plantLabel.getParent();
                        parent.remove(plantLabel);
                        parent.revalidate();
                        parent.repaint();
                    }
                    moveTimer.start();
                }
            }
        });
        attackTimer.start();
    }

}
