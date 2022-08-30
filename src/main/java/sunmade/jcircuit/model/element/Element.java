package sunmade.jcircuit.model.element;

import javafx.scene.image.Image;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public abstract class Element extends View implements Conductible {
    private final Set<Pin> pins = new HashSet<>();
    private final Set<Attribute<?>> attributes = new HashSet<>();

    public Element(String name, Image image) {
        super(name, image);
    }

    public void addPin(Pin pin) {
        pins.add(Objects.requireNonNull(pin));
    }

    public void removePin(Pin pin) {
        pins.remove(Objects.requireNonNull(pin));
    }

    public Optional<Pin> getPin(int x, int y) {
        for (Pin pin : pins) {
            if (getX() + pin.getRelativeX() == x & getY() + pin.getRelativeY() == y) return Optional.of(pin);
        }
        return Optional.empty();
    }

    public Optional<Pin> getPin(String identifier) {
        if (identifier == null) throw new NullPointerException();
        for (Pin pin : pins) {
            if (pin.getIdentifier().equals(identifier)) return Optional.of(pin);
        }
        return Optional.empty();
    }

    public void addAttribute(Attribute<?> attribute) {

    }

    public void removeAttribute(Attribute<?> attribute) {

    }

    public Attribute<?> getAttribute(String name) {
        throw new NullPointerException();
    }

    public <T> Attribute<T> getAttribute(String name, T valueType) {
        throw new NullPointerException();
    }

    public <T> Set<Attribute<T>> getAttributes(T valueType) {
        throw new NullPointerException();
    }

    public Set<Pin> getPins() {
        return pins;
    }

    public Set<Attribute<?>> getAttributes() { return attributes; }

    @Override
    public View getCopy() {
        throw new NullPointerException();
    }
}