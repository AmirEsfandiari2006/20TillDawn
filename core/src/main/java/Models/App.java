package Models;

import java.util.ArrayList;

public class App {

    private Setting setting = new Setting();


    private static App instance;

    private App() {

    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    private ArrayList<User> users = new ArrayList<>();
    private User currentUser;



    public ArrayList<User> getUsers() {
        return users;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
}
