package sunmade.jcircuit.model.linker;

import java.util.Set;

public interface Linker<T> {
    void link(T first, T second);

    void unlink(T linkable);

    boolean isLinked(T linkable);

    Set<T> getLinks(T linkable);

    int getLinkCount();
}