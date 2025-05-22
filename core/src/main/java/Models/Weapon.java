package Models;

import Models.enums.WeaponType;
import com.badlogic.gdx.Gdx;

public class Weapon {


    private final WeaponType type;

    private int bonusProjectile;
    private int bonusMaxAmmo;

    public Weapon(WeaponType type) {
        this.type = type;
        this.type.getSprite().setX((float) (Gdx.graphics.getWidth() / 2));
        this.type.getSprite().setY((float) (Gdx.graphics.getHeight() / 2));
    }


    public WeaponType getType() {
        return type;
    }

    public int getProjectile() {
        return this.getType().getProjectTile() + bonusProjectile;
    }

    public int getBonusProjectile() {
        return bonusProjectile;
    }


    public void setBonusProjectile(int bonusProjectile) {
        this.bonusProjectile = bonusProjectile;
    }

    public int getMaxAmmo() {
        return this.type.getMaxAmmo() + bonusMaxAmmo;
    }


    public int getBonusMaxAmmo() {
        return bonusMaxAmmo;
    }

    public void setBonusMaxAmmo(int bonusMaxAmmo) {
        this.bonusMaxAmmo = bonusMaxAmmo;
    }
}
