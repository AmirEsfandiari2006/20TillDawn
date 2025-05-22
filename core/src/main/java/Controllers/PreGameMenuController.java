package Controllers;

import Controllers.GameControllers.GameController;
import Models.GameAssetManager;
import Models.Weapon;
import Models.enums.CharacterType;
import Models.enums.WeaponType;
import Views.GameLauncher;
import Views.PreGameMenu;
import com.Final.Main;

public class PreGameMenuController {
    private PreGameMenu view;

    public void setView(PreGameMenu view) {
        this.view = view;
    }

    public void handleStartGame(String selectedHero, String selectedWeapon, String selectedDuration) {
        Main.getMain().setScreen(new GameLauncher(
            new GameController(
                getSelectedCharacter(selectedHero),
                getWeapon(selectedWeapon),
                getGameTime(selectedDuration)
            ),
            GameAssetManager.getInstance().getSkin()
        ));
    }

    public Weapon getWeapon(String weapon){
        switch (weapon){
            case "SMG": return new Weapon(WeaponType.SMG);
            case "Shotgun": return new Weapon(WeaponType.SHOTGUN);
            case "Revolver": return new Weapon(WeaponType.REVOLVER);
        }
        assert false : "Invalid weapon name" ;
        return null;
    }

    public CharacterType getSelectedCharacter(String character) {
        switch (character){
            case "Shana": return CharacterType.SHANA;
            case "Diamond": return CharacterType.DIAMOND;
            case "Scarlet": return CharacterType.SCARLET;
            case "Lilith": return CharacterType.LILITH;
            case "Dasher": return CharacterType.DASHER;
        }
        assert false : "Invalid character name" ;
        return null;
    }

    public int getGameTime(String duration) {
        switch (duration){
            //TODO don't forget this
            case "2 Minutes": return 1;
            case "5 Minutes": return 5;
            case "10 Minutes": return 10;
            case "20 Minutes": return 20;
        }
        assert false : "Invalid duration" ;
        return -1;
    }

}
