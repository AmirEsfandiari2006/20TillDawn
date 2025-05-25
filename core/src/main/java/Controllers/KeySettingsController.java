package Controllers;

import Models.GameAssetManager;
import Models.KeySettings;
import Models.App;
import Views.KeySettingsMenu;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class KeySettingsController {

    private KeySettingsMenu view;
    public void setView(KeySettingsMenu view) { this.view = view; }

    private final KeySettings keySettings;
    private final Map<String, TextButton> keyButtons = new HashMap<>();

    public KeySettingsController() {
        this.keySettings = App.getInstance().getSettings().getKeySettings();
    }

    public void setKeyBinding(String field, int keycode) {
        try {
            Field keyField = KeySettings.class.getField(field);
            keyField.setInt(keySettings, keycode);
            System.out.println("Set " + field + " to: " + Keys.toString(keycode));
        } catch (Exception e) {
            System.err.println("Failed to bind key for " + field + ": " + e.getMessage());
        }
    }

    public int getKey(String field) {
        try {
            Field keyField = KeySettings.class.getField(field);
            return keyField.getInt(keySettings);
        } catch (Exception e) {
            System.err.println("Failed to get key for " + field + ": " + e.getMessage());
            return Keys.UNKNOWN;
        }
    }

    public void addKeyBindButton(Table table, String label, String field) {
        TextButton button = new TextButton(label + ": " + Keys.toString(this.getKey(field)),
            GameAssetManager.getInstance().getSkin());

        button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                view.setWaitingForKey(field);
                view.getInstructionLabel().setText("Press a key to bind for " + label);
            }
        });

        keyButtons.put(field, button);

        table.row().pad(10);
        table.add(button).width(400).colspan(2);
    }

    public void updateButtonLabel(String field) {
        TextButton button = keyButtons.get(field);
        if (button != null) {
            button.setText(enhancedName(field) + ": " + Keys.toString(getKey(field)));
        }
    }

    private String enhancedName(String field) {
        return switch (field) {
            case "moveUp" -> "Move Up";
            case "moveDown" -> "Move Down";
            case "moveLeft" -> "Move Left";
            case "moveRight" -> "Move Right";
            case "reload" -> "Reload";
            case "autoAim" -> "Auto Aim";
            default -> field;
        };
    }
}
