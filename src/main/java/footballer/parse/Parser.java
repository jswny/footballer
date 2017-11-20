package footballer.parse;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import footballer.Utils;
import footballer.structure.Season;

/**
 * Parses a source for game information.
 */
public class Parser {

    /**
     * Builds a {@link Season} with the current season structure ({@link Utils#createCurrentStructure(int)}) and fills it in with {@link footballer.structure.Game}s.
     * This method uses the NFL website to grab game data and populate the season.
     * @param seasonYear the year of the season to be created and populated with {@link footballer.structure.Game}s
     * @param upToWeek the last week (inclusive) to populate {@link footballer.structure.Game}s from
     * @return the {@link Season} specified by the year {@code seasonYear}
     * populated with {@link footballer.structure.Game}s from {@link footballer.structure.Week} {@code 1} through {@code upToWeek} (inclusive)
     */
    public static Season parseCurrentStructure(int seasonYear, int upToWeek) {
        Season season = Utils.createCurrentStructure(seasonYear);

        String url = "http://nfl.com/ajax/scorestrip";

        for (int weekNum = 1; weekNum < upToWeek + 1; weekNum++) {
            season.addWeek(weekNum);
            String query = String.format("season=%s&seasonType=%s&week=%s", Integer.toString(seasonYear), "REG", Integer.toString(weekNum));

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc = null;
            try {
                doc = builder.parse(url + "?" + query);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("g");

            for (int i = 0; i < nodeList.getLength(); i++) {
                NamedNodeMap nodeMap = nodeList.item(i).getAttributes();
                String homeTeamName = Utils.upperCaseFirstLetter(nodeMap.getNamedItem("hnn").getNodeValue());
                String awayTeamName = Utils.upperCaseFirstLetter(nodeMap.getNamedItem("vnn").getNodeValue());
                int homeTeamScore = Integer.parseInt(nodeMap.getNamedItem("hs").getNodeValue().equals("") ? "-1" : nodeMap.getNamedItem("hs").getNodeValue());
                int awayTeamScore = Integer.parseInt(nodeMap.getNamedItem("vs").getNodeValue().equals("") ? "-1" : nodeMap.getNamedItem("vs").getNodeValue());
                season.addGame(weekNum, awayTeamName, homeTeamName, awayTeamScore, homeTeamScore);
            }
        }

        return season;
    }
}
