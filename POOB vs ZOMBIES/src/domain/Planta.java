package domain;

public abstract class Planta {
    protected String nombre;
    protected int costo;
    protected int puntosDeVida;

    public Planta(String nombre, int costo, int puntosDeVida) {
        this.nombre = nombre;
        this.costo = costo;
        this.puntosDeVida = puntosDeVida;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCosto() {
        return costo;
    }

    public int getPuntosDeVida() {
        return puntosDeVida;
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
}
