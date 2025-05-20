package Models.enums;

public enum CharacterType {

    SHANA("Shana",4,4),
    DIAMOND("Diamond",7,1),
    SCARLET("Scarlet",3,5),
    LILITH("Lilith",5,3),
    DASHER("Dasher",2,10),

    ;

    private final String name;
    private final int health;
    private final int speed;

    CharacterType(String name, int health, int speed) {
        this.name = name;
        this.health = health;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }
}
