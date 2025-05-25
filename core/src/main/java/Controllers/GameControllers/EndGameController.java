package Controllers.GameControllers;

import Controllers.MainMenuController;
import Models.App;
import Models.GameAssetManager;
import Models.Player;
import Models.enums.GameState;
import Views.MainMenu;
import com.Final.Main;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EndGameController {

    private final GameController gameController;

    public EndGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void checkGameEnded() {
        boolean isVictory = false;
        Player player = gameController.getPlayer();
        if(gameController.getView().getElapsedTime() > (gameController.getSelectedTime() * 60)) {
            gameController.setGameState(GameState.END_GAME);
            GameAssetManager.getInstance().playSFX("winning");
            showEndScreen(gameController.getView().getStage(), "Glory is Yours", true);
            isVictory = true;
        }
        if(player.getCurrentHealth() <= 0) {
            gameController.setGameState(GameState.END_GAME);
            GameAssetManager.getInstance().playSFX("losing");
            showEndScreen(gameController.getView().getStage(), "You Died!", false);
            isVictory = false;
        }

        if(gameController.getGameState() == GameState.END_GAME) {
            if(!App.getInstance().getCurrentUser().getUsername().equals("Guest")) saveData(isVictory);
        }
    }

    public void showEndScreen(Stage stage,String title, boolean isVictory) {
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        int survivedTime = isVictory ? gameController.getSelectedTime() * 60 : (int)gameController.getView().getElapsedTime();
        String time = showTime(survivedTime);


        Label titleLabel = new Label(title, GameAssetManager.getInstance().getSkin(), "title");
        Label message = new Label("Username: " + App.getInstance().getCurrentUser().getUsername(), GameAssetManager.getInstance().getSkin());
        Label survivalTime = new Label("Survived Time: " + time , GameAssetManager.getInstance().getSkin());
        Label kills = new Label("Number Of Kills: " + gameController.getPlayer().getKills(), GameAssetManager.getInstance().getSkin());
        Label score = new Label("Score: " + gameController.getPlayer().getKills() * survivedTime, GameAssetManager.getInstance().getSkin());
        TextButton quit = new TextButton("Quit", GameAssetManager.getInstance().getSkin());


        quit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
            }
        });

        configureTile(titleLabel, isVictory);

        table.add(titleLabel).pad(10).row();
        table.add(message).pad(10).row();
        table.add(survivalTime).pad(10).row();
        table.add(kills).pad(10).row();
        table.add(score).pad(10).row();

        table.add(quit).pad(10);

        stage.addActor(table);
    }

    private void configureTile(Label titleLabel, boolean isVictory) {
        if(isVictory) titleLabel.setColor(Color.GREEN); else titleLabel.setColor(Color.RED);
    }

    private String showTime(int elapsedTime) {
        int minutes =  elapsedTime/ 60;
        int seconds =  elapsedTime % 60;
        return (minutes  + ":" + ((seconds >= 10) ? String.valueOf(seconds) : "0" + seconds));
    }

    private void saveData(boolean isVictory) {
        int survivedTime = isVictory ? gameController.getSelectedTime() * 60 : (int)gameController.getView().getElapsedTime();
        App.getInstance().getCurrentUser().increaseKills(gameController.getPlayer().getKills());
        App.getInstance().getCurrentUser().increaseScore(survivedTime * gameController.getPlayer().getKills());
        App.getInstance().getCurrentUser().setMaxSurviveTime(survivedTime);
    }


}
