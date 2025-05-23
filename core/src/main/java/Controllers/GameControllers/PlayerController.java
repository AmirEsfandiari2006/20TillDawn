package Controllers.GameControllers;

import Models.*;
import Models.Monsters.XpCoin;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    private final GameController gameController;

    public PlayerController(Player player, OrthographicCamera camera, ArrayList<XpCoin> xpCoins, GameController gameController ) {
        this.gameController = gameController;
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
        updateGunReload();

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
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        CollisionRectangle rect = player.getCollisionRectangle();
        shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

        shapeRenderer.end();
    }

    public void drawXpCoinCollisionBoxes() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);

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

        if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.ALT_RIGHT)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                gameController.getCheatController().decreaseTime();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                gameController.getCheatController().increaseLevel();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                gameController.getCheatController().fullHealth();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
                gameController.getCheatController().unlimitedAmmo();
            }
        }

        // --- Movement handling ---
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

        reloadGun();

        player.updatePlayerCollisionRectangle();
        player.getPlayerSprite().setX(newX);
        player.getPlayerSprite().setY(newY);
        player.getCollisionRectangle().setPosition(newX, newY);
    }

    private void reloadGun() {
        Weapon weapon = player.getWeapon();

        if (weapon.isReloading()) return;

        if (App.getInstance().getSetting().isAutoReload() || Gdx.input.isKeyPressed(KeySettings.getInstance().reload)) {
            weapon.setReloading(true);
            weapon.setReloadTimer(weapon.getType().getTimeReload());
        }
    }

    private void updateGunReload(){
        if (player.getWeapon().isReloading()) {
            float timeLeft = player.getWeapon().getReloadTimer() - Gdx.graphics.getDeltaTime();
            player.getWeapon().setReloadTimer(timeLeft);

            if (timeLeft <= 0) {
                player.getWeapon().setCurrentAmmo(player.getWeapon().getMaxAmmo());
                player.getWeapon().setReloading(false);
            }
        }
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

        float maskWidth = 150;
        float maskHeight = 150;

        Main.getBatch().setColor(1, 1, 1, 0.2f);
        Main.getBatch().draw(lightMask, playerCenterX - maskWidth / 2f, playerCenterY - maskHeight / 2f, maskWidth, maskHeight);
        Main.getBatch().setColor(1, 1, 1, 1);
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
