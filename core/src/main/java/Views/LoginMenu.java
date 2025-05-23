package Views;

import Controllers.LoginMenuController;
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

public class LoginMenu implements Screen {

    private final LoginMenuController controller;
    private Stage stage;

    private final Label titleLabel;
    private final TextField usernameField;
    private final TextField passwordField;

    private final TextButton loginButton;
    private final TextButton guestButton;
    private final TextButton forgetPassButton;
    private final TextButton signUpButton;

    private Table table;

    public LoginMenu(LoginMenuController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);

        titleLabel = new Label("Login Menu", skin,"title");

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        loginButton = new TextButton("Login", skin);
        guestButton = new TextButton("Login as Guest", skin);
        forgetPassButton = new TextButton("Forget Pass", skin);
        signUpButton = new TextButton("Sign Up", skin);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        usernameField.setMessageText("Enter Username");
        passwordField.setMessageText("Enter Password");

        table = new Table();
        table.setFillParent(true);
        table.center();

        // Load and set background image
        Texture bgTexture = new Texture(Gdx.files.internal("background.png"));
        table.setBackground(new TextureRegionDrawable(new TextureRegion(bgTexture)));

        titleLabel.setScale(3f);

        table.add(titleLabel).colspan(2).padBottom(20);
        table.row();
        table.add(usernameField).colspan(2).width(480).pad(5);
        table.row();
        table.add(passwordField).colspan(2).width(480).pad(5);
        table.row();
        table.add(loginButton).colspan(2).width(300).pad(10);
        table.row();
        table.add(guestButton).colspan(2).width(480).pad(10);
        table.row();
        table.add(forgetPassButton).width(400).pad(10);
        table.add(signUpButton).width(400).pad(10);

        stage.addActor(table);

        signUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSignUpButton();
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleLoginUser();
            }
        });

        guestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.loginAsGuest();
            }
        });

        forgetPassButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.forgetPass();
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

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getGuestButton() {
        return guestButton;
    }

    public TextButton getForgetPassButton() {
        return forgetPassButton;
    }

    public TextButton getSignUpButton() {
        return signUpButton;
    }

    public Stage getStage() {
        return stage;
    }
}
