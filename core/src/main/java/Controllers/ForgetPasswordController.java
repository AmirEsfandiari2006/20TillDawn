package Controllers;



import Models.GameAssetManager;
import Models.Utils;
import Models.User;
import Models.App;
import Views.ForgetPasswordMenu;
import Views.LoginMenu;
import com.Final.Main;

public class ForgetPasswordController {

    private ForgetPasswordMenu view;

    public void setView(ForgetPasswordMenu view) { this.view = view; }

    public void handleSeePassword () {
        if(!isUsernameExists()){
            Utils.showErrorDialog(view.getStage(), "Error", "No such username exists.");
            return;
        } else if(!isEmailAddressCorrect()){
            Utils.showErrorDialog(view.getStage(), "Error", "Invalid Email, Email address does not match.");
            return;
        }
        Utils.showErrorDialog(view.getStage(), "Info",  "Your password is: " + findUsername(view.getUsernameField().getText()).getPassword());

    }

    public void handleBack() {
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController() , GameAssetManager.getInstance().getSkin()));
    }

    public boolean isUsernameExists() {
        for (User user : App.getInstance().getUsers()) {
            if (view.getUsernameField().getText().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmailAddressCorrect() {
        for (User user : App.getInstance().getUsers()) {
            if (view.getUsernameField().getText().equals(user.getUsername())) {
                if (view.getEmailField().getText().equals(user.getEmailAddress())) {
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



}
