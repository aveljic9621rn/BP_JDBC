package controller.check;

import controller.check.rules.Rules;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Checker {

    private Stack<JSONObject> errorStack;
    private ArrayList<Rules> rules = new ArrayList<Rules>();
    private String query;
    private DescRepository descRepository;
    private ArrayList<JSONObject> errorMessages;

    public Checker() throws IOException, ParseException {
        this.descRepository = new DescRepository();
        this.errorMessages = descRepository.getRules();
        this.errorStack = new Stack();
        this.rules.add(new Rules());
    }

    public Checker(String query) {
        this.errorStack = new Stack();
        this.query = query;
    }

    public Stack checkStack() throws FileNotFoundException {

        for (Rules r: rules){

            for(JSONObject jsonObject : errorMessages) {
                if(r.check(query).equalsIgnoreCase((String) jsonObject.get("name"))) {
                    errorStack.push(jsonObject);
                    break;
                }
            }

        }

        return this.errorStack;
    }

    public Stack getErrorStack() {
        return errorStack;
    }

    public ArrayList<Rules> getRules() {
        return rules;
    }

    public String getQuery() {
        return query;
    }

    public DescRepository getDescRepository() {
        return descRepository;
    }


    public void setRules(ArrayList<Rules> rules) {
        this.rules = rules;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setDescRepository(DescRepository descRepository) {
        this.descRepository = descRepository;
    }

    public void setErrorStack(Stack<JSONObject> errorStack) {
        this.errorStack = errorStack;
    }

    public ArrayList<JSONObject> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(ArrayList<JSONObject> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
