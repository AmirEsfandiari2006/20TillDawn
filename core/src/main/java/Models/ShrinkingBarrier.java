package Models;

import com.Final.Main;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import Models.Player;

public class ShrinkingBarrier {
    private float x, y, width, height;
    private float shrinkSpeed = 5f; // pixels per second
    private boolean active = true;

    private final float DAMAGE_COOLDOWN = 1f; // 1 sec delay between damage
    private float damageTimer = 0f;



    public ShrinkingBarrier(float screenWidth, float screenHeight) {
        this.x = 0;
        this.y = 0;
        this.width = screenWidth;
        this.height = screenHeight;
    }

    public void update(float deltaTime, Player player) {
        if (!active) return;

        damageTimer += deltaTime;

        // Shrink from all sides
        x += shrinkSpeed * deltaTime;
        y += shrinkSpeed * deltaTime;
        width -= 2 * shrinkSpeed * deltaTime;
        height -= 2 * shrinkSpeed * deltaTime;

        // Clamp to avoid negative size
        if (width < 10 || height < 10) {
            width = 10;
            height = 10;
        }

    }

    public void render() {
        if (!active) return;

        Texture red = GameAssetManager.getInstance().getRedPixel();

        // Top edge
        Main.getBatch().draw(red, x, y + height - 2, width, 2);

        // Bottom edge
        Main.getBatch().draw(red, x, y, width, 2);

        // Left edge
        Main.getBatch().draw(red, x, y, 2, height);

        // Right edge
        Main.getBatch().draw(red, x + width - 2, y, 2, height);
    }

    public void deactivate() {
        active = false;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isActive() {
        return active;
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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getShrinkSpeed() {
        return shrinkSpeed;
    }

    public void setShrinkSpeed(float shrinkSpeed) {
        this.shrinkSpeed = shrinkSpeed;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public float getDAMAGE_COOLDOWN() {
        return DAMAGE_COOLDOWN;
    }

    public float getDamageTimer() {
        return damageTimer;
    }

    public void setDamageTimer(float damageTimer) {
        this.damageTimer = damageTimer;
    }
}
