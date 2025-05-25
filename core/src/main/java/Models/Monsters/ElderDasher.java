package Models.Monsters;

import Models.CollisionRectangle;
import Models.GameAssetManager;
import Models.Player;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ElderDasher implements Monster {
    private final Sprite sprite;
    private  float x, y;
    private int health = 400;
    private boolean facingRight = true;

    private final Animation<TextureRegion> animation;
    private float animationTimer;
    private float dashTimer;

    private final float DASH_INTERVAL = 5f;

    private final CollisionRectangle collisionRectangle;

    private float dashCooldownTimer = 0f;
    private float dashDurationTimer = 0f;
    private boolean isDashing = false;

    private static final float NORMAL_SPEED = 80f;
    private static final float DASH_SPEED = 660f;
    private static final float DASH_DURATION = 0.3f;

    public ElderDasher(float x,float y) {
        this.y = y;
        this.x = x;

        animation = GameAssetManager.getInstance().getDasherAnimation();
        sprite = new Sprite(animation.getKeyFrame(0));
        this.sprite.setSize(69,56);
        this.sprite.setPosition(x, y);
        this.collisionRectangle = new CollisionRectangle(x, y, sprite.getWidth(), sprite.getHeight());

    }

    @Override
    public void update(float deltaTime, Player player) {
        animationTimer += deltaTime;
        dashCooldownTimer += deltaTime;

        float dx = player.getPlayerSprite().getX() - x;
        float dy = player.getPlayerSprite().getY() - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // Handle dash timing
        if (isDashing) {
            dashDurationTimer += deltaTime;
            if (dashDurationTimer >= DASH_DURATION) {
                isDashing = false;
                dashDurationTimer = 0f;
            }
        } else if (dashCooldownTimer >= DASH_INTERVAL) {
            isDashing = true;
            dashCooldownTimer = 0f;
        }

        float speed = isDashing ? DASH_SPEED : NORMAL_SPEED;

        if (distance > 1f) {
            x += (dx / distance) * speed * deltaTime;
            y += (dy / distance) * speed * deltaTime;
            sprite.setPosition(x, y);
            collisionRectangle.setPosition(x, y);
        }

        // Flip sprite
        if (dx < 0 && facingRight) {
            facingRight = false;
        } else if (dx > 0 && !facingRight) {
            facingRight = true;
        }
    }


    @Override
    public Sprite getSprite() {
        return this.sprite;
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
    public CollisionRectangle getCollisionRectangle() {
        return this.collisionRectangle;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int health) {
            this.health = health;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
