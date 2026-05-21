package GameConsole;

public enum Feature {

    ALLIGATOR (-10),
    PINEAPPLE (20),
    ALOE (5),
    SNAKE (-100);

    private int health;

    Feature(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}
