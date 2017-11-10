package footballer.structure;

import java.util.ArrayList;
import java.util.List;

public class League {
    public final String name;
    private List<Season> seasons = new ArrayList<>();

    public League(String n) {
        name = n;
    }

    public Season addSeason(int year) {
        if (getSeason(year) != null) return null;
        Season season = new Season(year);
        seasons.add(season);
        return season;
    }

    public Season getSeason(int year) {
        for (Season season : seasons) {
            if (season.year == year) {
                return season;
            }
        }
        return null;
    }
}
