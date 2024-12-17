package domain;
import java.util.ArrayList;

public class peashooter extends Planta {
    private static final int DAÑO_GUISANTE = 20;
    private static final double TIEMPO_DISPARO = 1.5;
    private final ArrayList<Projectile> projectiles;// En segundos

    public peashooter(int x, int y) {
        super(x,y,"peashooter", 100, 300);
        projectiles = new ArrayList<>();
    }



    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void updateProjectiles() {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.move();
                if (p.getY() >= 9) { // Límite de la matriz
                    p.deactivate();
                }
            }
        }
    }
}
