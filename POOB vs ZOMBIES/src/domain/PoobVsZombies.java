package domain;

import java.util.Timer;
import java.util.TimerTask;

public class PoobVsZombies {
    private int soles;
    private int cerebros;

    public PoobVsZombies() {
        this.soles = 50; // Inicializar con un valor base
        this.cerebros = 10; // Inicializar con un valor base
    }

    // Métodos para gestionar soles
    public int getSoles() {
        return soles;
    }

    public void incrementarSoles(int cantidad) {
        soles = cantidad + soles;
    }

    public void incrementarCerebros(int cantidad){
        cerebros += cantidad;
    }


    // Métodos para gestionar cerebros
    public int getCerebros() {
        return cerebros;
    }


    // Actualización periódica (puedes personalizar este método)
    public void iniciarGeneracionSoles(int intervaloMilisegundos) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                incrementarSoles(25); // Generar soles cada intervalo
            }
        }, 0, intervaloMilisegundos);
    }

    public void iniciarGeneracionCerebros(int intervaloMilisegundos) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                incrementarCerebros(50); // Generar soles cada intervalo
            }
        }, 0, intervaloMilisegundos);
    }
}
