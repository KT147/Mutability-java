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

final class Pirate extends Combatant {

    private List<Town> townsvisited = new LinkedList<>();

    private List<Loot> loot;
    private List<Combatant> opponents;
    private List<Feature> features;


    public Pirate(String name) {
        super(name, Map.of("level", 0, "townIndex", 0));
        visitTown();
    }


    boolean useWeapon() {

        int count = opponents.size();
        if (count > 0) {
            int opponentIndex = count - 1;
            if (count > 1) {
                opponentIndex = new Random().nextInt(count);
            }
            Combatant combatant = opponents.get(opponentIndex);
            if (super.useWeapon(combatant)) {
                opponents.remove(opponentIndex);
            } else {
                return combatant.useWeapon(this);
            }
        }
        return false;
    }

    boolean visitTown() {

        List<Town> levelTowns = PirateGame.getTowns((value("level")));
        if (levelTowns == null) return true;
        Town town = levelTowns.get(value("townIndex"));
        if (town != null) {
            townsvisited.add(town);
            loot = town.loot();
            opponents = town.opponents();
            features = town.features();
            return false;
        }
        return true;
    }

    boolean hasExperiences() {
        return (features != null && features.size() > 0);
    }

    boolean hasOpponents() {
        return (opponents != null && opponents.size() > 0);
    }


    public String information() {
        var current = ((LinkedList<Town>) townsvisited).getLast();
        String[] simpleNames = new String[townsvisited.size()];
        Arrays.setAll(simpleNames, i -> townsvisited.get(i).name());
        return "--->" + current + "Pirate " + super.information() + " " + "townsVisited= " + Arrays.toString(simpleNames);
    }

    boolean findLoot() {
        if (loot.size() > 0) {
            Loot item = loot.remove(0);
            System.out.println("Found " + item);
            adjustValue("score", item.getScore());
            System.out.println(getName() + " score is " + value("score"));
        }

        if (loot.size() == 0) {
            return visitTown();
        }
        return false;

    }

    boolean experienceFeature() {

        if (features.size() > 0) {
            Feature item = features.remove(0);
            System.out.println("Ran into " + item);
            adjustHealth(item.getHealth());
            System.out.println(getName() + " health is now " + value("health"));
        }

        return (value("health") <= 0);
    }

    private boolean visitNextTown() {

        int townIndex = value("townIndex");
        var towns = PirateGame.getTowns(value("level"));
        if (towns == null) return true;
        if (townIndex >= (towns.size() - 1)) {
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

    private static final List<List<Town>> levelMap;

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
    Pirate createNewPlayer(String name) {
        return new Pirate(name);
    }


    @Override
    public Map<Character, GameAction> getGameActions(int playerIndex) {

        Pirate pirate = getPlayer(playerIndex);
        System.out.println(pirate);
        List<Weapon> weapons = Weapon.getWeaponsByLevel(pirate.value("level"));
        Map<Character, GameAction> map = new LinkedHashMap<>();
        if (pirate.hasExperiences()) {
            for (Weapon weapon : weapons) {
                char init = weapon.name().charAt(0);
                map.put(init, new GameAction(init, "Use" + weapon, this::useWeapon));
            }
        }
        map.put('F', new GameAction('F', "Find loot", this::findLoot));
        if (pirate.hasExperiences()) {
            map.put('X', new GameAction('X', "Experience Town Feature", this::experienceFeature));
        }

        map.putAll(getStandardActions());
        return map;
    }

    private static void loadData() {

        levelMap.add(new ArrayList<>(List.of(
                new Town(0, "111ksfkfs", "fskfjss"),
                new Town(0, "222ksfkfs", "fskfjss"),
                new Town(0, "333ksfkfs", "fskfjss")
        )));

        levelMap.add(new ArrayList<>(List.of(
                new Town(1, "444ksfkfs", "fskfjss"),
                new Town(1, "55544ksfkfs", "fskfjss"),
                new Town(1, "666ksfkfs", "fskfjss")
        )));
    }

    public static List<Town> getTowns(int level) {

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

    @Override
    public boolean printPlayer(int playerIndex) {
        System.out.println(getPlayer(playerIndex).information());
        return false;
    }

    private boolean findLoot(int playerIndex) {
        return getPlayer(playerIndex).findLoot();
    }

    private boolean experienceFeature(int playerIndex) {
        return getPlayer(playerIndex).experienceFeature();
    }
}
