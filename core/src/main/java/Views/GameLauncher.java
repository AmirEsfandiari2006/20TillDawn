package Views;

import Controllers.GameControllers.GameController;
import Models.GameAssetManager;
import Models.KeySettings;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameLauncher implements Screen, InputProcessor {

    private BitmapFont font;



    private Stage stage;
    private final GameController controller;
    private final OrthographicCamera camera;
    private float elapsedTime = 0f;

    public GameLauncher(GameController gameController , Skin skin) {
            font = new BitmapFont();
            this.controller = gameController;
            camera = new OrthographicCamera();
            controller.setView(this,camera);
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(Main.WORLD_WIDTH, Main.WORLD_HEIGHT, camera));
        Gdx.input.setInputProcessor(this);

    }


    @Override
    public void render(float delta) {
        elapsedTime += delta;

        ScreenUtils.clear(Color.BLACK);

        camera.zoom = 0.5f;

        Main.getBatch().begin();

        controller.updateGame(delta, elapsedTime); // draw world
        controller.getBarController().render(
            Main.getBatch(),
            camera,
            controller.getPlayer().getHealth(),
            controller.getPlayer().getKills(),
            (int)elapsedTime,
            controller.getSelectedTime()
        );


        float camX = (int)camera.position.x;
        float camY = (int)camera.position.y;

        Main.getBatch().end();


        //Only for Debug
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        controller.getPlayerController().drawPlayerCollisionBox();
        controller.getPlayerController().drawXpCoinCollisionBoxes();
        controller.getTreeController().drawTreeCollisionBoxes();
        controller.getMonsterController().drawMonsterCollisionBoxes();
        shapeRenderer.end();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == KeySettings.getInstance().shoot) {
            controller.getBulletController().handleWeaponShoot(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
