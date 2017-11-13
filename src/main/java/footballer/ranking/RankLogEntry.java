package footballer.ranking;

import footballer.Utils;
import footballer.structure.Game;
import footballer.structure.Team;

public class RankLogEntry {
    public final Game game;
    public final Team favorite;
    public final Team underdog;
    public final double favoriteInitial;
    public final double favoriteNew;
    public final double underdogInitial;
    public final double underdogNew;

    public RankLogEntry(Game game, Team favorite, Team underdog, double favoriteInitial, double favoriteNew, double underdogInitial, double underdogNew) {
        this.game = game;
        this.favorite = favorite;
        this.underdog = underdog;
        this.favoriteInitial = favoriteInitial;
        this.favoriteNew = favoriteNew;
        this.underdogInitial = underdogInitial;
        this.underdogNew = underdogNew;
    }

    public double getNewValue(String teamName) {
        if (teamName.equals(favorite.name)) {
            return favoriteNew;
        } else if (teamName.equals(underdog.name)) {
            return underdogNew;
        } else {
            return -1;
        }
    }

    public double getInitialValue(String teamName) {
        if (teamName.equals(favorite.name)) {
            return favoriteInitial;
        } else if (teamName.equals(underdog.name)) {
            return underdogInitial;
        } else {
            return -1;
        }
    }

    public double getDiff(String teamName) {
        if (teamName.equals(favorite.name)) {
            return favoriteNew - favoriteInitial;
        } else if (teamName.equals(underdog.name)) {
            return underdogNew - underdogInitial;
        } else {
            return -1;
        }
    }

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

    public boolean isMember(String teamName) {
        return favorite.name.equals(teamName) || underdog.name.equals(teamName);
    }

    private String applySign(double value) {
        return (value > 0 ? "+" : "") + value;
    }

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
