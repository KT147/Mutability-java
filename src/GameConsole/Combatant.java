package GameConsole;

import java.util.*;

abstract class Combatant implements Player {

    private final Map<String, Integer> gameData;
    private final String name;
    private List<String> townsvisited = new LinkedList<>();
    private Weapon weapon;

    public Combatant(String name) {
        this.name = name;
    }

    {
        gameData = new HashMap<>(Map.of(
                "health", 100,
                "score", 0,
                "level", 0,
                "townIndex", 0
        ));
        visitTown();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    private void setValue(String name, int value) {
        gameData.put(name, value);
    }

    int value(String name) {
        return gameData.get(name);
    }

    private void adjustValue(String name, int adj) {
        gameData.compute(name, (k, v) -> v += adj);
    }

    private void adjustHealth(int adj) {

        int health = value("health");
        health += adj;
        health = (health < 0) ? 0 : (Math.min(health, 100));
        setValue("health", health);
    }

    boolean useWeapon() {

        System.out.println("Used the " + weapon);
        return visitNextTown();
    }

    boolean visitTown() {

        List<String> levelTowns = PirateGame.getTowns((value("level")));
        if (levelTowns == null) return true;
        String town = levelTowns.get(value("townIndex"));
        if (town != null) {
            townsvisited.add(town);
            return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        var current = ((LinkedList<String>) townsvisited).getLast();
        String[] simpleNames = new String[townsvisited.size()];
        Arrays.setAll(simpleNames, i -> townsvisited.get(i).split(",")[0]);
        return "--->" + current + "Pirate " + name + " " + gameData + "townsVisited= " + Arrays.toString(simpleNames);
    }

    private boolean visitNextTown() {

        int townIndex = value("townIndex");
        var towns = PirateGame.getTowns(value("level"));
        if (towns == null) return true;
        if(townIndex >= (towns.size() -1)) {
            System.out.println("Level up");
            adjustValue("score", 500);
            adjustValue("level", 1);
            setValue("townIndex", 0);
        } else {
            System.out.println("Sailing to the next town");
            adjustValue("townIndex", 1);
            adjustValue("score", 50);
        }

        return visitTown();
    }
}
