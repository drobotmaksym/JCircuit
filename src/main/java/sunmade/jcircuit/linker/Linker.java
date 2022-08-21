package sunmade.jcircuit.linker;

// Redundant ?
public interface Linker<T> {

    void link(T first, T second);

    void unlink(T target);

}
