package footballer.ranking.system;

import footballer.ranking.Rank;
import footballer.ranking.logging.LogEntry;
import footballer.ranking.RankingSystem;
import footballer.structure.Game;
import footballer.structure.Team;
import java.util.List;

public class SelfBased extends RankingSystem {

    public SelfBased(List<Team> teams) {
        super(teams);
    }

    @Override
    protected LogEntry applyGame(Game game) {
        // Determine the favorite and underdog based on which team has the higher rank before any calculations
        Rank favorite = getGreaterRank(game.homeTeam, game.awayTeam);
        Rank underdog = favorite.team == game.homeTeam ? getRank(game.awayTeam.name) : getRank(game.homeTeam.name);

        double percentDiff = percentDiff(favorite, underdog);

        double favoriteInitial = favorite.getValue();
        double underdogInitial = underdog.getValue();

        double favoriteNew = favoriteInitial;
        double underdogNew = underdogInitial;

        double increment;

        if (game.getWinner() == favorite.team) { // Favorite wins
            increment = underdogInitial * percentDiff;
            favoriteNew = favoriteInitial + increment + 1; // Reward points to favorite
            underdogNew = underdogInitial - increment - 1; // Deduct points from underdog
        } else if (game.getWinner() == underdog.team) { // Underdog wins (upset)
            increment = favoriteInitial * percentDiff;
            favoriteNew = favoriteInitial - increment - 1; // Deduct points from favorite
            underdogNew = underdogInitial + increment + 1; // Reward points to underdog
        }

        // Constrain values so that they don't drop below 1, which screws up the rest of the algorithms
        if (favoriteNew < 1) favoriteNew = 1;
        if (underdogNew < 1) underdogNew = 1;

        favorite.setValue(favoriteNew);
        underdog.setValue(underdogNew);

        return new LogEntry(game, favorite.team, underdog.team, favoriteInitial, favoriteNew, underdogInitial, underdogNew);
    }

    protected double percentDiff(Rank rank1, Rank rank2) {
        return Math.abs((rank1.getValue() - rank2.getValue()) / ((rank1.getValue() + rank2.getValue()) / 2));
    }

}
