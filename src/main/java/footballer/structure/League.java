package footballer.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a league such as the NFL.
 */
public class League {
    public final String name;
    private List<Season> seasons = new ArrayList<>();

    public League(String n) {
        name = n;
    }

    /**
     * Adds a {@link Season} to this league.
     * @param year the year of the season to be added
     * @return the newly created {@link Season}, or {@code null} if a {@link Season} which matches {@code year} already exists in this league
     */
    public Season addSeason(int year) {
        if (getSeason(year) != null) return null;
        Season season = new Season(year);
        seasons.add(season);
        return season;
    }

    /**
     * Gets a {@link Season} by its year.
     * @param year the year of the {@link Season} to get
     * @return the {@link Season} which matches {@code year}, or {@code null} if no such season exists
     */
    public Season getSeason(int year) {
        for (Season season : seasons) {
            if (season.year == year) {
                return season;
            }
        }
        return null;
    }
}
