package Models;

import java.util.HashMap;
import java.util.Map;

public class Setting {

    private boolean autoReload = false;
    private String currentMusic = "FirstTrack";
    private float musicVolume = 1.0f;
    private boolean sfxEnabled = true;
    private boolean grayscaleEnabled = false;

    private final Map<String, Integer> keyBindings = new HashMap<>();

    public Setting() {
        keyBindings.put("moveUp", com.badlogic.gdx.Input.Keys.W);
        keyBindings.put("moveDown", com.badlogic.gdx.Input.Keys.S);
        keyBindings.put("moveLeft", com.badlogic.gdx.Input.Keys.A);
        keyBindings.put("moveRight", com.badlogic.gdx.Input.Keys.D);
        keyBindings.put("shoot", com.badlogic.gdx.Input.Keys.SPACE);
        keyBindings.put("reload", com.badlogic.gdx.Input.Keys.R);
    }

    // Music volume
    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    // SFX toggle
    public boolean isSfxEnabled() {
        return sfxEnabled;
    }

    public void setSfxEnabled(boolean sfxEnabled) {
        this.sfxEnabled = sfxEnabled;
    }

    // Grayscale toggle
    public boolean isGrayscaleEnabled() {
        return grayscaleEnabled;
    }

    public void setGrayscaleEnabled(boolean grayscaleEnabled) {
        this.grayscaleEnabled = grayscaleEnabled;
    }

    public void toggleGrayscale() {
        this.grayscaleEnabled = !this.grayscaleEnabled;
    }

    // Current music
    public String getCurrentMusic() {
        return currentMusic;
    }

    public void setCurrentMusic(String currentMusic) {
        this.currentMusic = currentMusic;
    }

    // Auto reload
    public boolean isAutoReload() {
        return autoReload;
    }

    public void setAutoReload(boolean autoReload) {
        this.autoReload = autoReload;
    }

    // Key bindings
    public int getKeyBinding(String action) {
        return keyBindings.getOrDefault(action, -1);
    }

    public void setKeyBinding(String action, int keycode) {
        keyBindings.put(action, keycode);
    }

    public Map<String, Integer> getAllKeyBindings() {
        return keyBindings;
    }

}
