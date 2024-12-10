package domain;

public class Girasol extends Planta {
    private int solesProducidos;
    private static final int SOLES_POR_PRODUCCION = 25;
    private static final int TIEMPO_PRODUCCION = 20; // En segundos

    public Girasol() {
        super("Girasol", 50, 300);
        this.solesProducidos = 0;
    }

    public int producirSoles() {
        solesProducidos += SOLES_POR_PRODUCCION;
        return SOLES_POR_PRODUCCION;
    }
}
