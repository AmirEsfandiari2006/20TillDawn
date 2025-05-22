package Models.Monsters;

import Models.CollisionRectangle;
import Models.Player;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Monster {
    void update(float deltaTime, Player player);
    Sprite getSprite();
    void render(SpriteBatch batch);
    CollisionRectangle getCollisionRectangle();
    int getHealth();
    void setHealth(int health);
    float getX();
    void setX(float x);
    float getY();
    void setY(float y);
    void setPosition(float x, float y);
}
