package sunmade.jcircuit.model.linker;

import sunmade.jcircuit.model.element.Element;
import sunmade.jcircuit.model.element.pin.Pin;

import java.util.*;

public class ElementLinker implements Linker<Element> {
    private final Set<Link<Element>> elementLinks = new HashSet<>();
    private final Linker<Pin> pinLinker;

    public ElementLinker(Linker<Pin> pinLinker) {
        this.pinLinker = Objects.requireNonNull(pinLinker);
    }

    @Override
    public void link(Element first, Element second) {
        if (first == null | second == null) throw new NullPointerException();
        getLink(first).ifPresentOrElse(
                elementLink -> elementLink.addBranch(new Link<>(second)),
                () -> elementLinks.add(new Link<>(first))
        );
        getLink(second).ifPresentOrElse(
                elementLink -> elementLink.addBranch(new Link<>(first)),
                () -> elementLinks.add(new Link<>(second))
        );
        for (Pin firstPin : first.getPins()) {
            for (Pin secondPin : second.getPins()) {
                int firstX = first.getX() + firstPin.getRelativeX();
                int firstY = first.getY() + firstPin.getRelativeY();
                int secondX = second.getX() + secondPin.getRelativeX();
                int secondY = second.getY() + secondPin.getRelativeY();
                if (firstX == secondX & firstY == secondY) {
                    pinLinker.link(firstPin, secondPin);
                }
            }
        }
    }

    @Override
    public void unlink(Element element) {
        getLink(element).ifPresent(elementLink -> {
            elementLink.getLinkable().getPins().forEach(pinLinker::unlink);
            elementLink.getBranches().forEach(branchLink -> branchLink.removeBranch(elementLink));
            elementLinks.remove(elementLink);
        });
    }

    @Override
    public boolean isLinked(Element element) {
        return getLink(element).isPresent();
    }

    @Override
    public Optional<Link<Element>> getLink(Element element) {
        if (element == null) throw new NullPointerException();
        for (Link<Element> elementLink : elementLinks) {
            if (elementLink.getLinkable() == element) {
                return Optional.of(elementLink);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<Link<Element>> getLinks() {
        return elementLinks;
    }
}
