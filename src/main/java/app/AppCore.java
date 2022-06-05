package app;

import controller.check.Checker;
import database.Database;
import database.DatabaseImplementation;
import database.MYSQLrepository;
import database.settings.Settings;
import database.settings.SettingsImplementation;
import gui.MainFrame;
import gui.table.TableModel;
import lombok.Getter;
import lombok.Setter;
import observer.Notification;
import observer.enums.NotificationCode;
import observer.implementation.PublisherImplementation;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import resource.implementation.InformationResource;
import tree.Tree;
import tree.implementation.TreeImplementation;
import utils.Constants;

import javax.swing.tree.DefaultTreeModel;
import java.io.FileNotFoundException;


@Getter
@Setter
public class AppCore extends PublisherImplementation {

    private Database database;
    private Settings settings;
    private TableModel tableModel;
    private DefaultTreeModel defaultTreeModel;
    private Tree tree;
    private Checker checker;
    private String query;

    public AppCore() throws Exception {
        this.settings = initSettings();
        this.database = new DatabaseImplementation(new MYSQLrepository(this.settings));
        this.tableModel = new TableModel();
        this.tree = new TreeImplementation();
        this.checker = new Checker();


    }

    public void run() throws FileNotFoundException {

        query = MainFrame.getInstance().getTextPane().getText();
        checker.setQuery(query);
        System.out.println("QUERY: " + checker.getQuery());
        if (this.checker.checkStack().isEmpty()){
            database.makeQueryToDatabase(checker.getQuery());
            System.out.println("STEK PRAZAN");

//            System.out.println(checker.getPorukeGreske().get(0).replace("description", "%s", "string"));

        } else

            while(!checker.getErrorStack().isEmpty()) {
                String suggestion;
                String name;
                String desc;
                JSONObject jsonObject = (JSONObject) checker.getErrorStack().pop();
                suggestion =  jsonObject.get("suggestion").toString();
                name = jsonObject.get("name").toString();
                desc = jsonObject.get("description").toString();
                System.out.println("stack: " +  name  + " " + desc + " " + suggestion);

            }


    }

    private Settings initSettings() {
        Settings settingsImplementation = new SettingsImplementation();
        settingsImplementation.addParameter("mysql_ip", Constants.MYSQL_IP);
        settingsImplementation.addParameter("mysql_database", Constants.MYSQL_DATABASE);
        settingsImplementation.addParameter("mysql_username", Constants.MYSQL_USERNAME);
        settingsImplementation.addParameter("mysql_password", Constants.MYSQL_PASSWORD);
        return settingsImplementation;
    }


    public DefaultTreeModel loadResource(){
        InformationResource ir = (InformationResource) this.database.loadResource();
        return this.tree.generateTree(ir);
    }

    public void readDataFromTable(String fromTable){

        tableModel.setRows(this.database.readDataFromTable(fromTable));

        //Zasto ova linija moze da ostane zakomentarisana?
        this.notifySubscribers(new Notification(NotificationCode.DATA_UPDATED, this.getTableModel()));
    }




}
