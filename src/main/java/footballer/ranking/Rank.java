package footballer.ranking;

import java.util.Comparator;
import footballer.Utils;
import footballer.structure.*;

/**
 * Defines a rank for a {@link Team}.
 */
public class Rank {
    public final Team team;
    private double value;

    /**
     * {@link Comparator} to be used to compare two ranks to each other by their {@code value}.
     */
    public static Comparator<Rank> valueComparator = (Rank e1, Rank e2) -> -Double.compare(e1.getValue(), e2.getValue());

    public Rank(Team team) {
        this.team = team;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String toString() {
        return "#Rank<Team: " + team.name + ", Value: " + Utils.roundDecimal(value, 2) + ">";
    }
}
