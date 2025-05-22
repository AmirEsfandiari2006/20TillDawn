package Models.Monsters;

import Models.CollisionRectangle;
import Models.GameAssetManager;
import Models.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tentacle implements Monster {
    private final Sprite sprite;
    private float x, y;
    private int health = 25;

    private final Animation<Texture> spawnAnimation;
    private final Animation<Texture> moveAnimation;
    private Animation<Texture> currentAnimation;

    private float animationTimer = 0f;
    private boolean spawning = true;

    private final CollisionRectangle collisionRect;

    public Tentacle(float x, float y) {
        this.x = x;
        this.y = y;

        spawnAnimation = GameAssetManager.getInstance().getTentacleSpawnAnimation();
        moveAnimation = GameAssetManager.getInstance().getTentacleMoveAnimation();
        currentAnimation = spawnAnimation;

        this.sprite = new Sprite(spawnAnimation.getKeyFrame(0));
        this.sprite.setPosition(x, y);

        // Create the collision rectangle using sprite dimensions
        this.collisionRect = new CollisionRectangle(x, y, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void update(float deltaTime, Player player) {
        animationTimer += deltaTime;

        if (spawning && currentAnimation.isAnimationFinished(animationTimer)) {
            currentAnimation = moveAnimation;
            animationTimer = 0f;
            spawning = false;
        }

        float dx = player.getPlayerSprite().getX() - x;
        float dy = player.getPlayerSprite().getY() - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        float speed = 50f;
        if (distance > 1f) {
            x += (dx / distance) * speed * deltaTime;
            y += (dy / distance) * speed * deltaTime;
            sprite.setPosition(x, y);
            collisionRect.setPosition(x, y); // update collision rectangle
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Texture currentFrame = currentAnimation.getKeyFrame(animationTimer, !spawning);
        batch.draw(currentFrame, x, y);
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

    @Override
    public CollisionRectangle getCollisionRectangle() {
        return collisionRect;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}


