package Models.enums;

import Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.io.Serializable;

public enum CharacterType implements Serializable {

    SHANA(GameAssetManager.getInstance().getShanaIdleAnimation(), "Shana",4,4),
    DIAMOND(GameAssetManager.getInstance().getDiamondIdleAnimation(), "Diamond",7,1),
    SCARLET(GameAssetManager.getInstance().getScarletIdleAnimation(), "Scarlet",3,5),
    LILITH(GameAssetManager.getInstance().getLilithIdleAnimation(), "Lilith",5,3),
    DASHER(GameAssetManager.getInstance().getDasherIdleAnimation(), "Dasher",2,10),

    ;
    private static final long serialVersionUID = 1L;

    private final Animation<Texture> idleAnimation;
    private final String name;
    private final int health;
    private final int speed;




    CharacterType(Animation<Texture> idleAnimation,String name, int health, int speed) {
        this.idleAnimation = idleAnimation;
        this.name = name;
        this.health = health;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public Animation<Texture> getIdleAnimation() {
        return idleAnimation;
    }
}
