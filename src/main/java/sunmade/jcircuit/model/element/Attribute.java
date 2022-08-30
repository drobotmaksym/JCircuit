package sunmade.jcircuit.model.element;

import java.util.Objects;

public class Attribute<T> {
    private final String name;
    private final T defaultValue;
    private T value;

    public Attribute(String name, T defaultValue) {
        this.name = Objects.requireNonNull(name);
        this.defaultValue = Objects.requireNonNull(defaultValue);
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
