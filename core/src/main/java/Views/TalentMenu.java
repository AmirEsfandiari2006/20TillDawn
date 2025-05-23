package Views;

import Controllers.TalentMenuController;
import Models.GameAssetManager;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static javax.swing.JColorChooser.showDialog;

public class TalentMenu implements Screen {

    private final TalentMenuController controller;
    private Stage stage;

    private final Label titleLabel;


    public TalentMenu(TalentMenuController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
        this.titleLabel = new Label("Talent Menu", skin, "title");

    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = GameAssetManager.getInstance().getSkin();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(titleLabel).padBottom(20).row();

        // 1. Show Hero Info
        TextButton heroInfoButton = new TextButton("Hero Info", skin);
        heroInfoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showDialog("Hero Info",
                    "-Shana         HP:4   Speed:4\n" +
                    "-Diamond    HP:7   Speed:1\n" +
                    "-Scarlet       HP:3   Speed:5\n" +
                    "-Lilith          HP:5   Speed:3\n" +
                    "-Dasher       HP:2   Speed:10\n");
            }
        });

        // 2. Show Game Keys
        TextButton gameKeysButton = new TextButton("Game Keys", skin);
        gameKeysButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showDialog("Game Keys", controller.getGameKeyBindingsAsString());
            }
        });

        // 3. Show Cheat Codes
        TextButton cheatCodesButton = new TextButton("Cheat Codes", skin);
        cheatCodesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showCheatDialog();
            }
        });

        // 4. Show Abilities
        TextButton abilitiesButton = new TextButton("Show Abilities", skin);
        abilitiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showDialog("Abilities",
                    "-Vitality: Increases the health of player by one\n" +
                    "-Damager: Increases player damage by 25% for 10 seconds\n" +
                    "-Procrease: Increases the projectile of weapon by one\n" +
                    "-Amocrease: Increases the amount of max ammo by one\n" +
                    "-Speedy: Doubles player's speed for 10 seconds\n");
            }
        });

        // Back Button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goBack(); // Implement this in controller
            }
        });

        // Add buttons to the table
        table.defaults().pad(10).width(300);
        table.add(heroInfoButton).width(400).row();
        table.add(gameKeysButton).width(400).row();
        table.add(cheatCodesButton).width(400).row();
        table.add(abilitiesButton).width(400).row();
        table.add(backButton).padTop(30).row();
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
    }

    public Stage getStage() {
        return stage;
    }

    private void showDialog(String title, String message) {
        Skin skin = GameAssetManager.getInstance().getSkin();

        Dialog dialog = new Dialog(title, skin) {
            @Override
            protected void result(Object object) {
                this.hide();
            }
        };

        Table contentTable = new Table();

        String[] lines = message.split("\n");
        for (String line : lines) {
            Label label = new Label(line, skin);
            contentTable.add(label).padBottom(10).left();
            contentTable.row();
        }

        dialog.getContentTable().add(contentTable).pad(20);
        dialog.button("OK");
        dialog.show(stage);
    }


}
