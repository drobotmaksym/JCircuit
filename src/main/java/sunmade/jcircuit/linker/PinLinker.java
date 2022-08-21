package sunmade.jcircuit.linker;

import sunmade.jcircuit.element.pin.Pin;

import java.util.*;

public class PinLinker implements Linker<Pin> {
    private final Set<PinLink> links = new HashSet<>();

    @Override
    public void link(Pin firstPin, Pin secondPin) {
        if (firstPin == null | secondPin == null) throw new NullPointerException();
        if (firstPin == secondPin) throw new IllegalArgumentException("Can not link two identical pins");

        links.add(new PinLink(firstPin, secondPin));
    }

    @Override
    public void unlink(Pin pin) {
        getLinks(pin).forEach(links::remove);
    }

    public List<PinLink> getLinks(Pin pin) {
        if (pin == null) throw new NullPointerException();

        List<PinLink> collectedLinks = new ArrayList<>();

        for (PinLink link : links) {
            if (link.getFirstPin() == pin | link.getSecondPin() == pin)
                collectedLinks.add(link);
        }

        return collectedLinks;
    }

    public List<Pin> getLinkedPins(Pin pin) {
        if (pin == null) throw new NullPointerException();

        List<Pin> collectedPins = new ArrayList<>();

        for (PinLink link : links) {
            link.getLinkedPin(pin).ifPresent(collectedPins::add);
        }

        return collectedPins;
    }

    public boolean isLinked(Pin pin) {
        if (pin == null) throw new NullPointerException();

        for (PinLink link : links) {
            if (link.getFirstPin() == pin | link.getSecondPin() == pin) return true;
        }

        return false;
    }

    public int getLinkCount() {
        return links.size();
    }
}