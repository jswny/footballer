package footballer;

import java.util.*;
import footballer.ranking.*;
import footballer.structure.*;
import footballer.ranking.system.*;
import footballer.parse.Parser;

public class Runner {
    public static void main(String[] args) {
        Season season = Parser.parseCurrentStructure(2017, 9);
//		Season season = Parser.parseCurrentStructure(2016, 17);

        String[] teamNames = Utils.espnPreseasonRankings;

        List<Team> teams = season.getTeams();
        Ranking evenPlayRanking = new EvenPlay(teams, 1.0);
        Ranking adjustedWinsRanking = new AdjustedWins(teams);
        Ranking self = new SelfBased(teams);

        evenPlayRanking.generateBaselineRanking(teamNames, 0, 16);
        adjustedWinsRanking.generateBaselineRanking(teamNames, 0, 0);
        self.generateBaselineRanking(teamNames, 0, 16);

        evenPlayRanking.applyGames(season);
        adjustedWinsRanking.applyGames(season);
        self.applyGames(season);

        System.out.println(evenPlayRanking.getLogForTeam("Dolphins"));

//        System.out.println(evenPlayRanking);
        System.out.println(adjustedWinsRanking);
//        System.out.println(self);

//        System.out.println(evenPlayRanking.getCSVData());
        System.out.println(adjustedWinsRanking.getCSVData());
//        System.out.println(self.getCSVData());

    }
}
