package rosetta.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.javafunk.funk.datastructures.tuples.Pair;
import rosetta.behaviours.ToJson;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;

import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.Literals.tuple;

public class CannedProjects implements Projects {
    private final HashMap<Pair<String, String>, Project> projects;

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

        this.projects = new HashMap<Pair<String, String>, Project>();
        projects.put(tuple("rails", "rails"), new Project(projectToJson, "rails", "rails", listWith(
                new Language("ruby", 60),
                new Language("python", 10),
                new Language("javascript", 15),
                new Language("clojure", 15))));
    }

    @Override public <T> T find(String user, String repository, LookupHandler<T, Project> handler) {
        Project project = projects.get(tuple(user, repository));

        if(project != null){
            return handler.found(project);
        }else{
            return handler.notFound();
        }
    }
}
