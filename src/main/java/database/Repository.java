package database;

import resource.DBNode;
import resource.data.Row;

import java.util.List;

public interface Repository {

    DBNode getSchema();
    void insertData(String table, String values, String columnNames);
    void makeQuery(String query);

    List<Row> get(String from);
}
