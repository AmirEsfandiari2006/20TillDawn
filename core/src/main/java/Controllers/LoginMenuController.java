package Controllers;

import Models.App;
import Models.GameAssetManager;
import Models.User;
import Models.Utils;
import Views.ForgetPasswordMenu;
import Views.LoginMenu;
import Views.MainMenu;
import Views.RegisterMenu;
import com.Final.Main;
import Controllers.ForgetPasswordController;


public class LoginMenuController {
    private LoginMenu view;

    public void setView(LoginMenu view) { this.view = view; }


    public void handleLoginUser() {
        if (!isUsernameExists()) {
            Utils.showErrorDialog(view.getStage(),"Error","Username is Invalid.");
            reset();
            return;
        } else if (!isPasswordCorrect()) {
            Utils.showErrorDialog(view.getStage(),"Error","Password is incorrect.");
            reset();
            return;
        }
        App.getInstance().setCurrentUser(findUsername(view.getUsernameField().getText()));
        Utils.showErrorDialog(view.getStage(), "Info",  "Login as User, Username: " + view.getUsernameField().getText());
        goToMainMenu();

    }



    public void goToMainMenu() {
        changeToMainMenu();
    }

    public void loginAsGuest() {
        App.getInstance().setCurrentUser(new User("Guest", null, "Guest@gmail.com"));
        goToMainMenu();
    }



    public boolean isUsernameExists() {
        for (User user : App.getInstance().getUsers()) {
            if (view.getUsernameField().getText().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean isPasswordCorrect() {
        for (User user : App.getInstance().getUsers()) {
            if (view.getUsernameField().getText().equals(user.getUsername())) {
                if (view.getPasswordField().getText().equals(user.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

    public User findUsername(String username) {
        for (User user : App.getInstance().getUsers()) if (username.equals(user.getUsername())) return user;
        assert false : "Invalid username";
        return null;
    }

    public void changeToMainMenu() {
        Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void reset(){
        view.getUsernameField().setText("");
        view.getPasswordField().setText("");
    }

    public void handleSignUpButton() {
        Main.getMain().setScreen(new RegisterMenu(new RegisterMenuController(), GameAssetManager.getInstance().getSkin()));
    }


    public void forgetPass() {
        Main.getMain().setScreen(new ForgetPasswordMenu(new ForgetPasswordController(),GameAssetManager.getInstance().getSkin()));
    }
}
