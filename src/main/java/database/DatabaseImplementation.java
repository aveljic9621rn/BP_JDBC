package database;

import lombok.AllArgsConstructor;
import lombok.Data;
import resource.DBNode;
import resource.data.Row;

import java.util.List;

@Data
@AllArgsConstructor
public class DatabaseImplementation implements Database {

    private Repository repository;


    @Override
    public DBNode loadResource() {
        return repository.getSchema();
    }


    @Override
    public List<Row> readDataFromTable(String tableName) {
        return repository.get(tableName);
    }

    @Override
    public void insertDataToTable(String name, String values, String columns) {
        repository.insertData(name, values, columns);
    }

    @Override
    public void makeQueryToDatabase(String query) {
        repository.makeQuery(query);
    }


}
