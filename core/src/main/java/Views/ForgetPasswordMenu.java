package Views;


import Controllers.ForgetPasswordController;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ForgetPasswordMenu implements Screen {

    private final ForgetPasswordController controller;
    private Stage stage;

    private final Label titleLabel;
    private final TextField usernameField;
    private final TextField emailField;
    private final TextButton seePassButton;
    private final TextButton backButton;

    private Table table;

    public ForgetPasswordMenu(ForgetPasswordController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);

        titleLabel = new Label("Forget Password", skin, "title");
        usernameField = new TextField("", skin);
        emailField = new TextField("", skin);
        seePassButton = new TextButton("See Pass", skin);
        backButton = new TextButton("Back", skin);
    }



    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        // Set up text fields
        usernameField.setMessageText("Enter Username");
        emailField.setMessageText("Enter email");

        // Create main table
        table = new Table();
        table.setFillParent(true);
        table.center();

        // Load and set background image
        Texture bgTexture = new Texture(Gdx.files.internal("background.png"));
        table.setBackground(new TextureRegionDrawable(new TextureRegion(bgTexture)));


        // Add components to table
        table.add(titleLabel).colspan(2).padBottom(10).row();
        table.add(usernameField).colspan(2).width(400).pad(10).row();
        table.add(emailField).colspan(2).width(400).pad(10).row();

        // Button row
        Table buttonTable = new Table();
        buttonTable.add(seePassButton).width(240).pad(10);
        buttonTable.add(backButton).width(240).pad(10);
        table.add(buttonTable).colspan(2).padTop(20);

        stage.addActor(table);

        // Add button listeners
        seePassButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSeePassword();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleBack();
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }

    // Getters for controller
    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextButton getSeePassButton() {
        return seePassButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public Stage getStage() {
        return stage;
    }
}
