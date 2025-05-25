package Controllers.GameControllers;

import Models.Monsters.Monster;
import Models.Monsters.MonsterBullet;
import Models.Monsters.XpCoin;
import Models.Player;
import Models.ShrinkingBarrier;
import Models.Tree;
import Models.Weapon;
import Models.enums.CharacterType;
import Models.enums.GameState;
import Views.GameLauncher;
import com.Final.Main;
import com.badlogic.gdx.graphics.OrthographicCamera;

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
    private HitController hitController;
    private AbilitySelectionController abilitySelectionController;
    private CheatController cheatController;
    private PauseController pauseController;
    private EndGameController endGameController;

    private GameState gameState = GameState.PLAYING;

    private final CharacterType selectedCharacter;
    private final Weapon selectedWeapon;
    private final int selectedTime;

    private Player player;

    private final ArrayList<Monster> monsters = new ArrayList<>();
    private final ArrayList<MonsterBullet> monsterBullets = new ArrayList<>();
    private final ArrayList<XpCoin> xpCoins = new ArrayList<>();
    private final ArrayList<Tree>  trees = new ArrayList<>();

    private ShrinkingBarrier shrinkingBarrier;


    public GameController(CharacterType selectedCharacter,Weapon weapon, int selectedTime) {
        this.selectedCharacter = selectedCharacter;
        this.selectedWeapon = weapon;
        this.selectedTime = selectedTime;
    }

    public void setView(GameLauncher view, OrthographicCamera camera) {
        this.view = view;
        this.player = new Player(this,selectedCharacter,selectedWeapon);
        this.playerController = new PlayerController(player,camera,xpCoins,this);
        this.worldController = new WorldController(playerController);
        this.treeController = new TreeController(camera, trees);
        this.bulletController = new BulletController(selectedWeapon,player,camera,monsters,xpCoins);
        this.weaponController = new WeaponController(this.selectedWeapon,player,camera,this);
        this.monsterController = new MonsterController(player,selectedTime,monsters,xpCoins, camera,monsterBullets,this);
        this.barController = new BarController(player);
        this.hitController = new HitController(player,monsters,monsterBullets,trees,this);
        this.abilitySelectionController = new AbilitySelectionController(this,player);
        this.cheatController = new CheatController(this);
        this.pauseController = new PauseController(this,view.getStage());
        this.endGameController = new EndGameController(this);
    }

    public void updateGame(float deltaTime , float elapsedTime) {
        worldController.update();
        weaponController.update();
        playerController.update();
        treeController.update();
        bulletController.update();
        monsterController.update(deltaTime,elapsedTime);
        hitController.update();
        abilitySelectionController.update();
        endGameController.checkGameEnded();
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

    public BarController getBarController() {
        return barController;
    }

    public Player getPlayer() {
        return player;
    }

    public TreeController getTreeController() {
        return treeController;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public AbilitySelectionController getAbilitySelectionController() {
        return abilitySelectionController;
    }

    public GameLauncher getView() {
        return view;
    }

    public void setView(GameLauncher view) {
        this.view = view;
    }

    public CheatController getCheatController() {
        return cheatController;
    }

    public PauseController getPauseController() {
        return pauseController;
    }

    public EndGameController getEndGameController() {
        return endGameController;
    }

    public void activeShrinkBarrier(){
        shrinkingBarrier = new ShrinkingBarrier(Main.WORLD_WIDTH,Main.WORLD_HEIGHT);
    }

    public ShrinkingBarrier getShrinkingBarrier() {
        return shrinkingBarrier;
    }

    public void setShrinkingBarrier(ShrinkingBarrier barrier) {
        shrinkingBarrier = barrier;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
}
