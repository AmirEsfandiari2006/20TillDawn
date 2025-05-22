package Controllers.GameControllers;

import Models.Monsters.Monster;
import Models.Monsters.XpCoin;
import Models.Player;
import Models.Weapon;
import Models.enums.CharacterType;
import Views.GameLauncher;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class GameController {
    private GameLauncher view;
    private OrthographicCamera camera;

    private PlayerController playerController;
    private WorldController worldController;
    private TreeController treeController;
    private BulletController bulletController;
    private WeaponController weaponController;
    private MonsterController monsterController;
    private BarController barController;

    private final CharacterType selectedCharacter;
    private final Weapon selectedWeapon;
    private final int selectedTime;

    private Player player;



    private int score;


    public GameController(CharacterType selectedCharacter,Weapon weapon, int selectedTime) {
        this.selectedCharacter = selectedCharacter;
        this.selectedWeapon = weapon;
        this.selectedTime = selectedTime;
    }

    public void setView(GameLauncher view, OrthographicCamera camera) {
        this.view = view;
        this.player = new Player(selectedCharacter,selectedWeapon);
        ArrayList<Monster> monsters = new ArrayList<>();
        ArrayList<XpCoin> xpCoins = new ArrayList<>();
        this.playerController = new PlayerController(player,camera,xpCoins);
        this.worldController = new WorldController(playerController);
        this.treeController = new TreeController(camera);
        this.bulletController = new BulletController(selectedWeapon,player,camera,monsters,xpCoins);
        this.weaponController = new WeaponController(this.selectedWeapon,player,camera);
        this.monsterController = new MonsterController(player,selectedTime,monsters,xpCoins, camera);
        this.barController = new BarController();
    }

    public void updateGame(float deltaTime , float elapsedTime) {
        worldController.update();
        weaponController.update();
        playerController.update();
        treeController.update();
        bulletController.update();
        monsterController.update(deltaTime,elapsedTime);
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

    public MonsterController getMonsterController() {
        return monsterController;
    }


    public int getScore() {
        return score;
    }

    public BarController getBarController() {
        return barController;
    }

    public Player getPlayer() {
        return player;
    }

    public TreeController getTreeController() {
        return treeController;
    }
}
