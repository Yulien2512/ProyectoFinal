package domain;

public abstract class Zombie {
    protected String nombre;
    protected int puntosDeVida;
    protected int daño;
    protected double tiempoMordida;

    public Zombie(String nombre, int puntosDeVida, int daño, double tiempoMordida) {
        this.nombre = nombre;
        this.puntosDeVida = puntosDeVida;
        this.daño = daño;
        this.tiempoMordida = tiempoMordida;
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
}
