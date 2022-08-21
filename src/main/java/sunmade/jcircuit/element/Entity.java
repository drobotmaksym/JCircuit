package sunmade.jcircuit.element;

import javafx.scene.image.Image;

import java.util.Objects;

public abstract class Entity {
    private String name;
    private Image image;
    private double x;
    private double y;

    public Entity(String name, Image image) {
        setName(name);
        setImage(image);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = Objects.requireNonNull(image);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}