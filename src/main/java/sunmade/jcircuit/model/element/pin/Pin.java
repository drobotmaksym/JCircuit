package sunmade.jcircuit.model.element.pin;

import java.util.Objects;

public class Pin {
    private final String identifier;
    private Voltage voltage = Voltage.ZERO;
    private int relativeX;
    private int relativeY;

    public Pin(String identifier) {
        this.identifier = Objects.requireNonNull(identifier);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Pin)) return false;
        Pin pin = (Pin) object;
        return Objects.equals(identifier, pin.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    public String getIdentifier() {
        return identifier;
    }

    public Voltage getVoltage() {
        return voltage;
    }

    public void setVoltage(Voltage voltage) {
        this.voltage = Objects.requireNonNull(voltage);
    }

    public int getRelativeX() {
        return relativeX;
    }

    public void setRelativeX(int relativeX) {
        this.relativeX = relativeX;
    }

    public int getRelativeY() {
        return relativeY;
    }

    public void setRelativeY(int relativeY) {
        this.relativeY = relativeY;
    }
}
