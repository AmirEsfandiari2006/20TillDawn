package Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.HashMap;
import java.util.Map;

public class GameAssetManager extends AssetManager {

    private Music currentMusic;
    private final Map<String, Sound> soundEffects = new HashMap<>();
    private Texture redPixel;

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

    private final Animation<TextureRegion> dasherAnimation = new Animation<>(
        0.1f,
        new TextureRegion(new Texture("Monsters/ElderDasher/ElderDeer0.png")),
        new TextureRegion(new Texture("Monsters/ElderDasher/ElderDeer1.png")),
        new TextureRegion(new Texture("Monsters/ElderDasher/ElderDeer2.png")),
        new TextureRegion(new Texture("Monsters/ElderDasher/ElderDeer3.png"))
        );


    public void queueMusic() {
        load("Musics/FirstTrack.mp3", Music.class);
        load("Musics/SecondTrack.mp3", Music.class);
        load("Musics/ThirdTrack.mp3", Music.class);
    }

    public void loadSoundEffects() {
        soundEffects.put("explosion", Gdx.audio.newSound(Gdx.files.internal("SFXs/ExplosionBlood.wav")));
        soundEffects.put("healShort", Gdx.audio.newSound(Gdx.files.internal("SFXs/HealShort.wav")));
        soundEffects.put("hitByEnemy", Gdx.audio.newSound(Gdx.files.internal("SFXs/Hit.wav")));
        soundEffects.put("obtainPoints", Gdx.audio.newSound(Gdx.files.internal("SFXs/ObtainPoints.wav")));
        soundEffects.put("powerUp", Gdx.audio.newSound(Gdx.files.internal("SFXs/Powerup.wav")));
        soundEffects.put("reload", Gdx.audio.newSound(Gdx.files.internal("SFXs/Reload.wav")));
        soundEffects.put("shoot", Gdx.audio.newSound(Gdx.files.internal("SFXs/SingleShot.wav")));
        soundEffects.put("uiclick", Gdx.audio.newSound(Gdx.files.internal("SFXs/UIClick.wav")));
        soundEffects.put("walk", Gdx.audio.newSound(Gdx.files.internal("SFXs/walk.mp3")));
        soundEffects.put("winning", Gdx.audio.newSound(Gdx.files.internal("SFXs/YouWin.wav")));
        soundEffects.put("losing", Gdx.audio.newSound(Gdx.files.internal("SFXs/YouLose.wav")));


    }

    public void loadRedPixel() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 1); // Red
        pixmap.fill();
        redPixel = new Texture(pixmap);
        pixmap.dispose();
    }

    public Texture getRedPixel() {
        return redPixel;
    }

    public void finishLoadingAssets() {
        finishLoading();
    }

    public Music getMusic(String name) {
        return get("music/" + name + ".mp3", Music.class);
    }

    private GameAssetManager() {

    }

    public void playCurrentMusic(Setting setting) {
        if (currentMusic != null) {
            currentMusic.stop();
        }

        String trackName = setting.getCurrentMusic();
        if (!isLoaded("Musics/" + trackName)) {
            System.err.println("Music not loaded: " + trackName);
            return;
        }

        Music music = getMusic(trackName);
        music.setLooping(true);
        music.setVolume(setting.getMusicVolume());
        music.play();
        currentMusic = music;
    }

    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
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

    public Music getCurrentMusic() {
        if (currentMusic == null) {
            playMusic(App.getInstance().getSettings().getCurrentMusic());
        }
        return currentMusic;
    }

    public void setCurrentMusic(Music currentMusic) {
        this.currentMusic = currentMusic;
    }

    public void playMusic(String musicName) {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }

        currentMusic = get("Musics/" + musicName + ".mp3", Music.class);
        currentMusic.setLooping(true);
        currentMusic.setVolume(App.getInstance().getSettings().getMusicVolume());
        currentMusic.play();
    }

    public void playSFX(String name) {
        if (!App.getInstance().getSettings().isSfxEnabled()) return;

        Sound sound = soundEffects.get(name);
        if (sound != null) {
            sound.play(1f);
        } else {
            System.err.println("SFX not found: " + name);
        }
    }


    private static GameAssetManager instance = new GameAssetManager();
    private Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    public static GameAssetManager getInstance() {
        if(instance == null) {
            instance = new GameAssetManager();
        }
        return instance;
    }

    public Animation<TextureRegion> getDasherAnimation() {
        return dasherAnimation;
    }
}
