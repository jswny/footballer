package footballer.ranking.logging;

import footballer.Utils;
import footballer.ranking.Rank;
import footballer.ranking.RankingSystem;
import footballer.structure.Game;
import footballer.structure.Team;

/**
 * Defines a single entry for a {@link Log}.
 *
 * This entry handles two different types of {@link Team}s, a {@code favorite}, and an {@code underdog}.
 * The {@code favorite} {@link Team} is the team with the higher initial {@link Rank} before the results of a {@link Game} are applied using a {@link RankingSystem},
 * and the {@code underdog} is the other team.
 * Currently, support for even initial {@link Rank}s is yet to be added.
 * Each type of {@link Team} (favorite and underdog) has the following attributes:
 * <ul>
 *     <li>
 *         An initial value ({@code favoriteInitial} or {@code underdogInitial})
 *         which represents the {@link Rank} of the team before the results of the {@link Game} were applied through the {@link RankingSystem}
 *     </li>
 *     <li>
 *         A new value ({@code favoriteNew} or {@code underdogNew})
 *         which represents the new {@link Rank} values of the team after the {@link RankingSystem} calculated the new rank based on the results of the {@link Game}.
 *     </li>
 * </ul>
 */
public class LogEntry {
    public final Game game;
    public final Team favorite;
    public final Team underdog;
    public final double favoriteInitial;
    public final double favoriteNew;
    public final double underdogInitial;
    public final double underdogNew;

    public LogEntry(Game game, Team favorite, Team underdog, double favoriteInitial, double favoriteNew, double underdogInitial, double underdogNew) {
        this.game = game;
        this.favorite = favorite;
        this.underdog = underdog;
        this.favoriteInitial = favoriteInitial;
        this.favoriteNew = favoriteNew;
        this.underdogInitial = underdogInitial;
        this.underdogNew = underdogNew;
    }

    /**
     * Gets the {@code new} {@link Rank} value for a given {@link Team}.
     * @param teamName the name of the {@link Team} to get the {@code new} value for
     * @return {@code favoriteNew} if {@code teamName} matches {@code favorite},
     * or {@code underdogNew} if {@code teamName} matches {@code underdog},
     * or {@code -1} if neither {@link Team} matches {@code teamName}
     */
    public double getNewValue(String teamName) {
        if (teamName.equals(favorite.name)) {
            return favoriteNew;
        } else if (teamName.equals(underdog.name)) {
            return underdogNew;
        } else {
            return -1;
        }
    }

    /**
     * Gets the {@code initial} {@link Rank} value for a given {@link Team}.
     * @param teamName the name of the {@link Team} to get the {@code initial} value for
     * @return {@code favoriteInitial} if {@code teamName} matches {@code favorite},
     * or {@code underdogInitial} if {@code teamName} matches {@code underdog},
     * or {@code -1} if neither {@link Team} matches {@code teamName}
     */
    public double getInitialValue(String teamName) {
        if (teamName.equals(favorite.name)) {
            return favoriteInitial;
        } else if (teamName.equals(underdog.name)) {
            return underdogInitial;
        } else {
            return -1;
        }
    }

    /**
     * Gets the change in {@link Rank} value for a given {@link Team}, which can be negative.
     * @param teamName the name of the {@link Team} to get the change in value for
     * @return {@code favoriteNew - favoriteInitial} if {@code teamName} matches {@code favorite},
     * or {@code underdogNew - underdogInitial} if {@code teamName} matches {@code underdog},
     * or {@code -1} if neither {@link Team} matches {@code teamName}
     */
    public double getDiff(String teamName) {
        if (teamName.equals(favorite.name)) {
            return favoriteNew - favoriteInitial;
        } else if (teamName.equals(underdog.name)) {
            return underdogNew - underdogInitial;
        } else {
            return -1;
        }
    }

    /**
     * Gets the percent change in {@link Rank} value for a given {@link Team}, which can be negative.
     * @param teamName the name of the {@link Team} to get the percent change in value for
     * @return The percent change between {@code favoriteInitial} and {@code favoriteNew} if {@code teamName} matches {@code favorite},
     * or the percent change between {@code underdogInitial} and {@code underdogNew} if {@code teamName} matches {@code underdog},
     * or {@code -1} if neither {@link Team} matches {@code teamName}
     */
    public double getPercentChange(String teamName) {
        double oldVal;
        double newVal;
        if (teamName.equals(favorite.name)) {
            oldVal = favoriteInitial;
            newVal = favoriteNew;
        } else if (teamName.equals(underdog.name)) {
            oldVal = underdogInitial;
            newVal = underdogNew;
        } else {
            return -1;
        }
        return ((newVal - oldVal) / Math.abs(oldVal)) * 100;
    }

    /**
     * Determines whether a given {@link Team} is a member of this rank log entry.
     * @param teamName the name of the {@link Team} to check
     * @return {@code true} if either {@code favorite} or {@code underdog} matches {@code teamName}, or {@code false} otherwise
     */
    public boolean isMember(String teamName) {
        return favorite.name.equals(teamName) || underdog.name.equals(teamName);
    }

    /**
     * Prepends {@code +} to the {@link String} form of a given {@code double} if the value is positive.
     * @param value the value to be prepended to
     * @return the {@link String} form of {@code value} withe {@code +} prepended to it if the value is positive,
     * or just the string form of {@code value} otherwise
     */
    private String applySign(double value) {
        return (value > 0 ? "+" : "") + value;
    }

    @Override
    public String toString() {
        String result = "";
        result += game + "\n";

        double favoriteDiffVal = Utils.roundDecimal(getDiff(favorite.name), 2);
        double underdogDiffVal = Utils.roundDecimal(getDiff(underdog.name), 2);

        String favoriteDiff = applySign(favoriteDiffVal);
        String underdogDiff = applySign(underdogDiffVal);

        double favoritePercentChangeVal = Utils.roundDecimal(getPercentChange(favorite.name), 1);
        double underdogPercentChangeVal = Utils.roundDecimal(getPercentChange(underdog.name), 1);

        String favoritePercentChange = applySign(favoritePercentChangeVal);
        String underdogPercentChange = applySign(underdogPercentChangeVal);

        String favoriteTeam = "(F) " + favorite.name;
        String underdogTeam = "(U) " + underdog.name;

        result += favoriteTeam + " - old: " + Utils.roundDecimal(favoriteInitial, 2) + " -> new: " + Utils.roundDecimal(favoriteNew, 2) + ", change: " + favoriteDiff + " (" + favoritePercentChange + "%)\n";
        result += underdogTeam + " - old: " + Utils.roundDecimal(underdogInitial, 2) + " -> new: " + Utils.roundDecimal(underdogNew, 2) + ", change: " + underdogDiff + " (" + underdogPercentChange + "%)\n";

        return result;
    }
}
