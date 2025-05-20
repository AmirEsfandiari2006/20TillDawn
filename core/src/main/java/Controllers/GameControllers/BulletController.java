package Controllers.GameControllers;

import Models.Bullet;
import Models.Player;
import Models.Weapon;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Iterator;


public class BulletController {

    private final Weapon weapon;
    private final Player player;
    private final OrthographicCamera camera;
    private final ArrayList<Bullet> bullets;

    public BulletController(Weapon weapon, Player player, OrthographicCamera camera) {
        this.camera = camera;
        this.player = player;
        this.weapon = weapon;
        this.bullets = new ArrayList<>();
    }

    public void update() {
        updateBullets(Gdx.graphics.getDeltaTime());
    }

    public void updateBullets(float deltaTime) {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet b = iterator.next();
            b.update(deltaTime);
            b.draw(Main.getBatch());
            if (b.isOffScreen()) {
                iterator.remove();
            }
        }
    }

    public void handleWeaponShoot(int mouseX, int mouseY) {
        Vector3 worldMouse = new Vector3(mouseX, mouseY, 0);
        camera.unproject(worldMouse);

        float startX = weapon.getType().getSprite().getX() + weapon.getType().getSprite().getWidth() / 2f;
        float startY = weapon.getType().getSprite().getY() + weapon.getType().getSprite().getHeight() / 2f;

        float dirX = worldMouse.x - startX;
        float dirY = worldMouse.y - startY;

        if (dirX == 0 && dirY == 0) return;

        bullets.add(new Bullet(startX, startY, dirX, dirY, weapon.getType().getDamage()));
    }

}
