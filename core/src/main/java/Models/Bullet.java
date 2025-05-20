package Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet {

    private final Sprite bulletSprite = new Sprite(new Texture("bullet.png"));

    public Bullet(int x, int y) {
        bulletSprite.setPosition(x, y);
    }
}
