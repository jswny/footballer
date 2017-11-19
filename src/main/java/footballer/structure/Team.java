package footballer.structure;

/**
 * Defines an NFL team.
 */
public class Team {
    public final String name;

    public Team(String n) {
        name = n;
    }

    @Override
    public String toString() {
        return name;
    }
}
