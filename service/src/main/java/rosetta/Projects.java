package rosetta;

public interface Projects {
    <T> T find(String id, LookupHandler<T, Project> handler);
}
