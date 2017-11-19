package footballer.structure;

import java.util.List;
import java.util.ArrayList;

/**
 * Defines an NFL season.
 */
public class Season {
    public final int year;
    private List<Conference> conferences = new ArrayList<>();
    private List<Week> weeks = new ArrayList<>();

    public Season(int y) {
        year = y;
    }

    /**
     * Adds a {@link Conference} to this season.
     * @param conferenceName the name of the {@link Conference} to be added
     * @return the newly created {@link Conference}, or {@code null} if a {@link Conference} which matches {@code conferenceName} already exists in this season
     */
    public Conference addConference(String conferenceName) {
        if (getConference(conferenceName) != null) return null;
        Conference conf = new Conference(conferenceName);
        conferences.add(conf);
        return conf;
    }

    /**
     * Adds a {@link Division} to a {@link Conference} in this season.
     * @param conferenceName the name of the {@link Conference} to add the {@link Division} to
     * @param divisionName the name of the {@link Division} to be added
     * @return the newly created {@link Division},
     * or {@code null} if a {@link Conference} which matches {@code conferenceName} does not exist in this season,
     * or if a division which matches {@code divisionName} already exists in the specified conference
     */
    public Division addDivision(String conferenceName, String divisionName) {
        Conference conf = getConference(conferenceName);
        if (conf == null) return null;
        return conf.addDivision(divisionName);
    }

    /**
     * Adds a {@link Team} to a specified {@link Division} in a specified {@link Conference} in this season.
     * @param conferenceName the name of the {@link Conference} containing the {@link Division} to add the {@link Team} to
     * @param divisionName the name of the {@link Division} to add the {@link Team} to
     * @param teamName the name of the {@link Team} to be added
     * @return the newly created {@link Team},
     * or {@code null} if a {@link Conference} which matches {@code conferenceName} does not exist in this season,
     * or if a {@link Division} which matches {@code divisionName} does not exist in the specified conference,
     * or if a team which matches {@code teamName} already exists in the specified division
     */
    public Team addTeam(String conferenceName, String divisionName, String teamName) {
        Conference conf = getConference(conferenceName);
        if (conf == null) return null;
        Division div = conf.getDivision(divisionName);
        if (div == null) return null;
        return div.addTeam(teamName);
    }

    /**
     * Adds a {@link Week} to this season.
     * @param number the number of the week ({@code 1}, {@code 2}, {@code 3}, etc.)
     * @return the newly created {@link Week}
     */
    public Week addWeek(int number) {
        Week week = new Week(number);
        weeks.add(week);
        return week;
    }

    /**
     * Adds a {@link Game} to a {@link Week} in this season.
     * @param weekNum the number of the {@link Week} to add the {@link Game} to
     * @param awayTeamName the name of the away {@link Team}
     * @param homeTeamName the name of the home {@link Team}
     * @param awayTeamScore the score of the away {@link Team}
     * @param homeTeamScore the score of the home {@link Team}
     * @return the newly created {@link Game},
     * or {@code null} if a {@link Week} which matches {@code weekNum} does not exist in this season,
     * or if a {@link Team} which matches {@code awayTeamName} does not exist in this season,
     * or if a {@link Team} which matches {@code homeTeamName} does not exist in this season
     */
    public Game addGame(int weekNum, String awayTeamName, String homeTeamName, int awayTeamScore, int homeTeamScore) {
        Week week = getWeek(weekNum);
        Team awayTeam = getTeam(awayTeamName);
        Team homeTeam = getTeam(homeTeamName);
        if (week == null || awayTeam == null || homeTeam == null) return null;
        Game game = new Game(awayTeam, homeTeam, awayTeamScore, homeTeamScore);
        week.addGame(game);
        return game;
    }

    /**
     * Gets a {@link Conference} in this division by its name.
     * @param conferenceName the name of the {@link Conference} to get
     * @return the {@link Conference} which matches {@code conferenceName}, or {@code null} if no such conference exists
     */
    public Conference getConference(String conferenceName) {
        for (Conference conf : conferences) {
            if (conf.name.equals(conferenceName)) return conf;
        }
        return null;
    }

    /**
     * Gets a {@link Division} in a specified {@link Conference} in this season by its name.
     * @param conferenceName the name of the {@link Conference} to get the {@link Division} from
     * @param divisionName the name of the {@link Division} to get
     * @return the {@link Division} which matches {@code divisionName} in the {@link Conference} which matches {@code conferenceName} in this season,
     * or {@code null} if no such division exists,
     * or if no such conference exists
     */
    public Division getDivision(String conferenceName, String divisionName) {
        Conference conf = getConference(conferenceName);
        if (conf == null) return null;
        Division div = conf.getDivision(divisionName);
        if (div == null) return null;
        return div;
    }

    /**
     * Gets a {@link Team} in this season by its name.
     * @param teamName the name of the {@link Team} to get
     * @return the {@link Team} which matches {@code teamName}, or {@code null} if no such team exists
     */
    public Team getTeam(String teamName) {
        for (Team team : getTeams()) {
            if (team.name.equals(teamName)) return team;
        }
        return null;
    }

    /**
     * Gets a {@link Week} in this season by its number.
     * @param weekNum the number of the {@link Week} to get
     * @return the {@link Week} which matches {@code weekNum}, or {@code null} if no such week exists
     */
    public Week getWeek(int weekNum) {
        for (Week week : weeks) {
            if (week.number == weekNum) return week;
        }
        return null;
    }

    /**
     * Gets a list of all {@link Team}s in this season.
     * @return a {@link List} of all {@link Team}s in this season
     */
    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();
        for (Conference conf : conferences) {
            teams.addAll(conf.getTeams());
        }
        return teams;
    }

    /**
     * Gets a list of all {@link Week}s in this season.
     * @return a {@link List} of all {@link Week}s in this season
     *
     */
    public List<Week> getWeeks() {
        return weeks;
    }

    /**
     * Gets a list of all {@link Game}s in this season.
     * @return a {@link List} of all {@link Game}s in this season
     */
    public List<Game> getGames() {
        List<Game> games = new ArrayList<>();
        for (Week week : weeks) {
            games.addAll(week.getGames());
        }
        return games;
    }

    /**
     * Gets a {@link Record} for a {@link Team} in this season by its name.
     * @param teamName the name of the {@link Team} to get the {@link Record} for
     * @return the {@link Record} for the {@link Team} which matches {@code teamName}, or {@code null} if no such team exists
     */
    public Record getRecord(String teamName) {
        Team team = getTeam(teamName);
        if (team == null) return null;

        int homeWins = 0;
        int awayWins = 0;
        int homeLosses = 0;
        int awayLosses = 0;
        int ties = 0;

        List<Game> games = getGames();

        for (Game game : games) {
            if (game.homeTeam == team && game.homeTeamScore > game.awayTeamScore) {
                homeWins++;
            } else if (game.awayTeam == team && game.awayTeamScore > game.homeTeamScore) {
                awayWins++;
            } else if (game.homeTeam == team && game.awayTeamScore > game.homeTeamScore) {
                homeLosses++;
            } else if (game.awayTeam == team && game.homeTeamScore > game.awayTeamScore) {
                awayLosses++;
            } else {
                ties++;
            }
        }

        return new Record(team, homeWins, awayWins, homeLosses, awayLosses, ties);
    }

    /**
     * Gets a list of all {@link Record}s for {@link Team}s in this season.
     * @return a {@link List} of {@link Record}s for all teams in this season
     */
    public List<Record> getRecords() {
        List<Record> records = new ArrayList<>();
        for (Team team : getTeams()) {
            records.add(getRecord(team.name));
        }
        return records;
    }

    @Override
    public String toString() {
        String result = year + ": [\n    Conferences: [\n";

        for (Conference conf : conferences) {
            result += "        " + conf + ",\n";
        }
        result = result.substring(0, result.length() - 2);
        result += "\n    ],";

        result += "\n    Weeks: [\n";
        for (Week week : weeks) {
            result += "        " + week + ",\n";
        }
        result = result.substring(0, result.length() - 2);
        return result + "\n    ]\n]";
    }
}


