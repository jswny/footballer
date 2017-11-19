package footballer.structure;

/**
 * Defines a record for a {@link Team}.
 */
public class Record {
    private final Team team;
    private int homeWins;
    private int awayWins;
    private int homeLosses;
    private int awayLosses;
    private int ties;

    public Record(Team team, int homeWins, int awayWins, int homeLosses, int awayLosses, int ties) {
        this.team = team;
        this.homeWins = homeWins;
        this.awayWins = awayWins;
        this.homeLosses = homeLosses;
        this.awayLosses = awayLosses;
        this.ties = ties;
    }

    public Team getTeam() {
        return team;
    }

    public int getWins() {
        return homeWins + awayWins;
    }

    public int getLosses() {
        return homeLosses + awayLosses;
    }

    public int getHomeWins() {
        return homeWins;
    }

    public void setHomeWins(int homeWins) {
        this.homeWins = homeWins;
    }

    public int getAwayWins() {
        return awayWins;
    }

    public void setAwayWins(int awayWins) {
        this.awayWins = awayWins;
    }

    public int getHomeLosses() {
        return homeLosses;
    }

    public void setHomeLosses(int homeLosses) {
        this.homeLosses = homeLosses;
    }

    public int getAwayLosses() {
        return awayLosses;
    }

    public void setAwayLosses(int awayLosses) {
        this.awayLosses = awayLosses;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }

    @Override
    public String toString() {
        return  "#Record<Team: " + team + ", Wins: [Home: " + homeWins + ", Away: " + awayWins + "], Losses: [Home: " + homeLosses + ", Away: " + awayLosses + "], Overall: " + getWins() + "-" + getLosses() + ">";
    }
}
