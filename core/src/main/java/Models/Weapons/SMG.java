package Models.Weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SMG extends Weapon {
    private final Texture texture = new Texture("Weapons/SMG.png");
    private final Sprite sprite = new Sprite(texture);
    private int ammo = 30;


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
