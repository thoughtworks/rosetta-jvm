package rosetta;

import com.google.inject.AbstractModule;

public class RepositoryModule extends AbstractModule {
    @Override protected void configure() {
        bind(Projects.class).to(CannedProjects.class);
    }
}
