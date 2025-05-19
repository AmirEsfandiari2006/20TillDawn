package Controllers;

import Controllers.GameControllers.GameController;
import Models.GameAssetManager;
import Models.Weapons.Revolver;
import Models.Weapons.SMG;
import Models.Weapons.Shotgun;
import Models.Weapons.Weapon;
import Views.GameLauncher;
import Views.PreGameMenu;
import com.Final.Main;

public class PreGameMenuController {
    private PreGameMenu view;

    public void setView(PreGameMenu view) {
        this.view = view;
    }

    public void handleStartGame(String selectedHero, String selectedWeapon, String selectedDuration) {
        Main.getMain().setScreen(new GameLauncher(new GameController(), GameAssetManager.getInstance().getSkin()));
    }


    public Weapon getWeapon(String weapon){
        switch (weapon){
            case "SMG": return new SMG();
            case "Shotgun": return new Shotgun();
            case "Revolver": return new Revolver();
        }
        assert false : "Invalid weapon name" ;
        return null;
    }

}
