package Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
}
