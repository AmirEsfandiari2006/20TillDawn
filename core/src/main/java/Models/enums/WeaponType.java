package Models.enums;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public enum WeaponType {

    REVOLVER(new Sprite(new Texture("Weapons/Revolver.png")),20,1,1,6),
    SHOTGUN(new Sprite(new Texture("Weapons/Shotgun.png")),10,4,1,2),
    SMG(new Sprite(new Texture("Weapons/SMG.png")),8,1,2,24)

    ;

    private final Sprite sprite;
    private final int damage;
    private final int projectTile;
    private final int timeReload;
    private final int maxAmmo;

    WeaponType(Sprite sprite, int damage, int projectTile, int timeReload, int maxAmmo) {
        this.sprite = sprite;
        this.damage = damage;
        this.projectTile = projectTile;
        this.timeReload = timeReload;
        this.maxAmmo = maxAmmo;
    }

    public int getDamage() {
        return damage;
    }

    public int getProjectTile() {
        return projectTile;
    }

    public int getTimeReload() {
        return timeReload;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
