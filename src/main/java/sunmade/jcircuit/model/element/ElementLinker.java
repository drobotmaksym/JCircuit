package sunmade.jcircuit.model.element;

import java.util.*;

public class ElementLinker implements Linker<Element> {
    private final Set<ElementLink> elementLinks = new HashSet<>();

    @Override
    public void link(Element first, Element second) {
        throw new NullPointerException();
    }

    @Override
    public void unlink(Element linkable) {
        throw new NullPointerException();
    }

    @Override
    public boolean isLinked(Element linkable) {
        throw new NullPointerException();
    }

    @Override
    public Set<Element> getLinks(Element linkable) {
        throw new NullPointerException();
    }

    @Override
    public int getLinkCount() {
        return elementLinks.size();
    }

}
