package sunmade.jcircuit.element.pin;

import java.util.Objects;

public class Pin {
    private final String id;
    private Current current;
    private double relativeX;
    private double relativeY;

    public Pin(String id, double relativeX, double relativeY) {
        this.id = Objects.requireNonNull(id);
        current = Current.ABSENT;
        setRelativeX(relativeX);
        setRelativeY(relativeY);
    }

    public Pin(String id) {
        this(id, 0, 0);
    }

    public String getId() {
        return id;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = Objects.requireNonNull(current);
    }

    public double getRelativeX() {
        return relativeX;
    }

    public void setRelativeX(double relativeX) {
        this.relativeX = relativeX;
    }

    public double getRelativeY() {
        return relativeY;
    }

    public void setRelativeY(double relativeY) {
        this.relativeY = relativeY;
    }
}
