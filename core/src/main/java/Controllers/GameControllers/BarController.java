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
    private final BitmapFont font;
    private final Texture barTexture = new Texture(Gdx.files.internal("GameBar.png"));


    public BarController() {
        font = getBitmapFont();
        font.setColor(Color.WHITE);
    }

    private BitmapFont getBitmapFont() {
        final BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/LiberationSans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14;
        font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera, int score, int kills) {
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
        font.draw(batch, "Score: " + score, labelX + 50, labelY  - 2);
        font.draw(batch, "Kills: " + kills, labelX  + 50, labelY - 22);
    }


    public void dispose() {
        font.dispose();
    }
}
