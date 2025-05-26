package Models;

import Models.Monsters.Monster;
import Models.Monsters.MonsterBullet;
import Models.Monsters.XpCoin;
import Models.enums.CharacterType;
import Models.enums.GameState;

import java.io.*;
import java.util.ArrayList;

public class SaveData implements Serializable {
    private static final long serialVersionUID = 1L;

    private GameState gameState;
    private CharacterType selectedCharacter;
    private Weapon selectedWeapon;
    private int selectedTime;

    private Player player;

    private ArrayList<Monster> monsters;
    private ArrayList<MonsterBullet> monsterBullets;
    private ArrayList<XpCoin> xpCoins;
    private ArrayList<Tree> trees;

    private int elapsedTime;

    // Constructor
    public SaveData(GameState gameState, CharacterType selectedCharacter, Weapon selectedWeapon,
                    int selectedTime, Player player,
                    ArrayList<Monster> monsters, ArrayList<MonsterBullet> monsterBullets,
                    ArrayList<XpCoin> xpCoins, ArrayList<Tree> trees, int elapsedTime) {
        this.gameState = gameState;
        this.selectedCharacter = selectedCharacter;
        this.selectedWeapon = selectedWeapon;
        this.selectedTime = selectedTime;
        this.player = player;
        this.monsters = monsters;
        this.monsterBullets = monsterBullets;
        this.xpCoins = xpCoins;
        this.trees = trees;
        this.elapsedTime = elapsedTime;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public CharacterType getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(CharacterType selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    public int getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(int selectedTime) {
        this.selectedTime = selectedTime;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public ArrayList<MonsterBullet> getMonsterBullets() {
        return monsterBullets;
    }

    public void setMonsterBullets(ArrayList<MonsterBullet> monsterBullets) {
        this.monsterBullets = monsterBullets;
    }

    public ArrayList<XpCoin> getXpCoins() {
        return xpCoins;
    }

    public void setXpCoins(ArrayList<XpCoin> xpCoins) {
        this.xpCoins = xpCoins;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public void setTrees(ArrayList<Tree> trees) {
        this.trees = trees;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }


    public static SaveData loadGame() {
        String username = App.getInstance().getCurrentUser().getUsername();
        String filePath = "data/" + username + ".save";

        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (SaveData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
