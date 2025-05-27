package Controllers;

import Controllers.GameControllers.GameController;
import Models.*;
import Models.Monsters.Monster;
import Models.Monsters.MonsterBullet;
import Models.Monsters.XpCoin;
import Models.enums.GameState;
import Views.*;
import com.Final.Main;
import com.sun.source.util.Trees;

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

    public void loadGame() {
        SaveData saveData = SaveData.loadGame();

        if(App.getInstance().getCurrentUser().getUsername().equals("Guest")) {
            Utils.showErrorDialog(view.getStage(),"Error","You logged in as guest.");
            return;
        }

        if(saveData == null) {
            Utils.showErrorDialog(view.getStage(),"Error","No saved game found.");
            return;
        }

        GameController gameController = new GameController(
            saveData.getSelectedCharacter(),
            saveData.getSelectedWeapon(),
            saveData.getSelectedTime()
        );

        gameController.setGameState(GameState.PLAYING);
        gameController.setPlayer(saveData.getPlayer());
        gameController.setMonsters(saveData.getMonsters());
        gameController.setMonsterBullets(saveData.getMonsterBullets());
        gameController.setXpCoins(saveData.getXpCoins());
        gameController.setTrees(saveData.getTrees());

        gameController.getPlayer().initGraphic();
        for(Monster monster: gameController.getMonsters()) {monster.initGraphic();}
        for(MonsterBullet monsterBullet: gameController.getMonsterBullets()) {monsterBullet.initGraphic();}
        for(XpCoin xpCoin: gameController.getXpCoins()){xpCoin.initGraphic();}

        gameController.getTrees().clear();
        for(Tree trees: gameController.getTrees()) {trees.initGraphic();}

        GameLauncher gameLauncher = new GameLauncher(gameController,GameAssetManager.getInstance().getSkin());

        gameLauncher.setElapsedTime(saveData.getElapsedTime());

        Main.getMain().setScreen(gameLauncher);
    }
}
