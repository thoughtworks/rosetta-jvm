package rosetta.service;

import rosetta.behaviours.ToJson;

import java.util.List;

public class Project{
    private final ToJson<Project> toJson;
    private final String user;
    private final String repository;
    private final List<Language> languages;

    public Project(ToJson<Project> toJson, String user, String repository, List<Language> languages) {
        this.toJson = toJson;
        this.user = user;
        this.repository = repository;
        this.languages = languages;
    }

    public String getUser() {
        return user;
    }

    public String getRepository() {
        return repository;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public String toJson(){
        return toJson.toJson(this);
    }
}
