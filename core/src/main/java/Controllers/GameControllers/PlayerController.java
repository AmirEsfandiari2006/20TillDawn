package Controllers.GameControllers;

import Models.CollisionRectangle;
import Models.GameAssetManager;
import Models.KeySettings;
import Models.Monsters.XpCoin;
import Models.Player;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayerController {


    private final Player player;
    private final OrthographicCamera camera;

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private final Texture lightMask = new Texture(Gdx.files.internal("whiteMask.png"));
    private final ArrayList<XpCoin> xpCoins;

    public PlayerController(Player player, OrthographicCamera camera, ArrayList<XpCoin> xpCoins ) {
        this.camera = camera;
        this.player = player;
        this.xpCoins = xpCoins;
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
        renderLightMask();

        if (player.isVisible()) {
            Main.getBatch().draw(player.getPlayerSprite(),
                player.getPlayerSprite().getX(),
                player.getPlayerSprite().getY());
        }

        if (player.isIdle()) {
            idleAnimation();
        }

        handlePlayerInput();
        updateTakingCoin();
        updateCamera();
    }

    public void drawPlayerCollisionBox() {
        shapeRenderer.setProjectionMatrix(camera.combined); // Use camera projection
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED); // Or any color you prefer

        CollisionRectangle rect = player.getCollisionRectangle();
        shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

        shapeRenderer.end();
    }

    public void drawXpCoinCollisionBoxes() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN); // Green for XP coins

        for (XpCoin coin : xpCoins) {
            CollisionRectangle rect = coin.getCollisionRectangle();
            shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }

        shapeRenderer.end();
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

        // Round the camera position to whole pixels to avoid jitter
        x = Math.round(x);
        y = Math.round(y);

        camera.position.set(x, y, 0);
        camera.update();
        Main.getBatch().setProjectionMatrix(camera.combined);
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

        player.updatePlayerCollisionRectangle();

        player.getPlayerSprite().setX(newX);
        player.getPlayerSprite().setY(newY);

        player.getCollisionRectangle().setPosition(newX, newY);
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

    private void renderLightMask() {
        float playerCenterX = player.getPlayerSprite().getX() + player.getPlayerSprite().getWidth() / 4;
        float playerCenterY = player.getPlayerSprite().getY() + player.getPlayerSprite().getHeight() / 8;

        float maskWidth = 150;  // Set based on your image or desired light radius
        float maskHeight = 150;

        Main.getBatch().setColor(1, 1, 1, 0.2f); // Opacity of the light
        Main.getBatch().draw(lightMask, playerCenterX - maskWidth / 2f, playerCenterY - maskHeight / 2f, maskWidth, maskHeight);
        Main.getBatch().setColor(1, 1, 1, 1); // Reset color
    }

    private void updateTakingCoin() {
        for (XpCoin coin : xpCoins) {
            coin.update(Gdx.graphics.getDeltaTime());
        }
        Iterator<XpCoin> coins = xpCoins.iterator();
        while (coins.hasNext()) {
            XpCoin coin = coins.next();
            if (player.getCollisionRectangle().hasCollision(coin.getCollisionRectangle())) {
                final int XP_ADDITION = 3;
                player.addXp(XP_ADDITION);
                coin.collect();
                coins.remove();
            }
        }
    }
}
