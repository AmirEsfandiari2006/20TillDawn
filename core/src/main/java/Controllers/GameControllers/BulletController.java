package Controllers.GameControllers;

import Models.Bullet;
import Models.Effects.DeathEffect;
import Models.Monsters.Monster;
import Models.Monsters.XpCoin;
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
    private final ArrayList<DeathEffect> deathEffects = new ArrayList<>();
    private final ArrayList<XpCoin> xpCoins;
    private final ArrayList<Monster> monsters;

    public BulletController(Weapon weapon, Player player, OrthographicCamera camera, ArrayList<Monster> monsters, ArrayList<XpCoin> xpCoins) {
        this.camera = camera;
        this.player = player;
        this.weapon = weapon;
        this.bullets = new ArrayList<>();
        this.monsters = monsters;
        this.xpCoins = xpCoins;
    }

    public void update() {
        updateBullets(Gdx.graphics.getDeltaTime());
        updateDeathEffects(Gdx.graphics.getDeltaTime());
    }

    public void updateBullets(float deltaTime) {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet b = bulletIterator.next();
            b.update(deltaTime);
            b.draw(Main.getBatch());

            boolean bulletHit = false;

            Iterator<Monster> monsterIterator = monsters.iterator();
            while (monsterIterator.hasNext()) {
                Monster monster = monsterIterator.next();
                if (b.getCollisionRectangle().hasCollision(monster.getCollisionRectangle())) {
                    handleShootMonster(b, monster); // This should not directly remove from the lists!
                    bulletHit = true;
                    if (monster.getHealth() <= 0) {
                        //Remove Monster
                        monsterIterator.remove();
                        player.setKills(player.getKills() + 1);
                        //Setup death Animation
                        deathEffects.add(new DeathEffect(monster.getSprite().getX(), monster.getSprite().getY()));
                        //Drop Xp coin
                        XpCoin xpCoin = new XpCoin(monster.getX(), monster.getY());
                        xpCoins.add(xpCoin);
                    }
                    break;
                }
            }

            if (bulletHit || b.isOffScreen()) {
                bulletIterator.remove(); // Safe removal
            }
        }
    }

    public void updateDeathEffects(float deltaTime) {
        Iterator<DeathEffect> fxIterator = deathEffects.iterator();
        while (fxIterator.hasNext()) {
            DeathEffect fx = fxIterator.next();
            fx.update(deltaTime);
            fx.render(Main.getBatch());
            if (fx.isFinished()) {
                fxIterator.remove();
            }
        }
    }


    public void handleShootMonster(Bullet b, Monster monster) {
        // Apply damage
        monster.setHealth(monster.getHealth() - b.getDamage());

        if (monster.getHealth() > 0) {
            // Compute direction from bullet to monster
            float dx = monster.getSprite().getX() - b.getSprite().getX();
            float dy = monster.getSprite().getY() - b.getSprite().getY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            float knockbackStrength = 20f; // Adjust for stronger effect
            if (distance > 0.001f) {
                float knockbackX = (dx / distance) * knockbackStrength;
                float knockbackY = (dy / distance) * knockbackStrength;

                // New coordinates
                float newX = monster.getSprite().getX() + knockbackX;
                float newY = monster.getSprite().getY() + knockbackY;

                // Update sprite
                monster.getSprite().setPosition(newX, newY);

                // Also update internal position if your monster uses x/y (e.g., EyeBat)
                monster.setPosition(newX, newY);

                // Update collision rectangle
                monster.getCollisionRectangle().setPosition(newX, newY);
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
