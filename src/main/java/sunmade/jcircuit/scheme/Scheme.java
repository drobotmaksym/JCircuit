package sunmade.jcircuit.scheme;

import javafx.scene.image.Image;
import sunmade.jcircuit.element.Element;
import sunmade.jcircuit.linker.ElementLinker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Scheme {
    private final List<Element> elements = new ArrayList<>();
    private final ElementLinker elementLinker = new ElementLinker();
    private String name;

    public Scheme(String name) {
        setName(name);
    }

    public void addElement(Element element) {
        elements.add(Objects.requireNonNull(element));
    }

    public void removeElement(Element element) {
        if (elements.remove(Objects.requireNonNull(element))) {
            elementLinker.unlink(element);
        }
    }

    public Optional<Element> getElement(double x, double y) {
        for (Element element : elements) {
            Image image = element.getImage();
            x -= element.getX() - image.getWidth() / 2;
            y -= element.getY() - image.getHeight() / 2;
            if (x >= 0 & x <= image.getWidth() & y >= 0 & y <= image.getHeight()) {
                return Optional.of(element);
            }
        }
        return Optional.empty();
    }

    public void linkElement(Element element) {
        if (element == null) throw new NullPointerException();
        for (Element listElement : elements) {
            elementLinker.link(element, listElement);
        }
    }

    public void unlinkElement(Element element) {
        elementLinker.unlink(element);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }
}
