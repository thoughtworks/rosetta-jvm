package rosetta.service;

import org.codehaus.jackson.map.ObjectMapper;
import rosetta.behaviours.ToJson;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;

import static org.javafunk.funk.Literals.listWith;

public class CannedProjects implements Projects {
    private final HashMap<String, Project> projects;

    @Inject
    public CannedProjects(final ObjectMapper objectMapper){
        ToJson<Project> projectToJson = new ToJson<Project>() {
            @Override public String toJson(Project object) {
                try {
                    return objectMapper.writeValueAsString(object);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        this.projects = new HashMap<String, Project>();
        projects.put("rails", new Project(projectToJson, "rails", listWith(new Language("ruby", 100))));
    }

    @Override public <T> T find(String id, LookupHandler<T, Project> handler) {
        Project project = projects.get(id);

        if(project != null){
            return handler.found(project);
        }else{
            return handler.notFound();
        }
    }
}
