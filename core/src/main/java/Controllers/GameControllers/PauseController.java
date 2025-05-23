package Controllers.GameControllers;

import Models.App;
import Models.GameAssetManager;
import Models.enums.GameState;
import Models.enums.Ability;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseController {
    private final GameController controller;
    private final Stage uiStage;
    private final Table pauseTable;

    public PauseController(GameController controller, Stage stage) {
        this.controller = controller;
        this.uiStage = stage;
        this.pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.center();
    }

    public void showPauseMenu() {
        pauseTable.clear();

        Label title = new Label("Game Paused", GameAssetManager.getInstance().getSkin(), "title");

        TextButton resumeButton = new TextButton("Resume",  GameAssetManager.getInstance().getSkin());
        TextButton saveAndQuitButton = new TextButton("Save & Quit",  GameAssetManager.getInstance().getSkin());
        TextButton abilitiesButton = new TextButton("Abilities",  GameAssetManager.getInstance().getSkin());
        TextButton cheatsButton = new TextButton("Cheat Codes",  GameAssetManager.getInstance().getSkin());
        TextButton grayscaleButton = new TextButton("Grayscale", GameAssetManager.getInstance().getSkin());
        TextButton giveUp = new TextButton("Give Up", GameAssetManager.getInstance().getSkin());

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.setGameState(GameState.PLAYING);
                hidePauseMenu();
            }
        });

        saveAndQuitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0); // Or return to main menu
            }
        });

        grayscaleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.getInstance().toggleGrayScale();
            }
        });

        abilitiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showAbilitiesDialog();
            }
        });

        giveUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showCheatDialog();
            }
        });

        cheatsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showCheatDialog();
            }
        });



        pauseTable.add(title).padBottom(40).row();
        pauseTable.add(resumeButton).width(320).padBottom(20).row();
        pauseTable.add(grayscaleButton).width(320).padBottom(20).row();
        pauseTable.add(abilitiesButton).width(320).padBottom(20).row();
        pauseTable.add(cheatsButton).width(320).padBottom(20).row();
        pauseTable.add(giveUp).width(320).padBottom(20).row();
        pauseTable.add(saveAndQuitButton).width(320).padBottom(20).row();

        uiStage.addActor(pauseTable);
    }

    public void hidePauseMenu() {
        pauseTable.remove();
    }

    private void showAbilitiesDialog() {
        Dialog dialog = new Dialog("Acquired Abilities",  GameAssetManager.getInstance().getSkin()) {
            @Override
            protected void result(Object object) {
                // Optional
            }
        };

        Table table = new Table();
        for (Ability ability : controller.getPlayer().getAllAbilities()) {
            StringBuilder sb = new StringBuilder();
            sb.append("- ").append(ability.getName()).append(": ").append(ability.getDescription()).append("\n");
            table.add(new Label(sb, GameAssetManager.getInstance().getSkin())).left().padBottom(10).row();

        }
        dialog.getContentTable().add(table).pad(20);
        dialog.button("OK");
        dialog.show(uiStage);
    }

    private void showCheatDialog() {
        Dialog dialog = new Dialog("Cheat Codes",  GameAssetManager.getInstance().getSkin()) {
            @Override
            protected void result(Object object) {
                // Optional
            }
        };
        Table table = new Table();
        table.add(new Label("Alt + 1: Decrease Time", GameAssetManager.getInstance().getSkin())).left().padBottom(10).row();
        table.add(new Label("Alt + 2: Increase Level", GameAssetManager.getInstance().getSkin())).left().padBottom(10).row();
        table.add(new Label("Alt + 3: Full Health", GameAssetManager.getInstance().getSkin())).left().padBottom(10).row();
        table.add(new Label("Alt + 4: Unlimited Ammo", GameAssetManager.getInstance().getSkin())).left().padBottom(10).row();

        dialog.getContentTable().add(table).pad(20);
        dialog.button("OK");
        dialog.show(uiStage);
    }
}
