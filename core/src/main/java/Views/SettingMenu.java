package Views;

import Controllers.SettingMenuController;
import Models.App;
import Models.GameAssetManager;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingMenu implements Screen {

    private final SettingMenuController controller;
    private Stage stage;

    private final Label titleLabel;
    private final TextButton backButton;
    private final Slider volumeSlider;
    private final SelectBox<String> musicSelectBox;
    private final CheckBox sfxCheckbox;
    private final CheckBox autoReloadCheckbox;
    private final TextButton keyBindingButton;
    private final TextButton grayscaleToggleButton;

    public SettingMenu(SettingMenuController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);

        titleLabel = new Label("Settings", skin, "title");
        backButton = new TextButton("Back", skin);
        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        musicSelectBox = new SelectBox<>(skin);
        sfxCheckbox = new CheckBox("Enable SFXs", skin);
        autoReloadCheckbox = new CheckBox("Auto Reload", skin);
        keyBindingButton = new TextButton("Change Key", skin);
        grayscaleToggleButton = new TextButton("Grayscale", skin);

        // Example volume initialization
        volumeSlider.setValue(App.getInstance().getSettings().getMusicVolume());

        // Sample music names — make sure these match what's loaded in your asset manager
        musicSelectBox.setItems("FirstTrack", "SecondTrack", "ThirdTrack");
        musicSelectBox.setSelected(App.getInstance().getSettings().getCurrentMusic());

        sfxCheckbox.setChecked(App.getInstance().getSettings().isSfxEnabled());
        autoReloadCheckbox.setChecked(App.getInstance().getSettings().isAutoReload());

        setupListeners();
    }

    private void setupListeners() {
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleBackToMainMenu();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = volumeSlider.getValue();
                controller.handleVolumeChanged(volume);
            }
        });

        musicSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.handleMusicSelected(musicSelectBox.getSelected());
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        sfxCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.handleSfxToggled(sfxCheckbox.isChecked());
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        autoReloadCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.handleAutoReloadToggled(autoReloadCheckbox.isChecked());
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        keyBindingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleKeyBindingChange();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        grayscaleToggleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.getInstance().toggleGrayScale();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(titleLabel).colspan(4).padBottom(20).row();

        table.add(new Label("Music Volume", GameAssetManager.getInstance().getSkin())).pad(5);
        table.add(volumeSlider).padBottom(5).width(455).row();

        table.add(new Label("Music  Track", GameAssetManager.getInstance().getSkin())).pad(5);
        table.add(musicSelectBox).width(400).row();

        table.add(sfxCheckbox).colspan(2).pad(5).row();
        table.add(autoReloadCheckbox).colspan(2).pad(5).row();
        table.add(keyBindingButton).colspan(2).width(320).pad(5).row();
        table.add(grayscaleToggleButton).colspan(2).width(320).pad(5).row();

        table.add(backButton).colspan(2).padTop(20).row();

        stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

    public Stage getStage() { return stage; }
}
