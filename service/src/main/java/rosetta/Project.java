package rosetta;

public class Project{
    private final ToJson<Project> toJson;
    private final String name;

    public Project(ToJson<Project> toJson, String name) {
        this.toJson = toJson;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toJson(){
        return toJson.toJson(this);
    }
}
