package domain;

public class Lanzaguisantes extends Planta {
    private static final int DAÑO_GUISANTE = 20;
    private static final double TIEMPO_DISPARO = 1.5; // En segundos

    public Lanzaguisantes() {
        super("Lanzaguisantes", 100, 300);
    }

    public int disparar() {
        return DAÑO_GUISANTE;
    }
}
