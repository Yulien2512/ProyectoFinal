package domain;

public class Projectile {
    private int x, y; // Posición del proyectil en la matriz
    private int speed; // Velocidad de desplazamiento
    private boolean active; // Indica si el proyectil está activo

    public Projectile(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 1; // Velocidad de desplazamiento
        this.active = true;
    }

    public void move() {
        if (active) {
            this.y += speed; // Avanza horizontalmente
        }
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

