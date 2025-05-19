package Controllers.GameControllers;

import Models.Player;
import Views.GameLauncher;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameController {
    private GameLauncher view;

    private PlayerController playerController;
    private WorldController worldController;

    public void setView(GameLauncher view) {
        this.view = view;
        this.playerController = new PlayerController(new Player());
        this.worldController = new WorldController(playerController);

    }

    public void updateGame() {
        worldController.update();
        playerController.update();
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WorldController getWorldController() {
        return worldController;
    }
}
