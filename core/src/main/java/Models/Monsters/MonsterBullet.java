package Models.Monsters;

import Models.CollisionRectangle;
import Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.Serial;
import java.io.Serializable;

public class MonsterBullet implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private transient Sprite sprite = new Sprite(new Texture("Monsters/EyeBat/monsterBullet.png"));
    private final float speed = 200f;
    private final CollisionRectangle collisionRectangle;
    private float dx, dy;

    public MonsterBullet(float startX, float startY, float targetX, float targetY) {

        sprite.setSize(10, 10);
        this.sprite.setPosition(startX, startY);
        float directionX = targetX - startX;
        float directionY = targetY - startY;
        float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);

        dx = (directionX / length) * speed;
        dy = (directionY / length) * speed;

        this.collisionRectangle = new CollisionRectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void update(float deltaTime) {
        sprite.setPosition(sprite.getX() + dx * deltaTime, sprite.getY() + dy * deltaTime);
        collisionRectangle.setPosition(sprite.getX(), sprite.getY());
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public CollisionRectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public void initGraphic() {
        sprite = new Sprite(new Texture("Monsters/EyeBat/monsterBullet.png"));
        sprite.setSize(10, 10);
    }
}
