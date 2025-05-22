package Models.Effects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class Explosion {
    private final Animation<TextureRegion> animation;
    private float stateTime = 0f;
    private final float x, y;
    private boolean finished = false;

    public Explosion(Animation<TextureRegion> animation, float x, float y) {
        this.animation = animation;
        this.x = x;
        this.y = y;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        if (animation.isAnimationFinished(stateTime)) {
            finished = true;
        }
    }

    public void render(SpriteBatch batch) {
        if (!finished) {
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
            batch.draw(currentFrame, x, y);
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
