package sunmade.jcircuit.model.linker;

import java.util.Collection;
import java.util.Optional;

public interface Linker<T> {
    void link(T first, T second);

    void unlink(T linkable);

    boolean isLinked(T linkable);

    Optional<Link<T>> getLink(T linkable);

    Collection<Link<T>> getLinks();
}