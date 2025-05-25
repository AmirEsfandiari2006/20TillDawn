package Models;

import com.badlogic.gdx.Input;

public class KeySettings {

    public int moveUp = Input.Keys.W;
    public int moveDown = Input.Keys.S;
    public int moveLeft = Input.Keys.A;
    public int moveRight = Input.Keys.D;
    public int shoot = Input.Buttons.LEFT;
    public int reload= Input.Keys.R;

    private static KeySettings instance;

    public int autoAim = Input.Keys.SPACE;
}
