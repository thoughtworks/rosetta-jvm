package rosetta.service;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Language {
    private final String name;
    private final Integer weighting;

    public Language(String name, Integer weighting) {
        this.name = name;
        this.weighting = weighting;
    }

    public String getName() {
        return name;
    }

    public Integer getWeighting() {
        return weighting;
    }

    @Override public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
