package sunmade.jcircuit.linker;

import sunmade.jcircuit.element.Element;

import java.util.Objects;

public class ElementLinker implements Linker<Element> {
    private final PinLinker pinLinker = new PinLinker();

    @Override
    public void link(Element firstElement, Element secondElement) {
        if (firstElement == null | secondElement == null) throw new NullPointerException();
        if (firstElement == secondElement) throw new IllegalArgumentException();

        for (PinLink pinLink : firstElement.getPinIntersections(secondElement)) {
            pinLinker.link(pinLink.getFirstPin(), pinLink.getSecondPin());
        }
    }

    @Override
    public void unlink(Element element) {
        Objects.requireNonNull(element).getPins().forEach(pinLinker::unlink);
    }

    public PinLinker getPinLinker() {
        return pinLinker;
    }
}