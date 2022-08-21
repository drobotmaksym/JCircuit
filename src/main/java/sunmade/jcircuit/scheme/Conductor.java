package sunmade.jcircuit.scheme;

import sunmade.jcircuit.element.Element;
import sunmade.jcircuit.linker.PinLinker;
import sunmade.jcircuit.element.pin.Pin;

import java.util.Objects;

public class Conductor {
    private final PinLinker pinLinker;

    public Conductor(PinLinker pinLinker) {
        this.pinLinker = Objects.requireNonNull(pinLinker);
    }

    public void conductThrough(Element element) {
        if (element == null) throw new NullPointerException();

        for (Pin pin : element.getPins()) {
            for (Pin linkedPin : pinLinker.getLinkedPins(pin)) {
                pin.setCurrent(linkedPin.getCurrent());
            }
        }

    }

}

/*
When adding an element:
scheme.add(element)
conductor(scheme).conduct()
schemePane.update()
 */