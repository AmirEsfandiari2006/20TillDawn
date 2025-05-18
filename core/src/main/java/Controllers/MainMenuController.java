package Controllers;

import Models.App;
import Models.GameAssetManager;
import Models.Utils;
import Views.LoginMenu;
import Views.MainMenu;
import Views.PreGameMenu;
import Views.ProfileMenu;
import com.Final.Main;

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



}
