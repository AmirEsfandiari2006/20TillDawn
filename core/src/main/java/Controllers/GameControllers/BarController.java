package Controllers.GameControllers;

import Models.App;
import Models.Player;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class BarController {



    private final BitmapFont normalFont;
    private final BitmapFont largeFont;
    private final BitmapFont smallFont;
    private final Texture barTexture = new Texture(Gdx.files.internal("GameBar.png"));


    private final Player player;

    private final Texture xpBarBackground = new Texture(Gdx.files.internal("default.png"));
    private final Texture xpBarFill = new Texture(Gdx.files.internal("fill.png"));

    public BarController(Player player) {
        this.player = player;
        smallFont =getBitmapFont( 10, Color.WHITE);
        normalFont = getBitmapFont(14,Color.WHITE);
        largeFont = getBitmapFont(18,Color.WHITE);
    }

    private BitmapFont getBitmapFont(int size, Color color) {
        final BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/LiberationSans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        parameter.genMipMaps = true;
        parameter.hinting = FreeTypeFontGenerator.Hinting.Full;
        parameter.size = size;
        parameter.color = color;
        font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera, int currentHealth, int fullHealth, int kills, int elapsedTime, int totalTime, int xp, int level, int xpForNextLevel) {
        int labelX = (int)(camera.position.x - 480);
        int labelY = (int)(camera.position.y + 260);
        int barWidth = 1200;
        int barHeight = 50;

        // 1. Draw background first
        batch.draw(barTexture, labelX - 40, labelY - barHeight + 10, barWidth, barHeight); // adjust Y as needed

        Texture avatarImage;
        if(App.getInstance().getCurrentUser().getUsername().equals("Guest")){
            avatarImage = new Texture(Gdx.files.internal("Avatars/Avatar6.png"));
        } else {
            avatarImage = new Texture(Gdx.files.internal("Avatars/Avatar" + App.getInstance().getCurrentUser().getAvatarIndex() + ".png"));
        }

        Main.getBatch().draw(avatarImage, labelX - 10 ,labelY - 40,50,50);

        // 2. Draw the text on top of the background
        normalFont.draw(batch, "Health: " + currentHealth + "/" + fullHealth, labelX + 50, labelY  - 2);
        normalFont.draw(batch, "Kills: " + kills, labelX  + 50, labelY - 22);

        int minutes = ((60 * totalTime) - elapsedTime) / 60;
        int seconds = ((60 * totalTime) - elapsedTime) % 60;
        String time = ( (seconds >= 10) ? String.valueOf(seconds) : "0" + seconds);

        largeFont.draw(batch, "Time:  " + minutes + ":" + time , labelX + 140, labelY - 8);

        // 3. XP Bar rendering
        int xpBarX = labelX + 260;
        int xpBarY = labelY - 35;
        int xpBarWidth = 200;
        int xpBarHeight = 16;

        float xpRatio = (xpForNextLevel == 0) ? 0f : Math.min(1f, (float) xp / xpForNextLevel); // Clamp to 1

        batch.draw(xpBarBackground, xpBarX, xpBarY, xpBarWidth, xpBarHeight);
        batch.draw(xpBarFill, xpBarX, xpBarY, xpBarWidth * xpRatio, xpBarHeight);

        normalFont.draw(batch, "Level: " + level, xpBarX + 80 , xpBarY + 30);
        smallFont.draw(batch, xp + "/" + xpForNextLevel + " XP", xpBarX + 85, xpBarY + 12);

        int relaodX = xpBarX + 227;
        int relaodY = xpBarY;

        Sprite iconSprite = new Sprite(player.getWeapon().getType().getSprite());
        iconSprite.setFlip(false, false);

        batch.draw(iconSprite, relaodX, relaodY, 40, 40);
        largeFont.draw( batch, player.getWeapon().getCurrentAmmo() + "/" + player.getWeapon().getMaxAmmo(), relaodX + 50, labelY - 8);
    }



    public void dispose() {
        smallFont.dispose();
        normalFont.dispose();
        largeFont.dispose();
    }
}
