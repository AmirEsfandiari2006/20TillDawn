package Models;


import Controllers.GameControllers.GameController;
import Models.enums.Ability;
import Models.enums.CharacterType;
import Models.enums.GameState;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Player  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private transient Texture playerTexture = new Texture("Characters/1/idle1.png");
    private transient Sprite playerSprite = new Sprite(playerTexture);

    private transient Texture lightMask;
    private transient Sprite lightSprite;

    private final CharacterType characterType;
    private final Weapon weapon;

    private float blinkTimer = 0f;
    private boolean visible = true;

    private boolean invincible = false;
    private float invincibleTime = 0f;
    private static final float INVINCIBILITY_DURATION = 2f;

    private int kills = 0;

    private int xp = 0;
    private int level = 0;

    private int bonusHealth;
    private int currentHealth;

    private float time = 5;
    private final CollisionRectangle collisionRectangle;

    private boolean isIdle = true;
    private boolean isRunning = false;

    private final transient GameController gameController;

    private float speedBoostTimer = 0f;
    private float damageBoostTimer = 0f;

    private float speedMultiplier = 1f;
    private float damageMultiplier = 1f;

    private final ArrayList<Ability> allAbilities = new ArrayList<>();

    public Player(GameController gameController,CharacterType selectedCharacter, Weapon selectedWeapon){
        this.gameController = gameController;
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
        return (int)( characterType.getSpeed() * speedMultiplier);
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
                visible = true;
                blinkTimer = 0f;
            }
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void increaseLevel() {
        level++;
        gameController.setGameState(GameState.ABILITY_SELECTION);
        gameController.getAbilitySelectionController().showAbilitySelection();
        GameAssetManager.getInstance().playSFX("powerUp");
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
            increaseLevel();
        }
    }

    public int getXpNeededForNextLevel() {
        return (level + 1) * 20;
    }

    public float getXpProgressRatio() {
        return (float) xp / getXpNeededForNextLevel();
    }

    public int getFinalDamage(){
        return (int) (damageMultiplier *  weapon.getType().getDamage());
    }

    public ArrayList<Ability> getAllAbilities() {
        return allAbilities;
    }

    public void applyAbility(Ability ability) {
        switch (ability) {
            case VITALITY:
                ++this.bonusHealth;
                this.currentHealth = getFullHealth();
                break;
            case DAMAGER:
                this.damageMultiplier = 1.25f;
                this.damageBoostTimer = 10f;
                break;
            case PROCREASE:
                this.weapon.setBonusProjectile(this.getWeapon().getBonusProjectile() + 1);
                break;
            case AMOCREASE:
                this.weapon.setBonusMaxAmmo(this.getWeapon().getBonusMaxAmmo() + 5);
                break;
            case SPEEDY:
                this.speedMultiplier = 2.0f;
                this.speedBoostTimer = 10f;
                break;
        }
    }

    public void updateAbilityTimer(float delta) {
        if (speedBoostTimer > 0) {
            speedBoostTimer -= delta;
            if (speedBoostTimer <= 0) {
                speedMultiplier = 1f;
            }
        }

        if (damageBoostTimer > 0) {
            damageBoostTimer -= delta;
            if (damageBoostTimer <= 0) {
                damageMultiplier = 1f;
            }
        }
    }

    public void initGraphic(){
        playerTexture = new Texture("Characters/1/idle1.png");
        playerSprite = new Sprite(playerTexture);
        initLightEffect();
    }

}
