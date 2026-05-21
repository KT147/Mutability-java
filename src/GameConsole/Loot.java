package GameConsole;

public enum Loot {
    GOLD_COINS(10),
    NECKLACE(100),
    SILVER_RING(1000);

    private final int score;

    Loot(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
