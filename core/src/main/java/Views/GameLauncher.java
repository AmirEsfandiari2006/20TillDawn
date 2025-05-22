package Views;

import Controllers.GameControllers.GameController;
import Models.KeySettings;
import Models.enums.GameState;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameLauncher implements Screen, InputProcessor {

    private final GameController controller;

    // Gameplay rendering
    private final OrthographicCamera gameCamera;
    private final StretchViewport gameViewport;

    // UI rendering
    private final OrthographicCamera uiCamera;
    private final StretchViewport uiViewport;
    private Stage uiStage;

    private float elapsedTime = 0f;

    public GameLauncher(GameController gameController, Skin skin) {
        this.controller = gameController;

        // Game camera setup (follows player, can zoom)
        gameCamera = new OrthographicCamera();
        gameViewport = new StretchViewport(Main.WORLD_WIDTH, Main.WORLD_HEIGHT, gameCamera);

        // UI camera setup (static UI)
        uiCamera = new OrthographicCamera();
        uiViewport = new StretchViewport(Main.WORLD_WIDTH, Main.WORLD_HEIGHT, uiCamera);
        uiStage = new Stage(uiViewport);

        controller.setView(this, gameCamera);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        setStage(uiStage);
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        // Clear screen
        ScreenUtils.clear(Color.BLACK);

        updateInputProcessor();

        // GAME RENDERING
        gameCamera.update();
        gameCamera.zoom = 0.5f; // You can make this dynamic if needed
        Main.getBatch().setProjectionMatrix(gameCamera.combined);


        if (controller.getGameState() == GameState.PLAYING) {
            Main.getBatch().begin();
            controller.updateGame(delta, elapsedTime);
            controller.getBarController().render(Main.getBatch(), gameCamera,
                controller.getPlayer().getCurrentHealth(),
                controller.getPlayer().getFullHealth(),
                controller.getPlayer().getKills(),
                (int) elapsedTime,
                controller.getSelectedTime(),
                controller.getPlayer().getXp(),
                controller.getPlayer().getLevel(),
                controller.getPlayer().getXpNeededForNextLevel()
            );

            Main.getBatch().end();
            // Optional: debug shapes
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(gameCamera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            controller.getPlayerController().drawPlayerCollisionBox();
            controller.getPlayerController().drawXpCoinCollisionBoxes();
            controller.getTreeController().drawTreeCollisionBoxes();
            controller.getMonsterController().drawMonsterCollisionBoxes();
            controller.getMonsterController().drawMonsterBulletCollisionBoxes();
            shapeRenderer.end();
        }


        // UI RENDERING
        uiStage.act(delta);
        uiStage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
        uiViewport.update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        uiStage.dispose();
    }

    // Input Handling
    @Override
    public boolean keyDown(int keycode) { return false; }

    @Override
    public boolean keyUp(int keycode) { return false; }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == KeySettings.getInstance().shoot) {
            controller.getBulletController().handleWeaponShoot(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) { return false; }

    public void setStage(Stage stage) {
        this.uiStage = stage;
    }

    public Stage getStage() {
        return uiStage;
    }

    private void updateInputProcessor() {
        if (controller.getGameState() == GameState.PLAYING) {
            Gdx.input.setInputProcessor(this);
        } else {
            Gdx.input.setInputProcessor(uiStage);
        }
    }

}
