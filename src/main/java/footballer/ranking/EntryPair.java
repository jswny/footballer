package footballer.ranking;

/**
 * Defines a pairing of a {@link footballer.structure.Week} number ({@code int}), and a {@link RankLogEntry}.
 * This class is used (internally) to keep track of each {@link footballer.structure.Team}'s change in {@link Rank} for each week.
 */
public class EntryPair {
    public final int week;
    public final RankLogEntry entry;

    public EntryPair(int week, RankLogEntry entry) {
        this.week = week;
        this.entry = entry;
    }

    @Override
    public String toString() {
        return "Week " + week + ": \n" + entry;
    }
}
