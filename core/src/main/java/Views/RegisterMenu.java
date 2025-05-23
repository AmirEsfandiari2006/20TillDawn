package Views;

import Controllers.RegisterMenuController;
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

public class RegisterMenu implements Screen {

    private final RegisterMenuController controller;
    private Stage stage;

    private final Label titleLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField emailField;

    private final TextButton registerButton;
    private final TextButton signInButton;




    private Table table;


    public RegisterMenu(RegisterMenuController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);

        //Set Up labels
        titleLabel = new Label("Register Menu", skin,"title");

        //Set Up fields
        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        emailField = new TextField("", skin);

        //Set Up buttons
        registerButton = new TextButton("Register", skin);
        signInButton = new TextButton("Sign in", skin);

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        // Text fields
        usernameField.setMessageText("Enter username");

        passwordField.setMessageText("Enter Password");
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        emailField.setMessageText("Enter email");




        // Table layout
        table = new Table();
        table.setFillParent(true);
        table.center();

        Texture bgTexture = new Texture(Gdx.files.internal("background.png"));
        table.setBackground(new TextureRegionDrawable(new TextureRegion(bgTexture)));

        table.add(titleLabel).colspan(2).padBottom(20);
        table.row();
        table.add(usernameField).colspan(2).width(320).pad(5);
        table.row();
        table.add(passwordField).colspan(2).width(320).pad(5);
        table.row();
        table.add(emailField).colspan(2).width(320).pad(5);
        table.row();
        table.add(registerButton).width(300).pad(10);
        table.add(signInButton).width(300).pad(10);

        stage.addActor(table);

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleRegisterButtonClicked();
            }
        });

        signInButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSignInButtonClicked();
            }
        });


    }

    @Override
    public void render(float v) {
        Gdx.input.setInputProcessor(stage);
        ScreenUtils.clear(Color.BLACK);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextButton getRegisterButton() {
        return registerButton;
    }

    public TextButton getSignInButton() {
        return signInButton;
    }

    public Stage getStage() {
        return stage;
    }
}
