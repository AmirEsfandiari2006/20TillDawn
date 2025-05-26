package Models.Monsters;

import Models.CollisionRectangle;
import Models.GameAssetManager;
import Models.Player;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.Serial;
import java.io.Serializable;

public class EyeBat implements Monster, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private transient Sprite sprite;
    private float x, y;
    private int health = 50;
    private boolean facingRight = true;

    private transient Animation<TextureRegion> animation;
    private float animationTimer = 0f;
    private float fireTimer = 0f;

    private static final float FIRE_INTERVAL = 3f;

    private final CollisionRectangle collisionRect;

    public EyeBat(float x, float y) {
        this.x = x;
        this.y = y;

        animation = GameAssetManager.getInstance().getEyeBatAnimation();
        this.sprite = new Sprite(animation.getKeyFrame(0));
        this.sprite.setPosition(x, y);
        this.collisionRect = new CollisionRectangle(x, y, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void update(float deltaTime, Player player) {
        animationTimer += deltaTime;
        fireTimer += deltaTime;

        float dx = player.getPlayerSprite().getX() - x;
        float dy = player.getPlayerSprite().getY() - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        float speed = 50f;
        if (distance > 1f) {
            x += (dx / distance) * speed * deltaTime;
            y += (dy / distance) * speed * deltaTime;
            sprite.setPosition(x, y);
            collisionRect.setPosition(x, y);
        }

        // Use setFlip instead of flip to avoid toggling
        if (dx < 0 && facingRight) {
            sprite.setFlip(true, false);
            facingRight = false;
        } else if (dx > 0 && !facingRight) {
            sprite.setFlip(false, false);
            facingRight = true;
        }
    }

    public MonsterBullet updateAndCheckFire(float deltaTime, Player player) {
        if (fireTimer >= FIRE_INTERVAL) {
            fireTimer = 0f;
            return new MonsterBullet(x + sprite.getWidth() / 2, y + sprite.getHeight() / 2,
                player.getPlayerSprite().getX(),
                player.getPlayerSprite().getY());
        }
        return null;
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(animationTimer, true);

        // Flip the frame region according to facingRight
        if (facingRight && currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        } else if (!facingRight && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }

        sprite.setRegion(currentFrame);
        sprite.draw(batch);
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public CollisionRectangle getCollisionRectangle() {
        return collisionRect;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void initGraphic(){
        animation = GameAssetManager.getInstance().getEyeBatAnimation();
        this.sprite = new Sprite(animation.getKeyFrame(0));
    }

}
