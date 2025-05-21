package Models.Monsters;

import Models.CollisionRectangle;
import Models.GameAssetManager;
import Models.Player;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EyeBat implements Monster {

    private final Sprite sprite;
    private float x, y;
    private int health = 50;
    private boolean facingRight = true;

    private final Animation<TextureRegion> animation;
    private float animationTimer = 0f;

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

        // Flip only when direction changes
        if (dx < 0 && facingRight) {
            sprite.flip(true, false);
            facingRight = false;
        } else if (dx > 0 && !facingRight) {
            sprite.flip(true, false);
            facingRight = true;
        }
    }


    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(animationTimer, true);

        if (!facingRight && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        } else if (facingRight && currentFrame.isFlipX()) {
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

    public CollisionRectangle getCollisionRect() {
        return collisionRect;
    }
}
