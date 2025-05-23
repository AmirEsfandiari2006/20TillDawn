package Views;

import Controllers.ScoreboardController;
import Models.App;
import Models.GameAssetManager;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ScoreboardMenu implements Screen {

    private final ScoreboardController controller;
    private Stage stage;

    private final Label titleLabel;
    private final Label usernameHeader;
    private final Label scoreHeader;
    private final Label killsHeader;
    private final Label timeHeader;
    private final TextButton backButton;

    public ScoreboardMenu(ScoreboardController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);

        titleLabel = new Label("Scoreboard", skin, "title");
        usernameHeader = new Label("Username", skin);
        scoreHeader = new Label("Score", skin);
        killsHeader = new Label("Kills", skin);
        timeHeader = new Label("Max Time", skin);
        backButton = new TextButton("Back", skin);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.center();
        stage.addActor(rootTable);

        Table scoreTable = new Table();
        scoreTable.center();

        // Title
        rootTable.add(titleLabel).colspan(4).padBottom(20).center().row();

        // Header row with listeners
        addHeader(scoreTable, usernameHeader, "username");
        addHeader(scoreTable, scoreHeader, "score");
        addHeader(scoreTable, killsHeader, "kills");
        addHeader(scoreTable, timeHeader, "maxTime");
        scoreTable.row();

        // User rows
        controller.displayUsers(App.getInstance().getUsers(), GameAssetManager.getInstance().getSkin(), scoreTable);

        rootTable.add(scoreTable).expandX().center().row();

        // Back button centered under table
        rootTable.add(backButton).colspan(4).padTop(30).center();

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleBackToMainMenu();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);
        ScreenUtils.clear(Color.BLACK);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Gdx.graphics.getDeltaTime());
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

    }

    private void addHeader(Table table, Label label, String fieldName) {
        label.setColor(Color.YELLOW); // Optional: visually highlight clickable headers
        label.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.sortBy(fieldName);
            }
        });
        table.add(label).pad(10);
    }



}
