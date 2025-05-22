package Controllers.GameControllers;

import Models.CollisionRectangle;
import Models.GameAssetManager;
import Models.Monsters.XpCoin;
import Models.Tree;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

public class TreeController {


    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private final ArrayList<Tree> trees;
    private final OrthographicCamera camera;
    private final int NUMBER_OF_TREES = 10;


    public TreeController(OrthographicCamera camera) {
        this.trees = new ArrayList<>();
        this.camera = camera;
        Random random = new Random();
        for (int i = 0; i < NUMBER_OF_TREES; i++) {
            float x = random.nextFloat() * (Main.WORLD_WIDTH - 50);
            float y = random.nextFloat() * (Main.WORLD_HEIGHT - 50);
            trees.add(new Tree(x, y));
        }
    }

    public void update() {
        for (Tree tree : trees) {
            Sprite sprite = tree.getTreeSprite();
            Main.getBatch().draw(sprite, sprite.getX(), sprite.getY());
            treeAnimation();
        }
    }

    public void treeAnimation() {
        Animation<Texture> animation = GameAssetManager.getInstance().getTreeAnimation();

        for (Tree tree : trees) {

            tree.getTreeSprite().setRegion(animation.getKeyFrame(tree.getTime()));

            if(!animation.isAnimationFinished(tree.getTime())) {
                tree.setTime(tree.getTime() + Gdx.graphics.getDeltaTime());
            } else {
                tree.setTime(0);
            }

            animation.setPlayMode(Animation.PlayMode.LOOP);
        }

    }


    public void drawTreeCollisionBoxes() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN); // Green for XP coins

        for (Tree tree : trees) {
            CollisionRectangle rect = tree.getCollisionRectangle();
            shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }

        shapeRenderer.end();
    }

}
