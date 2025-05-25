package Controllers.GameControllers;

import Models.CollisionRectangle;
import Models.Monsters.*;
import Models.Player;
import Models.ShrinkingBarrier;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class MonsterController {

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final Random random = new Random();


    private final GameController gameController;

    private final Player player;
    private final OrthographicCamera camera;

    private final ArrayList<Monster> monsters;
    private final ArrayList<MonsterBullet> monsterBullets;
    private final ArrayList<XpCoin> xpCoins;

    private final int gameTotalTime;

    private float tentacleTimer = 0f;
    private static final float TENTACLE_SPAWN_TIME = 3f;

    private float eyeBatTimer = 0f;
    private static final float EYEBAT_SPAWN_TIME = 10f;

    private boolean isBossSpawned = false;

    public MonsterController(Player player, int gameTotalTime,ArrayList<Monster> monsters, ArrayList<XpCoin> xpCoins, OrthographicCamera camera, ArrayList<MonsterBullet> monsterBullets, GameController gameController) {
        this.gameController = gameController;
        this.player = player;
        this.gameTotalTime = (gameTotalTime * 60);
        this.monsters = monsters;
        this.monsterBullets = monsterBullets;
        this.xpCoins = xpCoins;
        this.camera = camera;
    }

    public void update(float deltaTime, float elapsedTime) {
        tentacleTimer += deltaTime;
        if (tentacleTimer >= TENTACLE_SPAWN_TIME) {
            IntStream.range(0, ((int) (elapsedTime / 30) + 1)).forEach(i -> spawnTentacle());
            tentacleTimer = 0f;
        }

        eyeBatTimer += deltaTime;
        if ((elapsedTime > ((float) gameTotalTime / 4)) && eyeBatTimer >= EYEBAT_SPAWN_TIME) {
            for (int i = 0; i < (((4 * elapsedTime) - gameTotalTime + 30) / 30); i++) {
                spawnEyeBat();
            };
            eyeBatTimer = 0f;
        }

        for (Monster monster : monsters) {
            monster.update(deltaTime, player);
            monster.render(Main.getBatch());

            if (monster instanceof EyeBat) {
                MonsterBullet bullet = ((EyeBat) monster).updateAndCheckFire(deltaTime, player);
                if (bullet != null) {
                    monsterBullets.add(bullet);
                }
            }
        }

        if( elapsedTime > ((float) gameTotalTime / 2) && !isBossSpawned) {
            isBossSpawned = true;
            spawnElderDasher();
        }

        if(!isBossFound()){
            gameController.setShrinkingBarrier(null);
        } else {
            if(gameController.getShrinkingBarrier() == null) {
                gameController.setShrinkingBarrier(new ShrinkingBarrier(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
            }
            gameController.getShrinkingBarrier().update(deltaTime,player);
            gameController.getShrinkingBarrier().render();
        }

        for (MonsterBullet bullet : monsterBullets) {
            bullet.update(deltaTime);
            bullet.render(Main.getBatch());
        }



        updateCoins(Gdx.graphics.getDeltaTime());

    }

    private void spawnTentacle() {
        float x = random.nextFloat() * (Main.WORLD_WIDTH - 50);
        float y = random.nextFloat() * (Main.WORLD_HEIGHT - 50);
        monsters.add(new Tentacle(x, y));
    }

    private void spawnEyeBat() {
        float x = random.nextFloat() * (Main.WORLD_WIDTH - 50);
        float y = random.nextFloat() * (Main.WORLD_HEIGHT - 50);
        monsters.add(new EyeBat(x, y));
    }

    public void spawnElderDasher(){
        float x = random.nextFloat() * (Main.WORLD_WIDTH - 50);
        float y = random.nextFloat() * (Main.WORLD_HEIGHT - 50);
        monsters.add(new ElderDasher(x, y));
        gameController.activeShrinkBarrier();
    }

    private void updateCoins(float deltaTime) {
        for (XpCoin coin : xpCoins) {
            coin.update(deltaTime);
            coin.render(Main.getBatch());
        }
    }

    public void drawMonsterCollisionBoxes() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        for (Monster monster : monsters) {
            CollisionRectangle rect = monster.getCollisionRectangle();
            shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }

        shapeRenderer.end();
    }

    public void drawMonsterBulletCollisionBoxes() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (MonsterBullet bullet : monsterBullets) {
            CollisionRectangle rect = bullet.getCollisionRectangle();
            shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
        shapeRenderer.end();
    }

    public boolean isBossFound(){
        return monsters.stream().anyMatch(monster -> monster instanceof ElderDasher);
    }

}
