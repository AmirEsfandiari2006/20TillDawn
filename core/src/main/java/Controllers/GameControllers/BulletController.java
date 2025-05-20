package Controllers.GameControllers;

import Models.Bullet;

import java.util.ArrayList;

public class BulletController {

    private final ArrayList<Bullet> bullets;

    public BulletController() {
        this.bullets = new ArrayList<>();
    }

    public void update() {

    }

    public void handleShoot(int x, int y) {
        bullets.add(new Bullet(x, y));
    }
}
