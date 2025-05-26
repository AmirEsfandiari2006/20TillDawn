package Views;

import Controllers.StartMenuController;
import Models.GameAssetManager;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StartMenu implements Screen {

    private final StartMenuController controller;
    private final Stage stage;

    private final TextButton startButton;
    private Table table;

    private Texture bgTexture;
    private Texture logoTexture;
    private Texture divider;

    public StartMenu(StartMenuController controller, Skin skin) {
        this.controller = controller;
        this.stage = new Stage(new ScreenViewport());
        this.controller.setView(this);

        startButton = new TextButton("Start Game", skin);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.center();


        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        divider = new Texture(Gdx.files.internal("divider.png"));


        bgTexture = new Texture(Gdx.files.internal("startBackground.png"));
        table.setBackground(new TextureRegionDrawable(new TextureRegion(bgTexture)));

        Image titleLogo = new Image(logoTexture);
        Image diverImage = new Image(divider);


        table.add(titleLogo).padBottom(20).row();
        table.add(diverImage).padBottom(20).row();
        table.add(startButton).colspan(2).padBottom(20).row();
        table.padBottom(150);

        stage.addActor(table); // UI elements rendered on top

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.startButton();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        controller.update(delta);

        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();

        Main.getBatch().begin();
        controller.draw(); // ← draw thunder here
        Main.getBatch().end();

    }


    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        if (bgTexture != null) bgTexture.dispose();
        if (logoTexture != null) logoTexture.dispose();
    }


    public Stage getStage() {
        return stage;
    }
}
