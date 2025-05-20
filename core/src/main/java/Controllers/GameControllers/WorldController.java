package Controllers.GameControllers;

import com.Final.Main;

import com.badlogic.gdx.graphics.Texture;

public class WorldController {


    private final PlayerController playerController;
    private final Texture background;

    public WorldController(PlayerController playerController) {
        this.playerController = playerController;
        this.background = new Texture("map.png");
    }

    public void update() {
        Main.getBatch().draw(background, 0, 0, Main.WORLD_WIDTH, Main.WORLD_HEIGHT);
    }

    public Texture getBackground() {
        return background;
    }
}
