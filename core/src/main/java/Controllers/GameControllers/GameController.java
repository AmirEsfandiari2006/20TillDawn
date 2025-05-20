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
        Player player = new Player(selectedCharacter,selectedWeapon);
        this.view = view;
        this.playerController = new PlayerController(player,camera);
        this.worldController = new WorldController(playerController);
        this.treeController = new TreeController();
        this.bulletController = new BulletController();
        this.weaponController = new WeaponController(this.selectedWeapon,player,camera);
    }

    public void updateGame() {
        worldController.update();
        weaponController.update();
        playerController.update();
        treeController.update();
        bulletController.update();
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

    public WeaponController getWeaponController() {
        return weaponController;
    }
}
