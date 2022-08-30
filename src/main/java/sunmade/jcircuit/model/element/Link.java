package sunmade.jcircuit.model.element;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Link<T> {
    private final T linkable;
    private final Set<Link<T>> links = new HashSet<>();

    public Link(T linkable) {
        this.linkable = Objects.requireNonNull(linkable);
    }

    public void addLink(Link<T> link) {

    }

    public void removeLink(Link<T> link) {

    }

    public T getLinkable() {
        return linkable;
    }

    public Set<Link<T>> getLinks() {
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;
        Link<?> link = (Link<?>) o;
        return Objects.equals(linkable, link.linkable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkable);
    }
}
