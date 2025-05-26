package Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.Serial;
import java.io.Serializable;

public class Tree implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private transient Sprite treeSprite = new Sprite(new Texture("Tree/tree1.png"));
    private final CollisionRectangle collisionRectangle ;
    private float time = 3;

    public Tree(float x, float y) {
        this.treeSprite.setPosition(x, y);
        this.collisionRectangle = new CollisionRectangle(x, y, treeSprite.getWidth(), treeSprite.getHeight());
    }

    public Sprite getTreeSprite() {
        return treeSprite;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public CollisionRectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public void initGraphic(){
        treeSprite = new Sprite(new Texture("Tree/tree1.png"));
    }
}
