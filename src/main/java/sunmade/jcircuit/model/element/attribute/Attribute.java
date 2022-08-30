package sunmade.jcircuit.model.element.attribute;

import sunmade.jcircuit.model.Cloneable;

import java.util.Objects;

public abstract class Attribute<T> implements Cloneable<Attribute<T>> {
    private final String name;
    private final T defaultValue;
    private T value;

    public Attribute(String name, T defaultValue) {
        this.name = Objects.requireNonNull(name);
        this.defaultValue = Objects.requireNonNull(defaultValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;
        Attribute<?> attribute = (Attribute<?>) o;
        return Objects.equals(name, attribute.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = Objects.requireNonNull(value);
    }
}
