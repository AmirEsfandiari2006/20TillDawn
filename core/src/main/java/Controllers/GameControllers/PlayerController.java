package Controllers.GameControllers;

import Models.GameAssetManager;
import Models.KeySettings;
import Models.Player;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector3;

public class PlayerController {


    private final Player player;
    private final OrthographicCamera camera;


    public PlayerController(Player player, OrthographicCamera camera) {
        this.camera = camera;
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


    public void update() {

        updateMouseDirection();
        Main.getBatch().draw(player.getPlayerSprite(), player.getPlayerSprite().getX(), player.getPlayerSprite().getY());

        if(player.isIdle()){
            idleAnimation();
        }
        handlePlayerInput();
        updateCamera();

    }

    private void updateCamera() {
        float cameraHalfWidth = camera.viewportWidth * camera.zoom / 2f;
        float cameraHalfHeight = camera.viewportHeight * camera.zoom / 2f;

        float x = player.getPlayerSprite().getX();
        float y = player.getPlayerSprite().getY();

        // Clamp the camera to world bounds
        x = Math.max(cameraHalfWidth, x);
        x = Math.min(Main.WORLD_WIDTH - cameraHalfWidth, x);
        y = Math.max(cameraHalfHeight, y);
        y = Math.min(Main.WORLD_HEIGHT - cameraHalfHeight, y);

        camera.position.set(x, y, 0);
        camera.update();
    }

    public void handlePlayerInput() {
        float speed = player.getPlayerSpeed();
        float newX = player.getPlayerSprite().getX();
        float newY = player.getPlayerSprite().getY();

        if (Gdx.input.isKeyPressed(KeySettings.getInstance().moveUp)) {
            newY += speed;
        }
        if (Gdx.input.isKeyPressed(KeySettings.getInstance().moveDown)) {
            newY -= speed;
        }
        if (Gdx.input.isKeyPressed(KeySettings.getInstance().moveLeft)) {
            newX -= speed;
            player.getPlayerSprite().setFlip(true, false);
        }
        if (Gdx.input.isKeyPressed(KeySettings.getInstance().moveRight)) {
            newX += speed;
            player.getPlayerSprite().setFlip(false, false);
        }

        float spriteWidth = player.getPlayerSprite().getRegionWidth();
        float spriteHeight = player.getPlayerSprite().getRegionHeight();

        newX = Math.max(0, Math.min(newX, Main.WORLD_WIDTH - spriteWidth));
        newY = Math.max(0, Math.min(newY, Main.WORLD_HEIGHT - spriteHeight));

        player.getPlayerSprite().setX(newX);
        player.getPlayerSprite().setY(newY);
    }

    private void updateMouseDirection() {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        float playerCenterX = player.getPlayerSprite().getX() + player.getPlayerSprite().getWidth() / 2;

        if (mousePos.x < playerCenterX && !player.getPlayerSprite().isFlipX()) {
            player.getPlayerSprite().flip(true, false);
        } else if (mousePos.x > playerCenterX && player.getPlayerSprite().isFlipX()) {
            player.getPlayerSprite().flip(true, false);
        }
    }


}
