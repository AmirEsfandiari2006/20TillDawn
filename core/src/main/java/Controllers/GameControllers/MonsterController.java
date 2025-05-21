package Controllers.GameControllers;

import Models.Monsters.EyeBat;
import Models.Monsters.Monster;
import Models.Monsters.Tentacle;
import Models.Player;
import com.Final.Main;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class MonsterController {

    private final Random random = new Random();
    private final Player player;
    private final ArrayList<Monster> monsters;
    private final int gameTotalTime;

    private float tentacleTimer = 0f;
    private static final float TENTACLE_SPAWN_TIME = 3f;

    private float eyeBatTimer = 0f;
    private static final float EYEBAT_SPAWN_TIME = 10f;

    public MonsterController(Player player, int gameTotalTime) {
        this.player = player;
        this.gameTotalTime = (gameTotalTime * 60);
        this.monsters = new ArrayList<>();
    }

    public void update(float deltaTime, float elapsedTime) {
        tentacleTimer += deltaTime;
        if (tentacleTimer >= TENTACLE_SPAWN_TIME) {
            IntStream.range(0, ((int) (elapsedTime / 30) + 1)).forEach(i -> spawnTentacle());
            tentacleTimer = 0f;
        }

        eyeBatTimer += deltaTime;
        if ((elapsedTime > ((float) gameTotalTime / 4)) && eyeBatTimer >= EYEBAT_SPAWN_TIME) {
            for(int i = 0; i < (((4 * elapsedTime) - gameTotalTime + 30) / 30); i++){
                spawnEyeBat();
            }
            eyeBatTimer = 0f;
        }

        for (Monster monster : monsters) {
            monster.update(deltaTime, player);
            monster.render(Main.getBatch());
        }
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


}
