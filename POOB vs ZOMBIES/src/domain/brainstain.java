package domain;

import java.util.Timer;
import java.util.TimerTask;

public class brainstain extends Zombie{
    private int cerebrosProducidos;
    static final int CEREBROS_POR_PRODUCCION = 25;
    private static final int TIEMPO_PRODUCCION = 20000; // 20 segundos en milisegundos
    private Timer timerProduccion;
    private PoobVsZombies juego;


    public brainstain(int x, int y) {
        super(x, y, "brainstain", 1000, 100, 40);
        this.cerebrosProducidos = 0;
        this.juego = new PoobVsZombies();
        this.timerProduccion = null;
    }

    public void iniciarProduccion() {
        if (juego == null) {
            throw new IllegalStateException("El juego no está asociado a esta planta.");
        }
        if (timerProduccion == null) {
            timerProduccion = new Timer();
            timerProduccion.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    producirCerebros();
                }
            }, 0, TIEMPO_PRODUCCION);
        }
    }

    // Método para producir soles y añadirlos al juego principal
    public void producirCerebros() {
        if (juego != null) {
            juego.incrementarCerebros(CEREBROS_POR_PRODUCCION);
            cerebrosProducidos += CEREBROS_POR_PRODUCCION;
            System.out.println("Brainstain producido: " + CEREBROS_POR_PRODUCCION);
        }
    }

    // Detiene la producción de soles
    public void detenerProduccion() {
        if (timerProduccion != null) {
            timerProduccion.cancel();
            timerProduccion = null;
        }
    }
}
