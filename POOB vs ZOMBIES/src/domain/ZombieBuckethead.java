package domain;

public class ZombieBuckethead extends Zombie {
    private static final int RESISTENCIA_CUBETA = 1000;

    public ZombieBuckethead(int x, int y) {
        super(x, y,"bucketheadZombie", 1100, 100, 30);
    }
}
