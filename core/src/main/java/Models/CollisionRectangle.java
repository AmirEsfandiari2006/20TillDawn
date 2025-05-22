package Models;

public class CollisionRectangle {
    private float x,y;
    private float width,height;

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


    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }
}
