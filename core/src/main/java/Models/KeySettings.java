package Models;

import com.badlogic.gdx.Input;

public class KeySettings {

    public int moveUp = Input.Keys.W;
    public int moveDown = Input.Keys.S;
    public int moveLeft = Input.Keys.A;
    public int moveRight = Input.Keys.D;


    private static KeySettings instance;

    private KeySettings(){

    }

    public static KeySettings getInstance() {
        if (instance == null) {
            instance = new KeySettings();
        }
        return instance;
    }

}
