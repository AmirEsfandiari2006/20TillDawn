package Controllers.GameControllers;

import Models.Effects.Explosion;
import Models.GameAssetManager;
import Models.Monsters.Monster;
import Models.Monsters.MonsterBullet;
import Models.Player;
import Models.ShrinkingBarrier;
import Models.Tree;
import com.Final.Main;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Iterator;

public class HitController {

    private final GameController gameController;
    private final Player player;
    private final ArrayList<Monster> monsters;
    private final ArrayList<MonsterBullet> monsterBullets;
    private final ArrayList<Tree> trees;

    private final ArrayList<Explosion> explosions = new ArrayList<>();

    public HitController(Player player, ArrayList<Monster> monsters, ArrayList<MonsterBullet> monsterBullets, ArrayList<Tree> trees , GameController gameController) {
        this.gameController = gameController;
        this.player = player;
        this.monsters = monsters;
        this.monsterBullets = monsterBullets;
        this.trees = trees;
    }

    public void update() {

        player.updateInvincibility(Gdx.graphics.getDeltaTime());

        handleTreeHit();
        handleMonsterHit();
        handleMonsterBulletHit();

        updateExplosions();
    }

    public void handleShrinkBarrierHit(){
        if (player.isInvincible()) return;

        final int HIT_SHRINK_BARRIER_DAMAGE = 1;
        ShrinkingBarrier shrinkingBarrier = gameController.getShrinkingBarrier();

        if ( shrinkingBarrier.getDamageTimer() >= shrinkingBarrier.getDAMAGE_COOLDOWN() && !shrinkingBarrier.getRectangle().contains(player.getCollisionRectangle().getX(), player.getCollisionRectangle().getY())) {
            GameAssetManager.getInstance().playSFX("hitByEnemy");
            player.setCurrentHealth(player.getCurrentHealth() - HIT_SHRINK_BARRIER_DAMAGE); // or use your existing damage method
            player.setInvincible(true);
        }
    }

    public void handleTreeHit() {
        if (player.isInvincible()) return;

        final int HIT_TREE_DAMAGE = 1;

        for (Tree tree : trees) {
            if (tree.getCollisionRectangle().hasCollision(player.getCollisionRectangle())) {
                GameAssetManager.getInstance().playSFX("hitByEnemy");
                player.setCurrentHealth(player.getCurrentHealth() - HIT_TREE_DAMAGE);
                player.setInvincible(true); // Activate invincibility
                break; // avoid multiple hits from several trees in one frame
            }
        }

    }

    public void handleMonsterHit(){
        if (player.isInvincible()) return;

        final int HIT_MONSTER_DAMAGE = 1;

        for (Monster monster : monsters) {

            if(monster.getCollisionRectangle().hasCollision(player.getCollisionRectangle())) {
                GameAssetManager.getInstance().playSFX("hitByEnemy");
                player.setCurrentHealth(player.getCurrentHealth() - HIT_MONSTER_DAMAGE);
                player.setInvincible(true); // Activate invincibility
                break; // avoid multiple hits from several trees in one frame
            }
        }
    }

    public void handleMonsterBulletHit() {
        if (player.isInvincible()) return;

        final int HIT_BULLET_DAMAGE = 1;

        Iterator<MonsterBullet> bulletIterator = monsterBullets.iterator();

        while (bulletIterator.hasNext()) {
            MonsterBullet monsterBullet = bulletIterator.next();

            if (monsterBullet.getCollisionRectangle().hasCollision(player.getCollisionRectangle())) {
                // Damage player
                player.setCurrentHealth(player.getCurrentHealth() - HIT_BULLET_DAMAGE);
                player.setInvincible(true);
                GameAssetManager.getInstance().playSFX("explosion");

                // Trigger explosion at bullet position
                float explosionX = monsterBullet.getSprite().getX();
                float explosionY = monsterBullet.getSprite().getY();

                Explosion explosion = new Explosion(GameAssetManager.getInstance().getExplosionAnimation(), explosionX, explosionY);
                this.explosions.add(explosion); // or wherever you store them

                bulletIterator.remove(); // Remove bullet
            }
        }
    }

    public void updateExplosions() {
        // Update explosions
        for (Iterator<Explosion> it = explosions.iterator(); it.hasNext();) {
            Explosion explosion = it.next();
            explosion.update(Gdx.graphics.getDeltaTime());
            if (explosion.isFinished()) {
                it.remove();
            }
        }

        for (Explosion explosion : explosions) {
            explosion.render(Main.getBatch());
        }
    }

}
