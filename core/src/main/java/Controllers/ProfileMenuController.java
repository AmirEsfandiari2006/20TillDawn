package Controllers;

import Models.App;
import Models.GameAssetManager;
import Models.Utils;
import Views.LoginMenu;
import Views.MainMenu;
import Views.ProfileMenu;
import com.Final.Main;

public class ProfileMenuController {
    private ProfileMenu view;

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
        App.getInstance().getCurrentUser().setAvatarIndex(avatarIndex);
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



}
