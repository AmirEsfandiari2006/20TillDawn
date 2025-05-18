package Models;

import Models.enums.Hero;
import Models.enums.Weapon;




public class Player {

    private final Game game;
    private final User user;
    private final Hero hero;
    private final Weapon weapon;
    private int score;

    public Player(Game game,User user, Hero hero,Weapon weapon, int score) {
        this.game = game;
        this.user = user;
        this.hero = hero;
        this.weapon = weapon;
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public Hero getHero() {
        return hero;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
