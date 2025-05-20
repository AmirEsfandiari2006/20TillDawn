package Controllers.GameControllers;

import Models.Player;
import Models.Weapon;
import Models.enums.CharacterType;
import Views.GameLauncher;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameController {
    private GameLauncher view;
    private OrthographicCamera camera;

    private PlayerController playerController;
    private WorldController worldController;
    private TreeController treeController;
    private BulletController bulletController;
    private WeaponController weaponController;

    private final CharacterType selectedCharacter;
    private final Weapon selectedWeapon;
    private final int selectedTime;


    public GameController(CharacterType selectedCharacter,Weapon weapon, int selectedTime) {
        this.selectedCharacter = selectedCharacter;
        this.selectedWeapon = weapon;
        this.selectedTime = selectedTime;
    }

    public void setView(GameLauncher view, OrthographicCamera camera) {
        this.view = view;
        this.playerController = new PlayerController(new Player(selectedCharacter,selectedWeapon),camera);
        this.worldController = new WorldController(playerController);
        this.treeController = new TreeController();
        this.bulletController = new BulletController();
        this.weaponController = new WeaponController(this.selectedWeapon);
    }

    public void updateGame() {
        worldController.update();
        playerController.update();
        treeController.update();
        bulletController.update();
        weaponController.update();
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public BulletController getBulletController() {
        return bulletController;
    }

    public int getSelectedTime() {
        return selectedTime;
    }
}
