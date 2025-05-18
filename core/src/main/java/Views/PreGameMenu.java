package Views;

import Controllers.PreGameMenuController;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PreGameMenu implements Screen {

    private final PreGameMenuController controller;
    private Stage stage;

    private final Label titleLabel;
    private final Label selectHeroLabel;
    private final Label selectWeaponLabel;
    private final Label gameDurationLabel;

    private final SelectBox<String> heroSelectBox;
    private final SelectBox<String> weaponSelectBox;
    private final SelectBox<String> durationSelectBox;

    private final TextButton startGameButton;

    private Table mainTable;

    public PreGameMenu(PreGameMenuController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);

        // Title
        titleLabel = new Label("PreGame Menu", skin);
        titleLabel.setAlignment(Align.center);

        // Selection labels
        selectHeroLabel = new Label("Select The Hero:", skin);
        selectWeaponLabel = new Label("Select Weapon:", skin);
        gameDurationLabel = new Label("Game Duration:", skin);

        // Hero dropdown
        heroSelectBox = new SelectBox<>(skin);
        heroSelectBox.setItems("Shana", "Diamond", "Scarlet", "Lilith", "Dasher");

        // Weapon dropdown
        weaponSelectBox = new SelectBox<>(skin);
        weaponSelectBox.setItems("Revolver", "Shotgun", "SMG");

        // Duration dropdown
        durationSelectBox = new SelectBox<>(skin);
        durationSelectBox.setItems("2 Minutes", "5 Minutes", "10 Minutes", "20 Minutes");

        // Start game button
        startGameButton = new TextButton("Start Game", skin);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Set up title
        titleLabel.setFontScale(1.5f);

        // Create main table
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        // Load and set background image
        Texture bgTexture = new Texture(Gdx.files.internal("background.png"));
        mainTable.setBackground(new TextureRegionDrawable(new TextureRegion(bgTexture)));

        // Add components to table
        titleLabel.setFontScale(3f);
        mainTable.add(titleLabel).colspan(2).padBottom(40).row();

        // Hero selection row
        selectHeroLabel.setFontScale(2f);
        mainTable.add(selectHeroLabel).width(400).row();
        mainTable.add(heroSelectBox).width(500).padBottom(15).row();

        // Weapon selection row
        selectWeaponLabel.setFontScale(2f);
        mainTable.add(selectWeaponLabel).width(400).row();
        mainTable.add(weaponSelectBox).width(500).padBottom(15).row();

        // Duration selection row
        gameDurationLabel.setFontScale(2f);
        mainTable.add(gameDurationLabel).width(400).row();
        mainTable.add(durationSelectBox).width(500).padBottom(30).row();

        // Start game button
        mainTable.add(startGameButton).colspan(2).width(300).pad(20);

        stage.addActor(mainTable);

        // Add button listener
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleStartGame(
                    heroSelectBox.getSelected(),
                    weaponSelectBox.getSelected(),
                    durationSelectBox.getSelected()
                );
            }
        });
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
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }

    // Getters for controller
    public SelectBox<String> getHeroSelectBox() {
        return heroSelectBox;
    }

    public SelectBox<String> getWeaponSelectBox() {
        return weaponSelectBox;
    }

    public SelectBox<String> getDurationSelectBox() {
        return durationSelectBox;
    }

    public Stage getStage() {
        return stage;
    }
}
