package GameConsole;

public final class Soldier extends Combatant{
    public Soldier(String name, Weapon weapon) {
        super(name);
        setWeapon(weapon);
    }
}
