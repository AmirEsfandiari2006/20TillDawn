package Controllers;

import Models.App;
import Models.GameAssetManager;
import Models.KeySettings;
import Views.MainMenu;
import Views.TalentMenu;
import com.Final.Main;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TalentMenuController {

    private TalentMenu view;

    public void setView(TalentMenu talentMenu) {
        this.view = talentMenu;
    }

    public void goBack() {
        Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public String getGameKeyBindingsAsString() {
        KeySettings keys = App.getInstance().getSettings().getKeySettings();
        return String.format("Move Up: %s\nMove Down: %s\nMove Left: %s\nMove Right: %s\nReload: %s",
            Input.Keys.toString(keys.moveUp),
            Input.Keys.toString(keys.moveDown),
            Input.Keys.toString(keys.moveLeft),
            Input.Keys.toString(keys.moveRight),
            Input.Keys.toString(keys.reload));
    }

    public void showCheatDialog() {
        Dialog dialog = new Dialog("Cheat Codes",  GameAssetManager.getInstance().getSkin()) {
            @Override
            protected void result(Object object) {
            }
        };
        Table table = new Table();
        table.add(new Label("Alt + 1: Decrease Time", GameAssetManager.getInstance().getSkin())).left().padBottom(10).row();
        table.add(new Label("Alt + 2: Increase Level", GameAssetManager.getInstance().getSkin())).left().padBottom(10).row();
        table.add(new Label("Alt + 3: Full Health", GameAssetManager.getInstance().getSkin())).left().padBottom(10).row();
        table.add(new Label("Alt + 4: Unlimited Ammo", GameAssetManager.getInstance().getSkin())).left().padBottom(10).row();

        dialog.getContentTable().add(table).pad(20);
        dialog.button("OK");
        dialog.show(view.getStage());
    }

}
