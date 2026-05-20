package GameConsole;

import java.util.*;

enum Weapon {

    KNIFE(0, 10),

    AXE(0, 30),

    MACHETE(1, 40),

    PISTOL(1, 50);

    private final int hitPoints;
    private final int minLevel;

    Weapon(int minLevel, int pointsDeducted) {
        this.minLevel = minLevel;
        this.hitPoints = pointsDeducted;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public static Weapon getWeaponByChar(char firstInitial) {
        for (Weapon w : values()) {
            if (w.name().charAt(0) == firstInitial) {
                return w;
            }

        }
        return values()[0];
    }

    public static List<Weapon> getWeaponsByLevel(int levelOfPlay) {
        List<Weapon> weapons = new ArrayList<>(EnumSet.allOf(Weapon.class));
        weapons.removeIf(w -> (w.minLevel > levelOfPlay));
        return weapons;
    }
}

class Pirate extends Combatant implements Player {

    private final Map<String, Integer> gameData;
    private final String name;
    private List<String> townsvisited = new LinkedList<>();
    private Weapon weapon;

    public Pirate(String name) {
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

public class PirateGame extends Game<Pirate> {

    private static final List<List<String>> levelMap;

    static {
        levelMap = new ArrayList<>();
        System.out.println("Loading data...");
        loadData();

        if (levelMap.size() == 0) {
            throw new RuntimeException("Could not load data");
        }
        System.out.println("Finished loading...");
    }

//    private List<Player> players = new ArrayList<>();


    public PirateGame(String gameName) {
        super(gameName);
    }

    @Override
    public Pirate createNewPlayer(String name) {
        return new Pirate(name);
    }

    @Override
    public Map<Character, GameAction> getGameActions(int playerIndex) {

        Pirate pirate = getPlayer(playerIndex);
        System.out.println(pirate);
        List<Weapon> weapons = Weapon.getWeaponsByLevel(pirate.value("level"));
        Map<Character, GameAction> map = new LinkedHashMap<>();
        for (Weapon weapon : weapons) {
            char init = weapon.name().charAt(0);
            map.put(init, new GameAction(init, "Use" + weapon, this::useWeapon));
        }
        map.putAll(getStandardActions());
        return map;
    }

    private static void loadData() {

        levelMap.add(new ArrayList<>(List.of(
                "Bridgetown",
                "Fitts Village",
                "Holetown"
        )));

        levelMap.add(new ArrayList<>(List.of(
                "Fort-De-France",
                "Sainte-Anne",
                "Le Vauclin"
        )));
    }

    public static List<String> getTowns(int level) {

        if (level <= (levelMap.size() - 1)) {
            return levelMap.get(level);
        }
        return null;
    }

    private boolean useWeapon(int playerIndex) {
        return getPlayer(playerIndex).useWeapon();
    }

    @Override
    public boolean executeGameAction(int player, GameAction action) {

        getPlayer(player).setWeapon(Weapon.getWeaponByChar(action.key()));
        return super.executeGameAction(player, action);
    }
}
