package domain;

import java.util.Timer;
import java.util.TimerTask;

public class eciplanta extends Planta{

    private int solesProducidos;
    static final int SOLES_POR_PRODUCCION = 25;
    private static final int TIEMPO_PRODUCCION = 20000; // 20 segundos en milisegundos
    private Timer timerProduccion;
    private PoobVsZombies juego;

    public eciplanta(int x, int y) {
        super(x,y,"eciPlanta", 75, 2000);
        this.solesProducidos = 0;
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
            System.out.println("Eciplanta producido: " + SOLES_POR_PRODUCCION);
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
