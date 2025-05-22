package Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameAssetManager {


    private final Animation<Texture> character1Animation = new Animation<>(
        0.1f,
        new Texture("Characters/1/idle1.png"),
        new Texture("Characters/1/idle2.png"),
        new Texture("Characters/1/idle3.png"),
        new Texture("Characters/1/idle4.png"),
        new Texture("Characters/1/idle5.png")
    );

    private final Animation<Texture> treeAnimation = new Animation<>(
        2f,
        new Texture("Tree/tree1.png"),
        new Texture("Tree/tree2.png"),
        new Texture("Tree/tree3.png")
        );

    private final Animation<Texture> tentacleSpawnAnimation = new Animation<>(
        0.1f,
        new Texture("Monsters/Tentacle/Spawn/TentacleSpawn0.png"),
        new Texture("Monsters/Tentacle/Spawn/TentacleSpawn1.png"),
        new Texture("Monsters/Tentacle/Spawn/TentacleSpawn2.png")
        );

    private final Animation<Texture> tentacleMoveAnimation = new Animation<>(
        0.1f,
        new Texture("Monsters/Tentacle/Move/TentacleIdle0.png"),
        new Texture("Monsters/Tentacle/Move/TentacleIdle1.png"),
        new Texture("Monsters/Tentacle/Move/TentacleIdle2.png"),
        new Texture("Monsters/Tentacle/Move/TentacleIdle3.png")
        );

    private final Animation<TextureRegion> eyeBatAnimation = new Animation<>(
        0.1f,
        new TextureRegion(new Texture("Monsters/EyeBat/EyeBat0.png")),
        new TextureRegion(new Texture("Monsters/EyeBat/EyeBat1.png")),
        new TextureRegion(new Texture("Monsters/EyeBat/EyeBat2.png"))
        );

    private final Animation<TextureRegion> deathFX = new Animation<>(
        0.1f,
        new TextureRegion(new Texture("Effects/DeathFX/DeathFX_0.png")),
        new TextureRegion(new Texture("Effects/DeathFX/DeathFX_1.png")),
        new TextureRegion(new Texture("Effects/DeathFX/DeathFX_2.png")),
        new TextureRegion(new Texture("Effects/DeathFX/DeathFX_3.png"))
        );

    private final Animation<TextureRegion> explosionAnimation = new Animation<>(
        0.1f,
        new TextureRegion(new Texture("Effects/ExplosionFX/ExplosionFX_0.png")),
        new TextureRegion(new Texture("Effects/ExplosionFX/ExplosionFX_1.png")),
        new TextureRegion(new Texture("Effects/ExplosionFX/ExplosionFX_2.png")),
        new TextureRegion(new Texture("Effects/ExplosionFX/ExplosionFX_3.png")),
        new TextureRegion(new Texture("Effects/ExplosionFX/ExplosionFX_4.png")),
        new TextureRegion(new Texture("Effects/ExplosionFX/ExplosionFX_5.png"))
    );

    private GameAssetManager() {

    }

    private static GameAssetManager instance = new GameAssetManager();
    private Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    public static GameAssetManager getInstance() {
        if(instance == null) {
            instance = new GameAssetManager();
        }
        return instance;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Animation<Texture> getCharacter1Animation() {
        return character1Animation;
    }

    public Animation<Texture> getTreeAnimation() {
        return treeAnimation;
    }

    public Animation<Texture> getTentacleSpawnAnimation() {
        return tentacleSpawnAnimation;
    }

    public Animation<Texture> getTentacleMoveAnimation() {
        return tentacleMoveAnimation;
    }

    public Animation<TextureRegion> getEyeBatAnimation() {
        return eyeBatAnimation;
    }

    public Animation<TextureRegion> getDeathFX() {
        return deathFX;
    }

    public Animation<TextureRegion> getExplosionAnimation() {
        Texture sheet = new Texture("Effects/ExplosionFX/ExplosionFX_0.png");
        TextureRegion[][] frames = TextureRegion.split(sheet, 20, 20); // adjust to your frame size
        TextureRegion[] animationFrames = new TextureRegion[frames[0].length];

        for (int i = 0; i < frames[0].length; i++) {
            animationFrames[i] = frames[0][i];
        }

        return new Animation<>(0.1f, animationFrames);
    }
}
