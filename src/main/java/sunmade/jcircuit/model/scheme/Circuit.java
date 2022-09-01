package sunmade.jcircuit.model.scheme;

import sunmade.jcircuit.model.element.Element;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Circuit {
    private final Set<Element> elements = new HashSet<>();

    public void addElement(Element element) {
        elements.add(Objects.requireNonNull(element));
    }

    public void removeElement(Element element) {
        elements.remove(Objects.requireNonNull(element));
    }

    public Set<Element> getElements() {
        return elements;
    }
}
