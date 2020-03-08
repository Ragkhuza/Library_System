import oracle.jdbc.OraclePreparedStatement;

import java.sql.*;
import java.text.SimpleDateFormat;

public class DBProcedureHelper extends DBConnection {
    private static Connection conn;
    public final static int EXECUTE_QUERY = 100, EXECUTE_UPDATE = 101;
    public final static int NO_OUT_PROCEDURE = 0;

    // runs a procedure
    /*public static ResultSet runProcedure(String sql, int procedureType, int executeType) {
        String final_sql = "";
        ResultSet final_res = null;

        switch (procedureType) {
            case NO_OUT_PROCEDURE:
                final_sql = "BEGIN " + sql + "; END;";
                break;
        }

        try {
            PreparedStatement pst = conn.prepareStatement(final_sql);

            if (executeType == EXECUTE_UPDATE)
                pst.executeUpdate();
            else if (executeType == EXECUTE_QUERY)
                final_res = pst.executeQuery();
            else
                System.out.println("[QueryBuilder.java] ERROR! EXECUTE TYPE MISMATCH");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[QueryBuilder.java] ERROR while running " + final_sql);
        }

        return final_res;

    }*/

    public static int borrowBook(int loginID, int bookID, String date) {
        PreparedStatement pst = null;
        conn = DBConnection.getConnection();
        int res = 0;
        String sql = "BEGIN borrBookTransact(?, ?, TO_DATE(? ,'MM/DD/YYYY')); END;";
        String comp_sql = "Excuting: BEGIN borrBookTransact(" + loginID + ", " + bookID + ", " + "TO_DATE(" + date + " ,'MM/DD/YYYY')); END;";

        if (conn != null) {
            try {
                pst = conn.prepareStatement(sql);
                pst.setInt(1, loginID);
                pst.setInt(2, bookID);
                pst.setString(3, date);

                System.out.println("[DBProcedureHelper.java] Executing: " + comp_sql);
                res = pst.executeUpdate();
            } catch (Exception e) {
                System.out.println("[DBProcedureHelper.java] Error:" + e.getMessage());
            }
        } else {
            NotificationManager.Error("[DBProcedureHelper.java] Connection Error");
        }


        return res;
    }
}
