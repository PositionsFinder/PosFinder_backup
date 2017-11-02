package de.positionsfinder.posfinder.database;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Created by User on 02.11.2017.
 * For further information have a look at:
 * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-statements.html
 */

public class JDBC_Connector {

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public JDBC_Connector() {

        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch(ClassNotFoundException | InstantiationException | IllegalAccessException ie){
            Log.e("ERR", "JDBC driver could not be loaded");

        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://10.22.62.29:3306/haider_db1", "haider","haider_edv");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM teststable");

            if (stmt.execute("SELECT * FROM teststable")) {
                rs = stmt.getResultSet();
            }

            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            while(rs.next()){
                for(int i = 1; i < numberOfColumns+1; i++) {
                    Log.d("DGB","Column: " + rsmd.getColumnName(i) + ", Content: " + rs.getString(i));
                }
            }

        } catch (SQLException e) {

            //e.printStackTrace();
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());

        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }

        }
    }
}