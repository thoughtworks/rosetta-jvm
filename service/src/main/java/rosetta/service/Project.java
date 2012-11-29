package rosetta.service;

import rosetta.behaviours.ToJson;

import java.util.List;

public class Project{
    private final ToJson<Project> toJson;
    private final String name;
    private final List<Language> languages;

    public Project(ToJson<Project> toJson, String name, List<Language> languages) {
        this.toJson = toJson;
        this.name = name;
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public String toJson(){
        return toJson.toJson(this);
    }
}
