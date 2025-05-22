package Models;


import Models.enums.CharacterType;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    private final Texture playerTexture = new Texture("Characters/1/idle1.png");
    private final Sprite playerSprite = new Sprite(playerTexture);

    private Texture lightMask;
    private Sprite lightSprite;

    private final CharacterType characterType;
    private final Weapon weapon;

    private int kills = 0;

    private float time = 5;
    private final CollisionRectangle collisionRectangle;

    private boolean isIdle = true;
    private boolean isRunning = false;

    public Player(CharacterType selectedCharacter, Weapon selectedWeapon){
        playerSprite.setPosition((float) Main.WORLD_WIDTH /2,(float)Main.WORLD_HEIGHT/2);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        this.collisionRectangle = new CollisionRectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
        this.characterType = selectedCharacter;
        this.weapon = selectedWeapon;
        initLightEffect();
    }

    public void initLightEffect() {
        lightMask = new Texture(Gdx.files.internal("whiteMask.png"));
        lightMask.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        lightSprite = new Sprite(lightMask);
        lightSprite.setSize(300, 300); // Adjust radius size here
        lightSprite.setOriginCenter();
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
        return characterType.getSpeed();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public int getHealth(){
        return characterType.getHealth();
    }


    public int getKills() {
        return this.kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }
}
