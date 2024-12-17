package domain;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PoobVsZombies {
    private int soles;
    private int cerebros;
    private ArrayList<Zombie> zombies;
    private ArrayList<Planta> plants;
    private ArrayList<Timer> activeTimers;

    public PoobVsZombies() {
        this.soles = 50; // Inicializar con un valor base
        this.cerebros = 10;
        zombies = new ArrayList<>();
        plants = new ArrayList<>();
        activeTimers = new ArrayList<>();// Inicializar con un valor base
    }



    // Métodos para gestionar soles
    public int getSoles() {
        return soles;
    }

    public void incrementarSoles(int cantidad) {
        soles += cantidad;
    }

    public void incrementarCerebros(int cantidad) {
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
                incrementarCerebros(50); // Generar cerebros cada intervalo
            }
        }, 0, intervaloMilisegundos);
    }

    // Método para verificar si se pueden posicionar plantas según el precio
    public boolean puedeColocarPlanta(int precioPlanta) {
        return soles >= precioPlanta;
    }

    // Método para reducir los soles al colocar una planta
    public void colocarPlanta(int precioPlanta) {
        if (puedeColocarPlanta(precioPlanta)) {
            soles -= precioPlanta;
        } else {
            throw new IllegalStateException("No tienes suficientes soles para colocar esta planta.");
        }
    }

    // Método para verificar si se pueden posicionar zombis según el precio
    public boolean puedeColocarZombie(int precioZombie) {
        return cerebros >= precioZombie;
    }

    // Método para reducir los cerebros al colocar un zombie
    public void colocarZombie(int precioZombie) {
        if (puedeColocarZombie(precioZombie)) {
            cerebros -= precioZombie;
        } else {
            throw new IllegalStateException("No tienes suficientes cerebros para colocar este zombie.");
        }
    }

    public ArrayList<Planta> getPlantas(){
        return plants;
    }

   public ArrayList<Zombie> getZombies(){
        return zombies;
   }
}
