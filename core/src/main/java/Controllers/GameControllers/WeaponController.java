package Controllers.GameControllers;

import Models.App;
import Models.CursorUtils;
import Models.Monsters.Monster;
import Models.Player;
import Models.Weapon;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class WeaponController {

    private final CursorUtils cursorUtils = new CursorUtils();


    private final GameController gameController;
    private final Player player;
    private final Weapon weapon;
    private final OrthographicCamera camera;

    public WeaponController(Weapon weapon, Player player, OrthographicCamera camera, GameController gameController) {
        this.gameController = gameController;
        this.player = player;
        this.weapon = weapon;
        this.camera = camera;
    }

    public void update() {
        Sprite playerSprite = player.getPlayerSprite();
        Sprite weaponSprite = weapon.getType().getSprite();

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        handleWeaponRotation(Gdx.input.getX(), Gdx.input.getY());

        float playerCenterX = playerSprite.getX() + playerSprite.getWidth() / 2;

        boolean mouseLeft = mousePos.x < playerCenterX;

        if (mouseLeft && !playerSprite.isFlipX()) {
            playerSprite.flip(true, false);
        } else if (!mouseLeft && playerSprite.isFlipX()) {
            playerSprite.flip(true, false);
        }

        if (mouseLeft && !weaponSprite.isFlipX()) {
            weaponSprite.flip(true, false);
        } else if (!mouseLeft && weaponSprite.isFlipX()) {
            weaponSprite.flip(true, false);
        }

        float offsetX = 15f;
        float offsetY = 8f;
        float weaponX;

        if (mouseLeft) {
            weaponX = playerSprite.getX() - offsetX;
        } else {
            weaponX = playerSprite.getX() + offsetX;
        }

        float weaponY = playerSprite.getY() + offsetY;
        weaponSprite.setPosition(weaponX, weaponY);

        weapon.getType().getSprite().setOriginCenter();

        Main.getBatch().draw(weapon.getType().getSprite(),
            weapon.getType().getSprite().getX(),
            weapon.getType().getSprite().getY(),
            weapon.getType().getSprite().getOriginX(),
            weapon.getType().getSprite().getOriginY(),
            weapon.getType().getSprite().getWidth(),
            weapon.getType().getSprite().getHeight(),
            weapon.getType().getSprite().getScaleX(),
            weapon.getType().getSprite().getScaleY(),
            weapon.getType().getSprite().getRotation()
        );
    }

    public void handleWeaponRotation(int screenX, int screenY) {
        Vector3 mousePos = new Vector3(screenX, screenY, 0);
        camera.unproject(mousePos);

        Sprite playerSprite = player.getPlayerSprite();
        Sprite weaponSprite = weapon.getType().getSprite();

        float playerX = playerSprite.getX() + playerSprite.getWidth() / 2f;
        float playerY = playerSprite.getY() + playerSprite.getHeight() / 2f;

        float dx = mousePos.x - playerX;
        float dy = mousePos.y - playerY;

        float angle = (float) Math.toDegrees((float) Math.atan2(dy, dx));

        if (angle > 180) angle -= 360;
        if (angle < -180) angle += 360;

        if (mousePos.x < playerX && !playerSprite.isFlipX()) {
            playerSprite.flip(true, false);
            weaponSprite.flip(true, false);
        } else if (mousePos.x >= playerX && playerSprite.isFlipX()) {
            playerSprite.flip(true, false);
            weaponSprite.flip(true, false);
        }

        if (playerSprite.isFlipX()) {
            angle = -angle;
        }

        if (playerSprite.isFlipX()) {
            angle = 180 - angle;
            if (angle > 180) angle -= 360;
            if (angle < -180) angle += 360;
        }

        angle = Math.max(-45f, Math.min(45f, angle));

        weaponSprite.setRotation(angle);
    }


    public void handleAutoAimShoot() {
        if (!App.getInstance().getSettings().isAutoAimEnabled()) return;

        ArrayList<Monster> monsters = gameController.getMonsters();
        if (monsters.isEmpty()) return;

        // Get player center in world space
        Sprite playerSprite = player.getPlayerSprite();
        float playerCenterX = playerSprite.getX() + playerSprite.getWidth() / 2f;
        float playerCenterY = playerSprite.getY() + playerSprite.getHeight() / 2f;

        // Find nearest monster
        Monster nearest = null;
        float minDist = Float.MAX_VALUE;

        for (Monster monster : monsters) {
            Sprite mSprite = monster.getSprite();
            float monsterCenterX = mSprite.getX() + mSprite.getWidth() / 2f;
            float monsterCenterY = mSprite.getY() + mSprite.getHeight() / 2f;

            float dx = monsterCenterX - playerCenterX;
            float dy = monsterCenterY - playerCenterY;
            float distSq = dx * dx + dy * dy;

            if (distSq < minDist) {
                minDist = distSq;
                nearest = monster;
            }
        }

        if (nearest == null) return;

        // Get world position of the monster's center
        Sprite monsterSprite = nearest.getSprite();
        float monsterWorldX = monsterSprite.getX() + monsterSprite.getWidth() / 2f;
        float monsterWorldY = monsterSprite.getY() + monsterSprite.getHeight() / 2f;

        // Convert world target to screen coordinates
        Vector3 monsterScreenPos = camera.project(new Vector3(monsterWorldX, monsterWorldY, 0));
        int screenX = (int) monsterScreenPos.x;
        int screenY = Gdx.graphics.getHeight() - (int) monsterScreenPos.y;

        // Move cursor to this screen position (optional)
        cursorUtils.moveCursorToScreen(screenX, screenY);

        // Shoot using correct screen coords
        handleWeaponRotation(screenX, screenY);
        gameController.getBulletController().handleWeaponShoot(screenX, screenY);
    }




}
