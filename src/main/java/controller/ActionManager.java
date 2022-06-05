package controller;

public class ActionManager {

    private BulkImportAction bulkAction;
    private PrettyActionAction prettyAction;
    private QueryCheckAction queryCheckAction;
    private ResultSetAction resultSetAction;

    public ActionManager() {
        initialise();
    }

    private void initialise() {
        bulkAction = new BulkImportAction();
        prettyAction = new PrettyActionAction();
        queryCheckAction = new QueryCheckAction();
        resultSetAction = new ResultSetAction();
    }

    public BulkImportAction getBulkAction() {
        return bulkAction;
    }

    public PrettyActionAction getPrettyAction() {
        return prettyAction;
    }

    public QueryCheckAction getQueryCheckAction() {
        return queryCheckAction;
    }

    public ResultSetAction getResultSetAction() {
        return resultSetAction;
    }
}
