package Controllers;

import Models.GameAssetManager;
import Views.LoginMenu;
import Views.StartMenu;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StartMenuController {

    private StartMenu view;

    private float leftThunderTimer = 0f;
    private float rightThunderTimer = 0f;

    private float leftAnimationTime = 0f;
    private float rightAnimationTime = 0f;

    private boolean isLeftVisible = false;
    private boolean isRightVisible = false;

    private final float thunderInterval = 1f;

    private final Animation<TextureRegion> thunderAnimation;

    private TextureRegion currentLeftFrame = null;
    private TextureRegion currentRightFrame = null;

    private float leftX, leftY;
    private float rightX, rightY;

    public StartMenuController() {
        this.thunderAnimation = GameAssetManager.getInstance().getThunderAnimation(); // Thunder animation
    }

    public void setView(StartMenu view) {
        this.view = view;
    }

    public void update(float delta) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float minY = 0;                     // top (y=0)
        float maxY = screenHeight / 2f;     // halfway down

        // LEFT THUNDER
        leftThunderTimer += delta;
        if (!isLeftVisible && leftThunderTimer > thunderInterval + Math.random() * 2) {
            leftThunderTimer = 0;
            leftAnimationTime = 0;
            isLeftVisible = true;

            // X: random in first third | Y: between 0 and half screen height (top half)
            leftX = (float) (Math.random() * (screenWidth / 4 - 200));
            leftY = minY + (float) Math.random() * (maxY - minY);
        }

        if (isLeftVisible) {
            leftAnimationTime += delta;
            currentLeftFrame = thunderAnimation.getKeyFrame(leftAnimationTime, false);
            if (thunderAnimation.isAnimationFinished(leftAnimationTime)) {
                isLeftVisible = false;
                currentLeftFrame = null;
            }
        }

        // RIGHT THUNDER
        rightThunderTimer += delta;
        if (!isRightVisible && rightThunderTimer > thunderInterval + Math.random() * 2) {
            rightThunderTimer = 0;
            rightAnimationTime = 0;
            isRightVisible = true;

            // X: random in last third | Y: between 0 and half screen height (top half)
            rightX = (float) ((screenWidth * 3 / 4) + Math.random() * (screenWidth / 3 - 200));
            rightY = minY + (float) Math.random() * (maxY - minY);
        }

        if (isRightVisible) {
            rightAnimationTime += delta;
            currentRightFrame = thunderAnimation.getKeyFrame(rightAnimationTime, false);
            if (thunderAnimation.isAnimationFinished(rightAnimationTime)) {
                isRightVisible = false;
                currentRightFrame = null;
            }
        }
    }

    public void draw() {
        if (isLeftVisible && currentLeftFrame != null) {
            Main.getBatch().draw(currentLeftFrame, leftX, leftY);
        }

        if (isRightVisible && currentRightFrame != null) {
            Main.getBatch().draw(currentRightFrame, rightX, rightY);
        }
    }

    public void startButton() {
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}
