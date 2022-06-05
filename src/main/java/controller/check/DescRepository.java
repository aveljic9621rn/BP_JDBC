package controller.check;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DescRepository {

    private JSONParser parser = new JSONParser();
    private ArrayList<JSONObject> rules = new ArrayList<>();

    public ArrayList<JSONObject> getRules() {
        return rules;
    }

    public DescRepository() throws IOException, ParseException {

        Object obj = parser.parse(new FileReader("json.json"));
        JSONObject jsonObject = (JSONObject) obj;

        JSONArray jsonrules = (JSONArray) jsonObject.get("RULES");

        Iterator<JSONObject> iterator = jsonrules.iterator();
        while (iterator.hasNext()){
            rules.add(iterator.next());
        }
  }
}
