package Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {
    private final Sprite bulletSprite = new Sprite(new Texture("bullet.png"));
    private final int damage;
    private final CollisionRectangle collisionRectangle;
    private final float dx, dy;
    private final float speed = 500f;

    public Bullet(float startX, float startY, float dirX, float dirY, int damage) {
        bulletSprite.setSize(10, 10);
        bulletSprite.setOriginCenter();
        bulletSprite.setPosition(startX, startY);

        this.collisionRectangle = new CollisionRectangle(bulletSprite.getX(), bulletSprite.getY(), bulletSprite.getWidth(), bulletSprite.getHeight());
        this.damage = damage;

        float length = (float) Math.sqrt(dirX * dirX + dirY * dirY);
        this.dx = dirX / length;
        this.dy = dirY / length;
    }

    public void update(float deltaTime) {
        float newX = bulletSprite.getX() + dx * speed * deltaTime;
        float newY = bulletSprite.getY() + dy * speed * deltaTime;
        bulletSprite.setPosition(newX, newY);
    }

    public void draw(SpriteBatch batch) {
        bulletSprite.draw(batch);
    }

    public Sprite getSprite() {
        return bulletSprite;
    }

    public boolean isOffScreen() {
        return bulletSprite.getX() < 0 || bulletSprite.getX() > Gdx.graphics.getWidth()
            || bulletSprite.getY() < 0 || bulletSprite.getY() > Gdx.graphics.getHeight();
    }
}

