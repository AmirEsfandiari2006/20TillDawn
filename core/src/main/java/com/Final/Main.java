package com.Final;

import Controllers.RegisterMenuController;
import Models.App;
import Models.GameAssetManager;
import Views.RegisterMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private static Main main;

    private static SpriteBatch batch;

    public static final int WORLD_WIDTH = 1980;
    public static final int WORLD_HEIGHT = 1080;

    public static ShaderProgram grayscaleShader;

    @Override
    public void create() {

        GameAssetManager.getInstance().queueMusic();
        GameAssetManager.getInstance().loadSoundEffects();
        GameAssetManager.getInstance().loadRedPixel();

        GameAssetManager.getInstance().finishLoadingAssets();

        GameAssetManager.getInstance().playMusic(App.getInstance().getSettings().getCurrentMusic());

        ShaderProgram.pedantic = false;
        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("Shaders/default.vert"),
            Gdx.files.internal("Shaders/grayscale.frag")
        );

        if (!grayscaleShader.isCompiled()) {
            System.out.println("Shader compile error: " + grayscaleShader.getLog());
        }

        Pixmap pixmap = new Pixmap(Gdx.files.internal("cursor.png"));
        Cursor cursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose();

        main = this;
        batch = new SpriteBatch();
        main.setScreen(new RegisterMenu(new RegisterMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();

    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }

}
