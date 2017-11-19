package footballer.structure;

/**
 * Defines an NFL game.
 */
public class Game {
    public final Team awayTeam;
    public final Team homeTeam;

    public final int awayTeamScore;
    public final int homeTeamScore;

    public Game(Team aT, Team hT, int aTS, int hTS) {
        awayTeam = aT;
        homeTeam = hT;

        awayTeamScore = aTS;
        homeTeamScore = hTS;
    }

    /**
     * Determines the winner of the game.
     * @return the winning {@link Team}, or {@code null} if the game was a tie
     */
    public Team getWinner() {
        if (homeTeamScore == awayTeamScore) {
            return null;
        } else if (homeTeamScore > awayTeamScore) {
            return homeTeam;
        } else {
            return awayTeam;
        }
    }

    @Override
    public String toString() {
        Team winner = getWinner();
        if (winner == null) {
            return awayTeam + " (" + awayTeamScore + ") @ " + homeTeam + " (" + homeTeamScore + ")";
        } else if (winner == homeTeam) {
            return awayTeam + " (" + awayTeamScore + ") @ " + homeTeam.toString().toUpperCase() + " (" + homeTeamScore + ")";
        } else {
            return awayTeam.toString().toUpperCase() + " (" + awayTeamScore + ") @ " + homeTeam + " (" + homeTeamScore + ")";
        }

    }
}
