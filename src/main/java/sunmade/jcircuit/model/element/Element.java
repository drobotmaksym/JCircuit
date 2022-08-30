package sunmade.jcircuit.model.element;

import javafx.scene.image.Image;
import org.w3c.dom.Attr;
import sunmade.jcircuit.model.element.attribute.Attribute;
import sunmade.jcircuit.model.element.pin.Pin;

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
        attributes.add(Objects.requireNonNull(attribute));
    }

    public void removeAttribute(Attribute<?> attribute) {
        attributes.remove(Objects.requireNonNull(attribute));
    }

    public Optional<Attribute<?>> getAttribute(String name) {
        if (name == null) throw new NullPointerException();
        for (Attribute<?> attribute : attributes) {
            if (attribute.getName().equals(name)) return Optional.of(attribute);
        }
        return Optional.empty();
    }

    public <T> Optional<Attribute<T>> getAttribute(String name, Class<T> valueClass) {
        if (valueClass == null) throw new NullPointerException();

        Optional<Attribute<?>> optionalAttribute = getAttribute(name);
        if (optionalAttribute.isEmpty()) return Optional.empty();

        return getOptionalCast(optionalAttribute.get(), valueClass);
    }

    public <T> Set<Attribute<T>> getAttributes(Class<T> valueType) {
        if (valueType == null) throw new NullPointerException();
        Set<Attribute<T>> matchingAttributes = new HashSet<>();
        for (Attribute<?> attribute : attributes) {
            getOptionalCast(attribute, valueType).ifPresent(matchingAttributes::add);
        }
        return matchingAttributes;
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<Attribute<T>> getOptionalCast(Attribute<?> attribute, Class<T> valueClass) {
        if (attribute == null | valueClass == null) throw new NullPointerException();
        if (attribute.getValue().getClass() == valueClass) return Optional.of((Attribute<T>) attribute);
        return Optional.empty();
    }

    public Set<Pin> getPins() {
        return pins;
    }

    public Set<Attribute<?>> getAttributes() { return attributes; }
}