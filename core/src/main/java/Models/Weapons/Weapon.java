package Models.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Weapon {
    private final Texture texture = new Texture((String) null);
    private final Sprite sprite = new Sprite(texture);
    private int ammo;


    public Weapon(){
        sprite.setX((float) Gdx.graphics.getWidth() / 2);
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(50,50);
    }

    public Texture getTexture() {
        return null;
    }

    public Sprite getSprite() {
        return null;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}
