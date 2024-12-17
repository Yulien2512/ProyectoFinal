package domain;

import javax.swing.*;
import java.awt.*;

public abstract class Planta {
    protected String nombre; // Eliminado el static
    protected int costo;
    protected int puntosDeVida;
    protected int x;
    protected int y;
    private JLabel label;
    private JPanel cell;
    private static final int GRID_OFFSET_X = 180;
    private static final int GRID_OFFSET_Y = 250;
    private int vida;

    public Planta(int x, int y, String nombre, int costo, int puntosDeVida) {
        this.x = x;
        this.y = y;
        this.nombre = nombre;
        this.costo = costo;
        this.puntosDeVida = puntosDeVida;
    }

    public String getNombre() {
        return nombre; // Devuelve el nombre único de esta instancia
    }

    public int getCosto() {
        return costo;
    }

    public int getX() {
        return x;
    }

    /**
     * Gets the plant's y-coordinate on the grid.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Gets the JLabel representing the plant's graphical appearance.
     *
     * @return The JLabel of the plant.
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Sets the JLabel representing the plant's graphical appearance.
     *
     * @param label The new JLabel.
     */
    public void setLabel(JLabel label) {
        this.label = label;
    }

    public static Planta getPlanta(String temp) {
        if (temp.equalsIgnoreCase("peashooter")) {
            return new peashooter(0,0);
        } else if (temp.equalsIgnoreCase("sunflower")) {
            return new sunflower(0,0);
        } else if (temp.equalsIgnoreCase("wallnut")) {
            return new wallnut(0,0);
        } else if (temp.equalsIgnoreCase("eciplanta")) {
            return new eciplanta(0,0);
        } else if (temp.equalsIgnoreCase("potatoMine")) {
            return new potatoMine(0,0);
        } else {
            return null;
        }
    }

    public void recibirDaño(int daño) {
        puntosDeVida -= daño;
        if (puntosDeVida < 0) {
            puntosDeVida = 0;
        }
    }

    public boolean estaViva() {
        return puntosDeVida > 0;
    }

    public int getRow() {
        return x; // Devuelve la fila de esta instancia
    }

    public int getCol() {
        return y;
    }

    public Rectangle getBounds() {
        // Tamaño genérico de las plantas, puede ajustarse para cada tipo
        return new Rectangle(x, y, 50, 50);
    }

    public int getVida() {
        return puntosDeVida;
    }
}
