package Models;

public class CollisionRectangle {
    float x,y;
    float width,height;

    public CollisionRectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean hasCollision(CollisionRectangle rectangle) {
        return x < rectangle.x + rectangle.width && y < rectangle.y + rectangle.height && x + width > rectangle.x && y + height > rectangle.y;
    }


    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
