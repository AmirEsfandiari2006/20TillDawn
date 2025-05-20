package Models.Monsters;

import Models.Player;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Monster {
    void update(float deltaTime, Player player);
    Sprite getSprite();
    void render(SpriteBatch batch);
}
