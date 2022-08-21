package sunmade.jcircuit.element;

import javafx.scene.image.Image;
import sunmade.jcircuit.linker.PinLink;
import sunmade.jcircuit.element.pin.Pin;

import java.util.*;

public abstract class Element extends Entity implements Conductible {
    protected final List<Pin> pins = new ArrayList<>();

    public Element(String name, Image image) {
        super(name, image);
    }

    public void addPin(Pin pin) {
        if (pin == null) throw new NullPointerException();

        for (Pin listPin : pins) {
            if (listPin.getId().equals(pin.getId())) return;
            if (listPin.getRelativeX() == pin.getRelativeX() & listPin.getRelativeY() == pin.getRelativeY()) return;
        }

        double distanceX = getImage().getWidth() / 2.0;
        double distanceY = getImage().getHeight() / 2.0;
        if ((pin.getRelativeX() < -distanceX | pin.getRelativeX() > distanceX) |
                (pin.getRelativeY() < -distanceY | pin.getRelativeY() > distanceY)
        ) throw new IllegalArgumentException();

        pins.add(pin);
    }

    public void removePin(Pin pin) {
        pins.remove(Objects.requireNonNull(pin));
    }

    public Optional<Pin> getPin(String id) {
        if (id == null) throw new NullPointerException();
        for (Pin pin : pins) {
            if (pin.getId().equals(id)) return Optional.of(pin);
        }
        return Optional.empty();
    }

    public Optional<Pin> getPin(double x, double y) {
        for (Pin pin : pins) {
            if (getX() + pin.getRelativeX() == x & getY() + pin.getRelativeY() == y) {
                return Optional.of(pin);
            }
        }
        return Optional.empty();
    }

    public List<PinLink> getPinIntersections(Element element) {
        if (element == null) throw new NullPointerException();

        List<PinLink> intersections = new ArrayList<>();

        for (Pin firstPin : pins) {
            Optional<Pin> pinOptional = element.getPin(
                    getX() + firstPin.getRelativeX(),
                    getY() + firstPin.getRelativeY()
            );
            pinOptional.ifPresent(secondPin -> intersections.add(new PinLink(firstPin, secondPin)));
        }

        return intersections;
    }

    public boolean hasPin(Pin pin) {
        return pins.contains(Objects.requireNonNull(pin));
    }

    public List<Pin> getPins() {
        return new ArrayList<>(pins);
    }
}

