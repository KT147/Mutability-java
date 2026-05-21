package GameConsole;

import java.util.*;

public sealed abstract class Combatant implements Player permits Islander, Pirate, Soldier {

    private final Map<String, Integer> gameData;
    private final String name;
    private Weapon weapon;

    public Combatant(String name) {
        this.name = name;
    }

    public Combatant(String name, Map<String, Integer> gameData) {
        this.name = name;
        if (gameData != null) {
            this.gameData.putAll(gameData);
        }
    }

    {
        gameData = new HashMap<>(Map.of(
                "health", 100,
                "score", 0
        ));
    }

    public Weapon getWeapon() {
        return weapon;
    }

    protected void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    protected void setValue(String name, int value) {
        gameData.put(name, value);
    }

    int value(String name) {
        return gameData.get(name);
    }

    protected void adjustValue(String name, int adj) {
        gameData.compute(name, (k, v) -> v += adj);
    }

    protected void adjustHealth(int adj) {

        int health = value("health");
        health += adj;
        health = (health < 0) ? 0 : (Math.min(health, 100));
        setValue("health", health);
    }

    boolean useWeapon(Combatant opponent) {

        System.out.println(name + " used the " + weapon);

        if (new Random().nextBoolean()) {
            System.out.println("got hit " + opponent.getName() + "!");

            opponent.adjustHealth(-weapon.getHitPoints());
            adjustValue("score", 50);
        } else {
            System.out.println(" missed");
        }


        return (opponent.value("health") <= 0);

    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String information() {
        return name + " " + gameData;
    }
}
