package sunmade.jcircuit.model.linker;

import sunmade.jcircuit.model.element.pin.Pin;

import java.util.HashSet;
import java.util.Set;

public class ElementLink {
    private final Set<Link<Pin>> pins = new HashSet<>();

    public void addPinLink(Link<Pin> pinLink) {

    }

    public void removePinLink(Link<Pin> pinLink) {

    }

    public Set<Link<Pin>> getPins() {
        return pins;
    }
}
