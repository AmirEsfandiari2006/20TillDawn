package Controllers.GameControllers;

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

    private float tentacleTimer = 0f;
    private static final float TENTACLE_SPAWN_TIME = 3f;

    public MonsterController(Player player) {
        this.player = player;
        this.monsters = new ArrayList<>();
    }

    public void update(float deltaTime, float elapsedTime) {
        tentacleTimer += deltaTime;
        if (tentacleTimer >= TENTACLE_SPAWN_TIME) {
            IntStream.range(0, (int) (elapsedTime / 30)).forEach(i -> spawnTentacle());
            tentacleTimer = 0f;
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
}
