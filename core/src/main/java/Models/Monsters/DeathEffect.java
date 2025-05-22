package Models.Monsters;

import Models.GameAssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DeathEffect {
    private final Animation<TextureRegion> animation;
    private float x, y;
    private float stateTime = 0f;
    private boolean finished = false;

    public DeathEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.animation = GameAssetManager.getInstance().getDeathFX(); // e.g. explosion or FX
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        if (animation.isAnimationFinished(stateTime)) {
            finished = true;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion frame = animation.getKeyFrame(stateTime, false);
        batch.draw(frame, x, y);
    }

    public boolean isFinished() {
        return finished;
    }
}
