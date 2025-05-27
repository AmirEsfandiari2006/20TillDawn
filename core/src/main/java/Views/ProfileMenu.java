package Views;

import Controllers.ProfileMenuController;
import Models.GameAssetManager;
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

public class ProfileMenu implements Screen {

    private final ProfileMenuController controller;
    private Stage stage;


    private final TextButton changeUsernameButton;
    private final TextField newUsernameField;
    private final TextButton usernameChangeButton;
    private final TextButton changePasswordButton;
    private final TextField newPasswordField;
    private final TextButton passwordChangeButton;
    private final TextButton deleteAccountButton;
    private final TextButton uploadAvatarButton;
    private final TextButton backButton;

    // Avatar buttons
    private final ImageButton[] avatarButtons = new ImageButton[5];
    private final Texture[] avatarTextures = new Texture[5];

    private Table mainTable;
    private Table avatarTable;

    public ProfileMenu(ProfileMenuController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);


        // Username section
        changeUsernameButton = new TextButton("Change Username", skin);
        newUsernameField = new TextField("", skin);
        newUsernameField.setMessageText("New Username");
        usernameChangeButton = new TextButton("Change", skin);

        // Password section
        changePasswordButton = new TextButton("Change Password", skin);
        newPasswordField = new TextField("", skin);
        newPasswordField.setMessageText("New Password");
        newPasswordField.setPasswordMode(true);
        newPasswordField.setPasswordCharacter('*');
        passwordChangeButton = new TextButton("Change", skin);

        // Other buttons
        deleteAccountButton = new TextButton("Delete Account", skin);
        backButton = new TextButton("Back", skin);

        // Load avatar textures
        for (int i = 0; i < 5; i++) {
            avatarTextures[i] = new Texture(Gdx.files.internal("avatars/avatar" + (i+1) + ".png"));
            avatarButtons[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(avatarTextures[i])));
        }
        this.uploadAvatarButton = new TextButton("Upload Avatar", skin);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Create main table
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        // Load and set background image
        Texture bgTexture = new Texture(Gdx.files.internal("background.png"));
        mainTable.setBackground(new TextureRegionDrawable(new TextureRegion(bgTexture)));

        // Username row
        newUsernameField.setVisible(false);
        usernameChangeButton.setVisible(false);
        mainTable.add(changeUsernameButton).colspan(3).pad(10).row();
        mainTable.add(newUsernameField).width(400);
        mainTable.add(usernameChangeButton).width(200).row();

        // Password row
        newPasswordField.setVisible(false);
        passwordChangeButton.setVisible(false);
        mainTable.add(changePasswordButton).colspan(3).pad(10).row();
        mainTable.add(newPasswordField).width(400).pad(5);
        mainTable.add(passwordChangeButton).pad(5).row();

        // Delete account button
        mainTable.add(deleteAccountButton).colspan(3).pad(20).row();

        // Avatar selection
        avatarTable = new Table();
        for (ImageButton avatarButton : avatarButtons) {
            avatarTable.add(avatarButton).size(64).pad(10);
        }
        mainTable.add(avatarTable).colspan(3).pad(20).row();

        mainTable.add(uploadAvatarButton).colspan(3).pad(10).row();

        // Back button
        mainTable.add(backButton).colspan(3).pad(20);

        stage.addActor(mainTable);

        // Add button listeners
        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleUsernameChange();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handlePasswordChange();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleDeleteAccount();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleBack();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        usernameChangeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeUsername();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });

        passwordChangeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changePassword();
                GameAssetManager.getInstance().playSFX("uiclick");
            }
        });


        // Avatar selection listeners
        for (int i = 0; i < avatarButtons.length; i++) {
            final int avatarIndex = i;
            avatarButtons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.handleAvatarChange(avatarIndex + 1);
                    GameAssetManager.getInstance().playSFX("uiclick");
                }
            });
        }

        uploadAvatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleCustomAvatarUpload();
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
        for (Texture texture : avatarTextures) {
            texture.dispose();
        }
    }

    // Getters for controller
    public TextField getNewUsernameField() {
        return newUsernameField;
    }

    public TextField getNewPasswordField() {
        return newPasswordField;
    }

    public TextButton getChangeUsernameButton() {
        return changeUsernameButton;
    }

    public TextButton getUsernameChangeButton() {
        return usernameChangeButton;
    }

    public TextButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public TextButton getPasswordChangeButton() {
        return passwordChangeButton;
    }

    public TextButton getDeleteAccountButton() {
        return deleteAccountButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public Stage getStage() {
        return stage;
    }
}
