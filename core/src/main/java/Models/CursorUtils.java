package Models;

import java.awt.*;

public class CursorUtils {
    private Robot robot;

    public CursorUtils() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void moveCursorToScreen(int screenX, int screenY) {
        if (robot != null) {
            robot.mouseMove(screenX, screenY);
        }
    }
}
