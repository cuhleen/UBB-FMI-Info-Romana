package streams.model;

@FunctionalInterface
public interface Area {

    <E> double compute (E e);

}
