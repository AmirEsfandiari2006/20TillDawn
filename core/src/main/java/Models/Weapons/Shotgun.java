package Models.Weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Shotgun extends Weapon {
    private final Texture texture = new Texture("Weapons/Shotgun.png");
    private final Sprite sprite = new Sprite(texture);
    private int ammo = 8;


    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public int getAmmo() {
        return ammo;
    }

    @Override
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}
