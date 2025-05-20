package Models;

import Models.enums.WeaponType;
import com.badlogic.gdx.Gdx;

public class Weapon {

    private final WeaponType type;

    public Weapon(WeaponType type) {
        this.type = type;
        this.type.getSprite().setX((float)(Gdx.graphics.getWidth() / 2 ));
        this.type.getSprite().setY((float)(Gdx.graphics.getHeight() / 2));
    }

    public WeaponType getType() {
        return type;
    }
}
