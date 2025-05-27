package Views;

import Controllers.MainMenuController;
import Models.App;
import Models.GameAssetManager;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenu implements Screen {

    private final MainMenuController controller;
    private Stage stage;

    private final Image avatarImage;

    private final Label userInfoLabel;
    private final Label mainMenuLabel;

    private final TextButton preGameButton;
    private final TextButton loadGameButton;
    private final TextButton talentButton;
    private final TextButton scoreBoardButton;
    private final TextButton profileButton;
    private final TextButton settingButton;
    private final TextButton logoutButton;

    private Table table;

    public MainMenu(MainMenuController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);

        if(App.getInstance().getCurrentUser().getUsername().equals("Guest")){
            avatarImage = new Image(new Texture(Gdx.files.internal("Avatars/Avatar6.png")));
        } else {
            avatarImage = loadAvatarImage(App.getInstance().getCurrentUser().getAvatarIndex()) ;
        }
        // User info section
        userInfoLabel = new Label("Username: " + App.getInstance().getCurrentUser().getUsername() +
            "\nEmail: " +  App.getInstance().getCurrentUser().getEmailAddress() + "\nScore: " +
            App.getInstance().getCurrentUser().getScore(), skin);

        // Divider
        mainMenuLabel = new Label("Main Menu", skin,"title");
        // Menu buttons
        preGameButton = new TextButton("PreGame", skin);
        loadGameButton = new TextButton("Load Game", skin);
        talentButton = new TextButton("Talent", skin);
        scoreBoardButton = new TextButton("Score Board", skin);
        profileButton = new TextButton("Profile", skin);
        settingButton = new TextButton("Setting", skin);
        logoutButton = new TextButton("Logout", skin);
    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Font scaling
        userInfoLabel.setFontScale(1.2f);

        // Load background
        Texture bgTexture = new Texture(Gdx.files.internal("background.png"));
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        // === USER INFO TABLE (Top-Left) ===
        Table userInfoTable = new Table();
        userInfoTable.top().left().pad(20);
        userInfoTable.setFillParent(true);
        userInfoTable.add(avatarImage).size(100,100);
        userInfoTable.add(userInfoLabel).left().top();

        // === MAIN MENU TABLE (Center) ===
        Table menuTable = new Table();
        menuTable.center();
        menuTable.setFillParent(true);

        Label divider = new Label("", GameAssetManager.getInstance().getSkin());
        divider.setColor(Color.WHITE);

        menuTable.add(mainMenuLabel).padBottom(20);
        menuTable.row();
        menuTable.add(preGameButton).width(300).pad(5);
        menuTable.row();
        menuTable.add(loadGameButton).width(300).pad(5);
        menuTable.row();
        menuTable.add(talentButton).width(300).pad(5);
        menuTable.row();
        menuTable.add(scoreBoardButton).width(300).pad(5);
        menuTable.row();
        menuTable.add(profileButton).width(300).pad(5);
        menuTable.row();
        menuTable.add(settingButton).width(300).pad(5);
        menuTable.row();
        menuTable.add(divider).width(400).height(2).pad(10);

        Table lougoutTable = new Table();
        lougoutTable.setFillParent(true);
        lougoutTable.bottom().left().pad(40);
        lougoutTable.add(logoutButton).width(300).pad(20);

        // Add both tables to stage
        stage.addActor(userInfoTable);
        stage.addActor(menuTable);
        stage.addActor(lougoutTable);

        // Listeners
        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleLogout();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        preGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handlePreGame();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleProfileButton();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        scoreBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleScoreboardButton();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSettingButton();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        talentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleTalentButton();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        loadGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.loadGame();
                GameAssetManager.getInstance().playSFX("uiclick");
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
    public TextButton getPreGameButton() {
        return preGameButton;
    }

    public TextButton getLoadGameButton() {
        return loadGameButton;
    }

    public TextButton getTalentButton() {
        return talentButton;
    }

    public TextButton getScoreBoardButton() {
        return scoreBoardButton;
    }

    public TextButton getProfileButton() {
        return profileButton;
    }

    public TextButton getSettingButton() {
        return settingButton;
    }

    public TextButton getLogoutButton() {
        return logoutButton;
    }

    public Stage getStage() {
        return stage;
    }

    public static Image loadAvatarImage(String avatarIndex) {
        // Try to load from default Avatars folder
        FileHandle internalHandle = Gdx.files.internal("Avatars/Avatar" + avatarIndex + ".png");
        if (internalHandle.exists()) {
            return new Image(new Texture(internalHandle));
        }

        // If not found, try from uploadedImages folder (external)
        FileHandle uploadedHandle = Gdx.files.local("data/uploadedImage/" + avatarIndex + ".png");
        if (uploadedHandle.exists()) {
            return new Image(new Texture(uploadedHandle));
        }

        // Fallback image or null if neither exists
        return new Image(new Texture(Gdx.files.internal("Avatars/Avatar6.png")));
    }
}
