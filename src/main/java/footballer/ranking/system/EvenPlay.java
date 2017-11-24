package footballer.ranking.system;

import java.util.List;
import footballer.ranking.Rank;
import footballer.ranking.RankLogEntry;
import footballer.ranking.RankingSystem;
import footballer.structure.Game;
import footballer.structure.Team;

public class EvenPlay extends RankingSystem {
    private final double dampener;

    public EvenPlay(List<Team> teams, double dampener) {
        super(teams);
        this.dampener = dampener;
    }

    @Override
    protected RankLogEntry applyGame(Game game) {
        // Determine the favorite and underdog based on which team has the higher rank before any calculations
        Rank favorite = getGreaterRank(game.homeTeam, game.awayTeam);
        Rank underdog = favorite.team == game.homeTeam ? getRank(game.awayTeam.name) : getRank(game.homeTeam.name);

        double ratio1 = underdog.getValue() / favorite.getValue();
        double ratio2 = 1 - ratio1;

        double higherRatio = ratio1 > ratio2 ? ratio1 : ratio2;
        double lowerRatio = 1 - higherRatio;

        double favoriteInitial = favorite.getValue();
        double underdogInitial = underdog.getValue();

        double favoriteNew = favoriteInitial;
        double underdogNew = underdogInitial;

        double diff = favorite.getValue() - underdog.getValue();

        double increment = dampener;

        if (game.getWinner() == favorite.team) { // Favorite wins
            increment *= lowerRatio * diff;
            favoriteNew = favoriteInitial + increment + 1; // Reward points to favorite
            underdogNew = underdogInitial - increment - 1; // Deduct points from underdog
        } else if (game.getWinner() == underdog.team) { // Underdog wins (upset)
            increment *= higherRatio * diff;
            favoriteNew = favoriteInitial - increment - 1; // Deduct points from favorite
            underdogNew = underdogInitial + increment + 1; // Reward points to underdog
        }

        // Constrain values so that they don't drop below 1, which screws up the rest of the algorithms
        if (favoriteNew < 1) favoriteNew = 1;
        if (underdogNew < 1) underdogNew = 1;

        favorite.setValue(favoriteNew);
        underdog.setValue(underdogNew);

        return new RankLogEntry(game, favorite.team, underdog.team, favoriteInitial, favoriteNew, underdogInitial, underdogNew);
    }
}
