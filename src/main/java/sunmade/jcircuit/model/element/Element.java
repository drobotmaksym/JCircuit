package sunmade.jcircuit.model.element;

import javafx.scene.image.Image;
import sunmade.jcircuit.model.element.attribute.Attribute;
import sunmade.jcircuit.model.element.pin.Pin;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Element extends View {
    private final Set<Pin> pins = new HashSet<>();
    private final Set<Attribute<?>> attributes = new HashSet<>();
    private final ElementType type;

    public Element(String name, Image image, ElementType type) {
        super(name, image);
        this.type = Objects.requireNonNull(type);
    }

    public Element(String name, Image image) {
        this(name, image, ElementType.Consumer);
    }

    public void addPin(Pin pin) {
        pins.add(Objects.requireNonNull(pin));
    }

    public void removePin(Pin pin) {
        pins.remove(Objects.requireNonNull(pin));
    }

    public Optional<Pin> getPin(int x, int y) {
        for (Pin pin : pins) {
            if (getX() + pin.getRelativeX() == x &
                    getY() + pin.getRelativeY() == y
            ) return Optional.of(pin);
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

    public boolean hasPin(Pin pin) {
        return getPin(Objects.requireNonNull(pin).getIdentifier()).isPresent();
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

    public <T> Set<Attribute<T>> getAttributes(Class<T> valueClass) {
        if (valueClass == null) throw new NullPointerException();
        Set<Attribute<T>> matchingAttributes = new HashSet<>();
        for (Attribute<?> attribute : attributes) {
            getOptionalCast(attribute, valueClass).ifPresent(matchingAttributes::add);
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

    public Set<Attribute<?>> getAttributes() {
        return attributes;
    }

    public ElementType getType() {
        return type;
    }

    @Override
    public Element getClone() {
        Element clone = new Element(getName(), getImage(), type);
        pins.forEach(pin -> clone.pins.add(pin.getClone()));
        attributes.forEach(attribute -> clone.attributes.add(attribute.getClone()));
        return clone;
    }
}