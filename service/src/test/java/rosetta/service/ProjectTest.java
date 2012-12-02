package rosetta.service;

import org.junit.Test;

import static org.javafunk.funk.Literals.listOf;

public class ProjectTest {
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentForNullToJson() {
        new Project(null, "", "", listOf(Language.class));
    }
}
