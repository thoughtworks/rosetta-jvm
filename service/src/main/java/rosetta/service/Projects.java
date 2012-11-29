package rosetta.service;

public interface Projects {
    <T> T find(String user, String repository, LookupHandler<T, Project> handler);
}
