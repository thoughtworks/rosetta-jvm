package rosetta;

public interface LookupHandler<T, V> {
    T found(V item);
    T notFound();
}
