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

// TODO: Create a parser interface (or abstract class) which returns a list of games from the XML to be added to the season
public class Parser {
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

    private static String convertStreamToString(InputStream is) {
        return new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
    }
}
