package rosetta.service;

public class Language {
    private final String name;
    private final Integer percentage;

    public Language(String name, Integer percentage) {
        this.name = name;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public Integer getPercentage() {
        return percentage;
    }
}
