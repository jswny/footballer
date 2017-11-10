package footballer.ranking;

public class EntryPair {
    public final int week;
    public final RankLogEntry entry;

    public EntryPair(int week, RankLogEntry entry) {
        this.week = week;
        this.entry = entry;
    }

//    public static List<Integer> getWeekNumbers(List<EntryPair> pairs) {
//        List<Integer> result = new ArrayList<>();
//        for (EntryPair pair : pairs) {
//            result.add(pair.week);
//        }
//        return result;
//    }

    @Override
    public String toString() {
        return "Week " + week + ": \n" + entry;
    }
}
