package sunmade.jcircuit.model.linker;

import sunmade.jcircuit.model.element.pin.Pin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PinLinker implements Linker<Pin> {
    private final Set<Link<Pin>> pinLinks = new HashSet<>();

    @Override
    public void link(Pin first, Pin second) {
        if (first == null | second == null) throw new NullPointerException();
        getLink(first).ifPresentOrElse(
                elementLink -> elementLink.addBranch(new Link<>(second)),
                () -> pinLinks.add(new Link<>(first))
        );
        getLink(second).ifPresentOrElse(
                elementLink -> elementLink.addBranch(new Link<>(first)),
                () -> pinLinks.add(new Link<>(second))
        );
    }

    @Override
    public void unlink(Pin pin) {
        getLink(pin).ifPresent(pinLink -> {
            pinLink.getBranches().forEach(branchLink -> branchLink.removeBranch(pinLink));
            pinLinks.remove(pinLink);
        });
    }

    @Override
    public boolean isLinked(Pin pin) {
        return getLink(pin).isPresent();
    }

    @Override
    public Optional<Link<Pin>> getLink(Pin pin) {
        if (pin == null) throw new NullPointerException();
        for (Link<Pin> pinLink : pinLinks) {
            if (pinLink.getLinkable() == pin) {
                return Optional.of(pinLink);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<Link<Pin>> getLinks() {
        return pinLinks;
    }
}
