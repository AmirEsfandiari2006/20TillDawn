package Controllers.GameControllers;

import Models.App;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class BarController {
    private final BitmapFont normalFont;
    private final BitmapFont largeFont;
    private final Texture barTexture = new Texture(Gdx.files.internal("GameBar.png"));


    public BarController() {
        normalFont = getBitmapFont(14,Color.WHITE);
        largeFont = getBitmapFont(18,Color.WHITE);
    }

    private BitmapFont getBitmapFont(int size, Color color) {
        final BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/LiberationSans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;
        font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera, int currentHealth, int fullHealth, int kills, int elapsedTime,int totalTime) {
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

    }


    public void dispose() {
        normalFont.dispose();
        largeFont.dispose();
    }
}
