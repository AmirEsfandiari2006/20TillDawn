package Controllers;

import Models.App;
import Models.GameAssetManager;
import Models.User;
import Models.Utils;
import Views.LoginMenu;
import Views.RegisterMenu;
import com.Final.Main;

public class RegisterMenuController {

    private RegisterMenu view;

    public void setView(RegisterMenu view) { this.view = view; }

    public void changeToLoginMenu() {
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController() , GameAssetManager.getInstance().getSkin()));
    }


    public void handleRegisterButtonClicked() {
        if(!validUsername()){
            Utils.showErrorDialog(view.getStage(),"Error", "Invalid Username");
            return;
        } else if(!validPassword()){
            Utils.showErrorDialog(view.getStage(),"Error", "Invalid Password");
            return;
        } else if(!validEmailAddress()){
            Utils.showErrorDialog(view.getStage(),"Error", "Invalid Email Address");
            return;
        }
        App.getInstance().getUsers().add(new User(view.getUsernameField().getText(), view.getPasswordField().getText(), view.getEmailField().getText()));
        changeToLoginMenu();
    }

    public void handleSignInButtonClicked() {
        changeToLoginMenu();
    }

    public boolean validPassword() {
        String correctRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@%$#&*()_]).{8,}$";
        return view.getPasswordField().getText().matches(correctRegex);
    }

    public boolean validUsername() {
        String name = view.getUsernameField().getText();
        if (name.isEmpty()) return false;
        return App.getInstance().getUsers().stream().noneMatch(user -> user.getUsername().equals(name));
    }

    public boolean validEmailAddress() {
        String correctRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return view.getEmailField().getText().matches(correctRegex);
    }

}


