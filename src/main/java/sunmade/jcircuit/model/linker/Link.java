package sunmade.jcircuit.model.linker;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Link<T> {
    private final T linkable;
    private final Set<Link<T>> branches = new HashSet<>();

    public Link(T linkable) {
        this.linkable = Objects.requireNonNull(linkable);
    }

    public void addBranch(Link<T> link) {
        branches.add(Objects.requireNonNull(link));
    }

    public void removeBranch(Link<T> link) {
        branches.remove(Objects.requireNonNull(link));
    }

    public T getLinkable() {
        return linkable;
    }

    public Set<Link<T>> getBranches() {
        return branches;
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
