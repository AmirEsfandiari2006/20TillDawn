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

    private float blinkTimer = 0f;
    private boolean visible = true;

    private boolean invincible = false;
    private float invincibleTime = 0f;
    private static final float INVINCIBILITY_DURATION = 2f; // 2 seconds of invincibility

    private int kills = 0;

    private int xp = 0;
    private int level = 0;

    private int bonusHealth;
    private int currentHealth;

    private float time = 5;
    private final CollisionRectangle collisionRectangle;

    private boolean isIdle = true;
    private boolean isRunning = false;

    public Player(CharacterType selectedCharacter, Weapon selectedWeapon){
        playerSprite.setPosition((float) Main.WORLD_WIDTH /2,(float)Main.WORLD_HEIGHT/2);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        this.collisionRectangle = new CollisionRectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth() / 3, playerSprite.getHeight() / 3);
        this.characterType = selectedCharacter;
        this.weapon = selectedWeapon;
        this.currentHealth = this.getFullHealth();
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

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void updatePlayerCollisionRectangle() {
        float spriteX = this.getPlayerSprite().getX();
        float spriteY = this.getPlayerSprite().getY();
        float spriteWidth = this.getPlayerSprite().getWidth();
        float spriteHeight = this.getPlayerSprite().getHeight();

        // Assuming your CollisionRectangle has getWidth() and getHeight() methods:
        float rectWidth = this.getCollisionRectangle().getWidth();
        float rectHeight = this.getCollisionRectangle().getHeight();

        float centeredX = spriteX + (spriteWidth - rectWidth) / 2f;
        float centeredY = spriteY + (spriteHeight - rectHeight) / 2f;

        this.getCollisionRectangle().setPosition(centeredX, centeredY);
    }

    public int getFullHealth() {
        return characterType.getHealth() + this.bonusHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }


    public void setBonusHealth(int bonusHealth) {
        this.bonusHealth = bonusHealth;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
        if (invincible) {
            invincibleTime = INVINCIBILITY_DURATION;
        }
    }

    public void updateInvincibility(float deltaTime) {
        if (invincible) {
            invincibleTime -= deltaTime;

            // Update blink
            blinkTimer += deltaTime;
            if (blinkTimer >= 0.2f) { // Toggle every 0.1 seconds
                visible = !visible;
                blinkTimer = 0f;
            }

            if (invincibleTime <= 0) {
                invincible = false;
                invincibleTime = 0;
                visible = true;      // Ensure player becomes visible again
                blinkTimer = 0f;     // Reset blink timer
            }
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addXp(int amount) {
        this.xp += amount;
        while (xp >= getXpNeededForNextLevel()) {
            xp -= getXpNeededForNextLevel(); // remove only this level’s XP
            level++;
        }
    }

    public int getXpNeededForNextLevel() {
        return (level + 1) * 20;
    }

    public float getXpProgressRatio() {
        return (float) xp / getXpNeededForNextLevel();
    }
}
