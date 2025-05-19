package Controllers.GameControllers;

import Models.GameAssetManager;
import Models.KeySettings;
import Models.Player;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class PlayerController {


    private final Player player;


    public PlayerController(Player player) {
        this.player = player;
    }

    public void idleAnimation() {
        Animation<Texture> animation = GameAssetManager.getInstance().getCharacter1Animation();

        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if(!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        } else {
            player.setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public Player getPlayer() {
        return player;
    }

    public void update() {
        player.getPlayerSprite().draw(Main.getBatch());

        if(player.isIdle()){
            idleAnimation();
        }
        handlePlayerInput();
    }

    public void handlePlayerInput() {
        float speed = player.getPlayerSpeed();

        if (Gdx.input.isKeyPressed(KeySettings.getInstance().moveUp)) {
            player.setY(player.getY() - speed);
        }
        if (Gdx.input.isKeyPressed(KeySettings.getInstance().moveDown)) {
            player.setY(player.getY() + speed);
        }
        if (Gdx.input.isKeyPressed(KeySettings.getInstance().moveLeft)) {
            player.setX(player.getX() + speed);
        }
        if (Gdx.input.isKeyPressed(KeySettings.getInstance().moveRight)) {
            player.setX(player.getX() - speed);
        }

    }

}
