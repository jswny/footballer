package footballer.structure;

import java.util.List;
import java.util.ArrayList;

public class Season {
    public final int year;
    private List<Conference> conferences = new ArrayList<>();
    private List<Week> weeks = new ArrayList<>();

    public Season(int y) {
        year = y;
    }

    public Conference addConference(String conferenceName) {
        if (getConference(conferenceName) != null) return null;
        Conference conf = new Conference(conferenceName);
        conferences.add(conf);
        return conf;
    }

    public Division addDivision(String conferenceName, String divisionName) {
        Conference conf = getConference(conferenceName);
        if (conf == null) return null;
        return conf.addDivision(divisionName);
    }

    public Team addTeam(String conferenceName, String divisionName, String teamName) {
        Conference conf = getConference(conferenceName);
        if (conf == null) return null;
        Division div = conf.getDivision(divisionName);
        if (div == null) return null;
        return div.addTeam(teamName);
    }

    /**
     * Adds a week with the specified number to the season.
     * @param number the number of the week (1, 2, 3, etc.)
     * @return the newly created week
     */
    public Week addWeek(int number) {
        Week week = new Week(number);
        weeks.add(week);
        return week;
    }

    /**
     * Adds a game to a specified week number.
     * @param weekNum the number of the week to add the game to
     * @param awayTeamName the name of the away team
     * @param homeTeamName the name of the home team
     * @param awayTeamScore the score of the away team
     * @param homeTeamScore the score of the home team
     * @return the newly created game, or null if the game was not created
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


    public Conference getConference(String conferenceName) {
        for (Conference conf : conferences) {
            if (conf.name.equals(conferenceName)) return conf;
        }
        return null;
    }

    public Division getDivision(String conferenceName, String divisionName) {
        Conference conf = getConference(conferenceName);
        if (conf == null) return null;
        Division div = conf.getDivision(divisionName);
        if (div == null) return null;
        return div;
    }

    public Team getTeam(String teamName) {
        for (Team team : getTeams()) {
            if (team.name.equals(teamName)) return team;
        }
        return null;
    }

    public Week getWeek(int weekNum) {
        for (Week week : weeks) {
            if (week.number == weekNum) return week;
        }
        return null;
    }

    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();
        for (Conference conf : conferences) {
            teams.addAll(conf.getTeams());
        }
        return teams;
    }

    public List<Week> getWeeks() {
        return weeks;
    }

    public List<Game> getGames() {
        List<Game> games = new ArrayList<>();
        for (Week week : weeks) {
            games.addAll(week.getGames());
        }
        return games;
    }

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

    public List<Record> getRecords() {
        List<Record> records = new ArrayList<>();
        for (Team team : getTeams()) {
            records.add(getRecord(team.name));
        }
        return records;
    }

//	public Ranking getRanking(Ranking ranking) {
//		ranking.applyGames(this);
//		return ranking;
//	}

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


