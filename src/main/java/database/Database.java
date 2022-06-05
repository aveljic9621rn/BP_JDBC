package database;

import resource.DBNode;
import resource.data.Row;

import java.util.List;

public interface Database{

    DBNode loadResource();

    void insertDataToTable(String name, String values, String columns);
    void makeQueryToDatabase(String query);

    List<Row> readDataFromTable(String tableName);


}
