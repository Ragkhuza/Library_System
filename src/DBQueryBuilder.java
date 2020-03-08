import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * NOTE:
 * The heart of the my messengers db interaction
 * This is the single class responsible for querying in database
 * One of the hardest thing Doggo built
 *
*/
class DBQueryBuilder extends DBConnection {
    private static String query = "";
    private static ResultSet resultSet = null;
    private static boolean connectionStarted = false;

    // For doing manual query
    public static void rawQuery(String q) {
        query = q;
    }

    // get result
    public static ResultSet get() {
        runQuery();
        return resultSet;
    }

    // get result
    public static ResultSet get(String table) {
        if (query.isEmpty()) {
            query = "SELECT * FROM " + table;
        }

        runQuery();
        return resultSet;
    }

    // Count result
    public static void count(String table, String alias) {
        if (query.isEmpty()) {
            query = "SELECT count(*) AS " + alias + " FROM " + table;
        }

        query += " ";
    }

    /**
     * [SYNTAX]
     * HashMap<String, Object> cols = new HashMap<String, Object>();
     * cols.put("username", "jeff");
     * cols.put("password", "password");
     * cols.put("first_name", "Jeff");
     * cols.put("last_name", "Bezos");
     * cols.put("activated", 1);
     * insert("users", cols);
     */
    public static boolean insert(String table, HashMap<String, Object> columns) {
        query += "INSERT INTO " + table + " ";
        query += "(";
        int counter = 0;
        for (String i : columns.keySet()) {
            counter++;
            query += i;
            if (counter < columns.size()) query += ", ";
        }

        query += ") VALUES (";

        counter = 0;
        for (Object i : columns.values()) {
            counter++;
            query += "'" + i + "'";
            if (counter < columns.size()) query += ", ";
        }
        query += ") ";

        return runManipulateQuery("INSERT");
    }

    /**
     * [Syntax]
     * String cols[] = {"user_id", "username"};
     * select(cols);
    */
    public static void select(String columns[]){
        if (query.isEmpty())
            query += "SELECT " + columns[0];
        /*else
            throw new NotEmptyQueryException("SELECT");*/

        for (int i = 1; i < columns.length ; i++) {
            if (i < columns.length) query += ", ";
            query += columns[i];
        }

        query += " ";
    }

    /**
     * [Syntax]
     * String cols[] = {"user_id", "username"};
     * select(cols);
     */
    public static void selectDistinct(String columns[]){
        if (query.isEmpty())
            query += "SELECT DISTINCT " + columns[0];
        /*else
            throw new NotEmptyQueryException("SELECT");*/

        for (int i = 1; i < columns.length ; i++) {
            if (i < columns.length) query += ", ";
            query += columns[i];
        }

        query += " ";
    }

    /**
     * [SYNTAX]
     * HashMap<String, Object> updateFields = new HashMap<String, Object>();
     * updateFields.put("username", "sakas");
     * String conditions = "user_id = 1";
     * update("users", updateFields, conditions);
     */
    public static boolean update(String table, HashMap<String, Object> columns, String conditions) {
        query += "UPDATE " + table + " ";
        query += "SET ";
        int counter = 0;
        for (String i : columns.keySet()) {
            counter++;
            query+= i + " = " + "'" + columns.get(i) + "'";
            if (counter < columns.size()) query+= ", ";
        }
        query += " WHERE " + conditions;

        return runManipulateQuery("Update");
    }

    /**
     * [Syntax]
     * from("users");
     */
    public static void from(String table) {
        query += "FROM " + table + " ";
    }

    /**
     * [Syntax]
     * where("user_id > 1");
     * where("user_id > 1 AND username = john");
     */
    public static void where(String condition) {
        query += "WHERE " + condition + " ";
    }

    /**
     * [Syntax]
     * delete("users", "user_id = 3");
     */
    public static boolean delete(String table, String conditions) {
        query += "DELETE FROM " + table + " ";
        query += "WHERE " + conditions + " ";

        return runManipulateQuery("Delete");
    }

    /**
     * [Syntax]
     * orderby("created_at");
     */
    public static void orderBy(String column) {
        query += "ORDER BY " + column + " ASC ";
    }

    /**
     * [Syntax]
     * orderby("created_at", "DESC");
     */
    public static void orderBy(String column, String direction) {
        query += "ORDER BY " + column + " ";
        query += direction + " ";
    }
    /**
     * Executes Update Query
     */
    public static boolean runManipulateQuery(String statement) {
        boolean success = false;
        try {
            if (statement.isEmpty())
                NotificationManager.Error("[QueryBuilder.java]" + statement);
            query+= ";";
            if(!connectionStarted) {
                getConnection();
                connectionStarted = true;
            }
            System.out.println("[QueryBuilder.java] Query: " + query);
            Statement stmt = conn.createStatement();
            int resultStatus = stmt.executeUpdate(query);
            System.out.println("[QueryBuilder.java]" + statement + " Result: " + resultStatus);
            if (resultStatus == 1) {
                success = true;
                System.out.println("[QueryBuilder.java]Data Successfully " + statement);
            } else {
                if (statement.equals("Update")) {
                    System.out.println("[QueryBuilder.java]ERROR " + statement + "");
                } else if(statement.equals("Delete")) {
                    System.out.println("[QueryBuilder.java]No " + statement + "ed Row");
                } else if(statement.equals("INSERT")) {
                    System.out.println("[QueryBuilder.java]No " + statement + "ed Row");
                }
            }

            query = ""; // reset query
            System.out.println("[QueryBuilder.java]Query has been reset");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[QueryBuilder.java] ERROR! " + e.getMessage());
        }

        return success;
    }

    /**
     * Executes Query
     * Doesn't work with Update Statements
     */
    public static void runQuery() {
        try {
            query+= ";";
            if(!connectionStarted) {
                getConnection();
                connectionStarted = true;
            }
            System.out.println("[QueryBuilder.java] Query: " + query);
            Statement stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);
            query = ""; // reset query
            System.out.println("[QueryBuilder.java]Query has been reset");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[QueryBuilder.java] ERROR! " + e.getMessage());
        }
    }

    // returns compiled Query (useful for debugging) without executing the statement
    public static String getCompiledQuery() {
        return query;
    }
}
