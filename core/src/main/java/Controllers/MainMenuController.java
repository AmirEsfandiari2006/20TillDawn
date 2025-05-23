package Controllers;

import Models.App;
import Models.GameAssetManager;
import Models.Utils;
import Views.*;
import com.Final.Main;

import java.util.MissingFormatArgumentException;

public class MainMenuController {
    private MainMenu view;

    public void setView(MainMenu view) {
        this.view = view;
    }

    public void handleLogout() {
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController() , GameAssetManager.getInstance().getSkin()));
    }

    public void handlePreGame() {
        Main.getMain().setScreen(new PreGameMenu(new PreGameMenuController() , GameAssetManager.getInstance().getSkin()));
    }

    public void handleProfileButton() {
        if(App.getInstance().getCurrentUser().getUsername().equals("Guest")) {
            Utils.showErrorDialog(view.getStage(),"Error","You are not logged in.");
            return;
        }
        Main.getMain().setScreen(new ProfileMenu(new ProfileMenuController() , GameAssetManager.getInstance().getSkin()));
    }


    public void handleScoreboardButton() {
        Main.getMain().setScreen(new ScoreboardMenu(new ScoreboardController() , GameAssetManager.getInstance().getSkin()));
    }

    public void handleSettingButton() {
        Main.getMain().setScreen(new SettingMenu(new SettingMenuController() , GameAssetManager.getInstance().getSkin()));
    }

    public void handleTalentButton() {
        Main.getMain().setScreen(new TalentMenu(new TalentMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}
