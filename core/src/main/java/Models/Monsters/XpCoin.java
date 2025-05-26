package Models.Monsters;

import Models.CollisionRectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.io.Serial;
import java.io.Serializable;


public class XpCoin implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private transient Sprite sprite;
    private final CollisionRectangle collisionRectangle;
    private boolean collected = false;

    public XpCoin(float x, float y) {
        this.sprite = new Sprite(new Texture("XpCoin.png"));
        this.sprite.setSize(10,10);
        this.sprite.setPosition(x, y);
        this.collisionRectangle = new CollisionRectangle(x, y, sprite.getWidth() * 0.5f, sprite.getHeight() * 0.5f);
    }

    public void render(Batch batch) {
        if (!collected) {
            sprite.draw(batch);
        }
    }

    public void update(float deltaTime) {
        // Update the collision rectangle to follow the sprite
        collisionRectangle.setPosition(sprite.getX(), sprite.getY());
        collisionRectangle.setSize(sprite.getWidth(), sprite.getHeight());

        // Optional: Add floating animation, etc. later
    }


    public CollisionRectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }

    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public void initGraphic(){
        this.sprite = new Sprite(new Texture("XpCoin.png"));
        this.sprite.setSize(10,10);
    }

}
