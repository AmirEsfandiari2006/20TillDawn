package Models;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

public class Utils {

    public static void showErrorDialog(Stage stage, String title, String message) {
    Dialog errorDialog = new Dialog(title, GameAssetManager.getInstance().getSkin()) {
        @Override
        protected void result(Object object) {
            System.out.println("OK clicked");
            this.hide();
            GameAssetManager.getInstance().playSFX("uiclick");
        }
    };
    errorDialog.text(message);
    errorDialog.setSize(800, 600);
    errorDialog.button("OK", true);
    errorDialog.show(stage);
    }

}
