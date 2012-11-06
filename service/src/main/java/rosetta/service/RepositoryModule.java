package rosetta.service;

import com.google.inject.AbstractModule;
import rosetta.service.CannedProjects;
import rosetta.service.Projects;

public class RepositoryModule extends AbstractModule {
    @Override protected void configure() {
        bind(Projects.class).to(CannedProjects.class);
    }
}
