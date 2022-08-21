package sunmade.jcircuit.linker;

import sunmade.jcircuit.element.pin.Pin;

import java.util.Objects;
import java.util.Optional;

public class PinLink {
    private final Pin firstPin;
    private final Pin secondPin;

    public PinLink(Pin firstPin, Pin secondPin) {
        if (firstPin == null | secondPin == null) throw new NullPointerException();
        if (firstPin.getId().equals(secondPin.getId())) throw new IllegalArgumentException();

        this.firstPin = firstPin;
        this.secondPin = secondPin;
    }

    public Optional<Pin> getLinkedPin(Pin pin) {
        if (pin == null) throw new NullPointerException();

        if (firstPin == pin) return Optional.of(secondPin);
        if (secondPin == pin) return Optional.of(firstPin);

        return Optional.empty();
    }

    public Pin getFirstPin() {
        return firstPin;
    }

    public Pin getSecondPin() {
        return secondPin;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PinLink)) return false;
        PinLink pinLink = (PinLink) object;
        return Objects.equals(firstPin, pinLink.firstPin) && Objects.equals(secondPin, pinLink.secondPin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPin, secondPin);
    }
}