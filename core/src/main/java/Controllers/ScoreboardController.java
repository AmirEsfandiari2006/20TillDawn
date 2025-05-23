package Controllers;

import Models.App;
import Models.GameAssetManager;
import Models.User;
import Views.MainMenu;
import Views.ScoreboardMenu;
import com.Final.Main;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.Comparator;
import java.util.List;

public class ScoreboardController {

    private ScoreboardMenu view;

    public void setView(ScoreboardMenu view) { this.view = view; }

    public void handleBackToMainMenu() {
        Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void displayUsers(List<User> users, Skin skin, Table table) {
        User currentUser = App.getInstance().getCurrentUser(); // Assuming you track this
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            Color rowColor = Color.WHITE;

            // Top 3 users coloring
            if (i == 0) rowColor = Color.GOLD;
            else if (i == 1) rowColor = Color.LIGHT_GRAY;
            else if (i == 2) rowColor = new Color(205/255f, 127/255f, 50/255f, 1); // bronze-like

            // If current user, override color
            if (currentUser != null && currentUser.getUsername().equals(user.getUsername())) {
                rowColor = Color.CYAN;
            }

            Label usernameLabel = new Label(user.getUsername(), skin);
            usernameLabel.setColor(rowColor);
            table.add(usernameLabel).pad(10);

            Label scoreLabel = new Label(String.valueOf(user.getScore()), skin);
            scoreLabel.setColor(rowColor);
            table.add(scoreLabel).pad(10);

            Label killsLabel = new Label(String.valueOf(user.getKills()), skin);
            killsLabel.setColor(rowColor);
            table.add(killsLabel).pad(10);

            Label timeLabel = new Label(String.valueOf(user.getMaxSurviveTime()), skin);
            timeLabel.setColor(rowColor);
            table.add(timeLabel).pad(10);

            table.row();
        }
    }


    public void sortBy(String field) {
        switch (field) {
            case "username":
                App.getInstance().getUsers().sort(Comparator.comparing(User::getUsername));
                break;
            case "score":
                App.getInstance().getUsers().sort(Comparator.comparing(User::getScore).reversed());
                break;
            case "kills":
                App.getInstance().getUsers().sort(Comparator.comparing(User::getKills).reversed());
                break;
            case "maxTime":
                App.getInstance().getUsers().sort(Comparator.comparing(User::getMaxSurviveTime).reversed());
                break;
        }
        // Re-show the menu
        Main.getMain().setScreen(new ScoreboardMenu(this, GameAssetManager.getInstance().getSkin()));
    }

}
