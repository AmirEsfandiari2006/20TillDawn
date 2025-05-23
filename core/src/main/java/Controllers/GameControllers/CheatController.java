package Controllers.GameControllers;

import com.badlogic.gdx.Game;

public class CheatController {

    private final GameController gameController;

    public CheatController(GameController gameController) {
        this.gameController = gameController;
    }

    public void decreaseTime() {
        gameController.getView().setElapsedTime(gameController.getView().getElapsedTime() + 60);
    }

    public void increaseLevel() {
        gameController.getPlayer().increaseLevel();
    }

    public void fullHealth(){
        gameController.getPlayer().setCurrentHealth(gameController.getPlayer().getFullHealth());
    }

    public void unlimitedAmmo(){
        gameController.getPlayer().getWeapon().setCurrentAmmo(Integer.MAX_VALUE);
    }

}
