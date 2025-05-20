package Models;


import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    private final Texture playerTexture = new Texture("Characters/1/idle1.png");
    private final Sprite playerSprite = new Sprite(playerTexture);

    private int playerSpeed = 5;



    private float time = 5;

    private CollisionRectangle collisionRectangle;

    private boolean isIdle = true;
    private boolean isRunning = false;

    public Player() {
        playerSprite.setPosition((float) Main.WORLD_WIDTH /2,(float)Main.WORLD_HEIGHT/2);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        this.collisionRectangle = new CollisionRectangle((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2 ,playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public CollisionRectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public boolean isIdle() {
        return isIdle;
    }

    public boolean isRunning() {
        return isRunning;
    }


    public void setIdle(boolean idle) {
        isIdle = idle;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }
}
