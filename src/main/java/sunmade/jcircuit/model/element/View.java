package sunmade.jcircuit.model.element;

import javafx.scene.image.Image;

import java.util.Objects;

public abstract class View {
    private final String name;
    private final Image image;
    private int x;
    private int y;

    public View(String name, Image image) {
        this.name = Objects.requireNonNull(name);
        this.image = Objects.requireNonNull(image);
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public abstract View getCopy();
}
