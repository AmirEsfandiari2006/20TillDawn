package Views;

import Controllers.KeySettingsController;
import Controllers.SettingMenuController;
import Models.GameAssetManager;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class KeySettingsMenu implements Screen {

    private Stage stage;
    private final Label instructionLabel;
    private String waitingForKey = null;

    private final KeySettingsController controller;

    public KeySettingsMenu(KeySettingsController controller, Skin skin) {
        this.controller = controller;
        instructionLabel = new Label("Click a button to rebind a key", skin);
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(instructionLabel).colspan(2).padBottom(20);
        table.row();

        controller.addKeyBindButton(table, "Move Up", "moveUp");
        controller.addKeyBindButton(table, "Move Down", "moveDown");
        controller.addKeyBindButton(table, "Move Left", "moveLeft");
        controller.addKeyBindButton(table, "Move Right", "moveRight");
        controller.addKeyBindButton(table, "Reload", "reload");

        TextButton backButton = new TextButton("Back", GameAssetManager.getInstance().getSkin());
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new SettingMenu(new SettingMenuController(), GameAssetManager.getInstance().getSkin())); // Change as needed
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        table.row().padTop(30);
        table.add(backButton).colspan(2);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                GameAssetManager.getInstance().playSFX("uiclick");
                if (waitingForKey != null) {
                    controller.setKeyBinding(waitingForKey, keycode);
                    controller.updateButtonLabel(waitingForKey);
                    waitingForKey = null;
                    instructionLabel.setText("Click a button to rebind a key");
                    return true;
                }

                return false;
            }
        });
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Label getInstructionLabel() {
        return instructionLabel;
    }

    public void setWaitingForKey(String waitingForKey) {
        this.waitingForKey = waitingForKey;
    }

    public KeySettingsController getController() {
        return controller;
    }
}
