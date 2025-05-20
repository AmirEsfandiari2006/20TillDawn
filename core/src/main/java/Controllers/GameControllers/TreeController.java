package Controllers.GameControllers;

import Models.GameAssetManager;
import Models.Tree;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class TreeController {

    private final ArrayList<Tree> trees;
    private final int NUMBER_OF_TREES = 10;


    public TreeController() {
        this.trees = new ArrayList<>();
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
}
