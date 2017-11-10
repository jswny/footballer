package footballer.ranking.system;

import footballer.ranking.Rank;
import footballer.ranking.RankLogEntry;
import footballer.ranking.Ranking;
import footballer.structure.Game;
import footballer.structure.Team;
import java.util.List;

public class AdjustedWins extends Ranking {
    public AdjustedWins(List<Team> teams) {
        super(teams);
    }

    public RankLogEntry applyGame(Game game) {
        // Determine the favorite and underdog based on which team has the higher rank before any calculations
        Rank favorite = getGreaterRank(game.homeTeam, game.awayTeam);
        Rank underdog = favorite.team == game.homeTeam ? getRank(game.awayTeam.name) : getRank(game.homeTeam.name);

        double higherRatio = 0.57;
        double lowerRatio = 1 - higherRatio;

        double favoriteInitial = favorite.getValue();
        double underdogInitial = underdog.getValue();

        double favoriteNew = favoriteInitial;
        double underdogNew = underdogInitial;

        if (game.getWinner() == favorite.team) { // Favorite wins
            if (game.homeTeam == favorite.team) {
                favoriteNew = favoriteInitial + lowerRatio;
                underdogNew = underdogInitial - lowerRatio;
            } else {
                favoriteNew = favoriteInitial + higherRatio;
                underdogNew = underdogInitial - higherRatio;
            }
        } else if (game.getWinner() == underdog.team) { // Underdog wins (upset)
            if (game.homeTeam == underdog.team) {
                favoriteNew = favoriteInitial - lowerRatio;
                underdogNew = underdogInitial + lowerRatio;
            } else {
                favoriteNew = favoriteInitial - higherRatio;
                underdogNew = underdogInitial + higherRatio;
            }
        }

        favorite.setValue(favoriteNew);
        underdog.setValue(underdogNew);

        return new RankLogEntry(game, favorite.team, underdog.team, favoriteInitial, favoriteNew, underdogInitial, underdogNew);
    }
}
