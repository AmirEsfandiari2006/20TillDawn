package Controllers;

import Models.App;
import Models.GameAssetManager;
import Views.KeySettingsMenu;
import Views.MainMenu;
import Views.SettingMenu;
import com.Final.Main;

public class SettingMenuController {

    private SettingMenu view;

    public void setView(SettingMenu view) {
        this.view = view;
    }

    public void handleBackToMainMenu() {
        Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handleVolumeChanged(float volume) {
        App.getInstance().getSettings().setMusicVolume(volume);
        GameAssetManager.getInstance().getCurrentMusic().setVolume(volume);
    }

    public void handleMusicSelected(String musicName) {
        App.getInstance().getSettings().setCurrentMusic(musicName);
        GameAssetManager.getInstance().playMusic(musicName);
    }

    public void handleSfxToggled(boolean enabled) {
        App.getInstance().getSettings().setSfxEnabled(enabled);
    }

    public void handleAutoReloadToggled(boolean enabled) {
        App.getInstance().getSettings().setAutoReload(enabled);
    }

    public void handleKeyBindingChange() {
        Main.getMain().setScreen(new KeySettingsMenu(new KeySettingsController(), GameAssetManager.getInstance().getSkin()));
    }

}
