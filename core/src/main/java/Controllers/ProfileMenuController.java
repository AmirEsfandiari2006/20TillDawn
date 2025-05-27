package Controllers;

import Models.App;
import Models.FilePicker;
import Models.GameAssetManager;
import Models.Utils;
import Views.LoginMenu;
import Views.MainMenu;
import Views.ProfileMenu;
import com.Final.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;

public class ProfileMenuController {
    private ProfileMenu view;

    private final FilePicker filePicker;

    public ProfileMenuController(FilePicker filePicker) {
        this.filePicker = filePicker;
    }


    public void setView(ProfileMenu view) {
        this.view = view;
    }

    public void handleUsernameChange() {
        view.getNewUsernameField().setVisible(!view.getNewUsernameField().isVisible());
        view.getUsernameChangeButton().setVisible(!view.getUsernameChangeButton().isVisible());
    }

    public void handlePasswordChange() {
        view.getNewPasswordField().setVisible(!view.getNewPasswordField().isVisible());
        view.getPasswordChangeButton().setVisible(!view.getPasswordChangeButton().isVisible());
    }

    public void handleDeleteAccount() {
        App.getInstance().getUsers().removeIf(user -> user.getUsername().equals(App.getInstance().getCurrentUser().getUsername()));
        App.getInstance().setCurrentUser(null);
        //Send user to login
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController() , GameAssetManager.getInstance().getSkin()));
    }

    public void handleBack() {
        Main.getMain().setScreen(new MainMenu(new MainMenuController() , GameAssetManager.getInstance().getSkin()));
    }

    public void handleAvatarChange(int avatarIndex) {
        App.getInstance().getCurrentUser().setAvatarIndex(String.valueOf(avatarIndex));
        Utils.showErrorDialog(view.getStage(),"Info" , "Your avatar changed.");
    }

    public void changeUsername() {
        if (!validUsername()) {
            Utils.showErrorDialog(view.getStage(), "Error" , "Username is empty or taken.");
            view.getNewUsernameField().setText("");
            return;
        }
        App.getInstance().getCurrentUser().setUsername(view.getNewUsernameField().getText());
        view.getNewUsernameField().setText("");
        handleUsernameChange();
        Utils.showErrorDialog(view.getStage(), "Info" , "Your username changed successfully.");
    }


    public void changePassword() {
        if (!validPassword()) {
            Utils.showErrorDialog(view.getStage(), "Error" , "Password doesn't meet the requirements.");
            view.getNewPasswordField().setText("");
            return;
        }
        App.getInstance().getCurrentUser().setPassword(view.getNewPasswordField().getText());
        view.getNewPasswordField().setText("");
        handlePasswordChange();
        Utils.showErrorDialog(view.getStage(), "Info" , "Your password changed successfully.");
    }

    public boolean validUsername() {
        String name = view.getNewUsernameField().getText();
        if (name.isEmpty()) return false;
        if (name.equals(App.getInstance().getCurrentUser().getUsername())) return false;
        return App.getInstance().getUsers().stream().noneMatch(user -> user.getUsername().equals(name));
    }


    public boolean validPassword() {
        String correctRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@%$#&*()_]).{8,}$";
        if (view.getNewPasswordField().getText().equals(App.getInstance().getCurrentUser().getPassword())) return false;
        return view.getNewPasswordField().getText().matches(correctRegex);
    }
    public void handleCustomAvatarUpload() {
        filePicker.chooseImage(fileHandle -> {
            if (fileHandle != null && fileHandle.exists()) {
                File uploadsDir = new File("data/uploadedImage");
                if (!uploadsDir.exists()) uploadsDir.mkdirs();

                // Find the next available filename
                int index = 1;
                File destFile;
                do {
                    destFile = new File(uploadsDir, "u" + index + ".png");
                    index++;
                } while (destFile.exists());

                try {
                    // Copy the uploaded image to the destination
                    FileHandle destHandle = Gdx.files.absolute(destFile.getAbsolutePath());
                    destHandle.writeBytes(fileHandle.readBytes(), false);

                    // Set the avatar index WITHOUT the .png extension (e.g., "u3")
                    String avatarNameWithoutExtension = destFile.getName().replace(".png", "");
                    App.getInstance().getCurrentUser().setAvatarIndex(avatarNameWithoutExtension);

                } catch (Exception e) {
                    e.printStackTrace();
                    // Optionally show an error to the user
                }
            }
        });
    }



}
