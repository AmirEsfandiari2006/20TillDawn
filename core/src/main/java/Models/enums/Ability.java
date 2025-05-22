package Models.enums;

import Models.Player;

public enum Ability {

    VITALITY("Vitality", "Increases the health of player by one"),
    DAMAGER("Damager", "Increases player damage by 25% for 10 seconds"),
    PROCREASE("Procrease", "Increases the projectile of weapon by one"),
    AMOCREASE("Amocrease", "Increases the amount of max ammo by one"),
    SPEEDY("Speedy", "Doubles player's speed for 10 seconds"),


    ;

    private final String name;
    private final String description;


    Ability(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
