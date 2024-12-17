package domain;

import java.util.Timer;
import java.util.TimerTask;

public class sunflower extends Planta {
    private int solesProducidos;
    static final int SOLES_POR_PRODUCCION = 25;
    private static final int TIEMPO_PRODUCCION = 20000; // 20 segundos en milisegundos
    private Timer timerProduccion;
    private PoobVsZombies juego;
    private int x;
    private int y;// Referencia al juego principal

    public sunflower(int x, int y) {
        super(x, y, "sunflower", 50, 300); // Nombre, precio, vida
        this.solesProducidos = 0;
        this.juego = new PoobVsZombies();
        this.timerProduccion = null;
        this.x = x;
        this.y = y;
    }

    // Inicia la producción de soles
    public void iniciarProduccion() {
        if (juego == null) {
            throw new IllegalStateException("El juego no está asociado a esta planta.");
        }
        if (timerProduccion == null) {
            timerProduccion = new Timer();
            timerProduccion.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    producirSoles();
                }
            }, 0, TIEMPO_PRODUCCION);
        }
    }

    // Método para producir soles y añadirlos al juego principal
    public void producirSoles() {
        if (juego != null) {
            juego.incrementarSoles(SOLES_POR_PRODUCCION);
            solesProducidos += SOLES_POR_PRODUCCION;
            System.out.println("Girasol producido: " + SOLES_POR_PRODUCCION);
        }
    }

    // Detiene la producción de soles
    public void detenerProduccion() {
        if (timerProduccion != null) {
            timerProduccion.cancel();
            timerProduccion = null;
        }
    }

    public int getY() {
        return y;
    }
    public int getX(){
        return x;
    }

}
