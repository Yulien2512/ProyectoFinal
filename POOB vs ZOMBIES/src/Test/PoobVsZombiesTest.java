package Test;

import static org.junit.jupiter.api.Assertions.*;

import domain.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PoobVsZombiesTest {
    private sunflower sunflower;
    private PoobVsZombies juego;

    @BeforeEach
    public void setUp() {
        juego = new PoobVsZombies();
        sunflower = new sunflower(0, 0);
    }


    @Test
    public void testDetenerProduccion() throws InterruptedException {
        sunflower.iniciarProduccion();
        Thread.sleep(10000);
        sunflower.detenerProduccion();
        int solesAntes = juego.getSoles();
        Thread.sleep(15000);
        assertEquals(solesAntes, juego.getSoles(), "La producción debe detenerse correctamente.");
    }
    @Test
    public void testObtenerPlantasYZombies() {
        PoobVsZombies juego = new PoobVsZombies();
        Planta planta = new sunflower(0, 0);
        Zombie zombie = new ZombieBasico(1, 0);

        juego.getPlantas().add(planta);
        juego.getZombies().add(zombie);

        assert juego.getPlantas().contains(planta) : "La planta no fue agregada correctamente.";
        assert juego.getZombies().contains(zombie) : "El zombie no fue agregado correctamente.";
    }
    @Test
    public void testGeneracionCerebros() throws InterruptedException {
        PoobVsZombies juego = new PoobVsZombies();
        juego.iniciarGeneracionCerebros(2000);
        Thread.sleep(6000);
        assert juego.getCerebros() == 160 : "La generación de cerebros no funciona correctamente.";
    }

    @Test
    public void testColocarZombieConCerebrosSuficientes() {
        PoobVsZombies juego = new PoobVsZombies();
        juego.colocarZombie(5);
        assert juego.getCerebros() == 5 : "No se descontaron correctamente los cerebros al colocar el zombie.";
    }

    @Test
    public void testColocarZombieSinCerebrosSuficientes() {
        PoobVsZombies juego = new PoobVsZombies();
        try {
            juego.colocarZombie(15);
            assert false : "Se debería haber lanzado una excepción por cerebros insuficientes.";
        } catch (IllegalStateException e) {
            assert e.getMessage().equals("No tienes suficientes cerebros para colocar este zombie.")
                    : "El mensaje de excepción no es el esperado.";
        }
    }
    @Test
    public void testColocarPlantaSinSolesSuficientes() {
        PoobVsZombies juego = new PoobVsZombies();
        try {
            juego.colocarPlanta(25);
            assert true;
        } catch (IllegalStateException e) {
            assert e.getMessage().equals("No tienes suficientes soles para colocar esta planta.")
                    : "El mensaje de excepción no es el esperado.";
        }
    }


}
