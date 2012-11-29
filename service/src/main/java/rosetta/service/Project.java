package rosetta.service;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import rosetta.behaviours.ToJson;

import java.util.ArrayList;
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
        this.languages = new ArrayList<Language>(languages);
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

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, "toJson");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "toJson");
    }

    @Override public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String inspect() {
        return toString();
    }
}
