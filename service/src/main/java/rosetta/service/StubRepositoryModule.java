package rosetta.service;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.codehaus.jackson.map.ObjectMapper;

public class StubRepositoryModule extends AbstractModule {
    @Override protected void configure() {
    }

    @Provides
    public Projects projects(ObjectMapper objectMapper){
        return new CannedProjects(objectMapper);
    }
}
