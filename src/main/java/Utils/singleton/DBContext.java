/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils.singleton;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

/**
 *
 * @author Tran Hieu Nghia - CE191115
 */
public class DBContext {

    private static DBContext instance;

    private DBContext() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static DBContext getInstance() {
        if (instance == null) {
            instance = new DBContext();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            String dbURL = "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=EventManagementV7;"
                    + "user=sa;"
                    + "password=nd291005;"
                    + "encrypt=true;trustServerCertificate=true;";

            // ❗ Luôn mở mới Connection
            return DriverManager.getConnection(dbURL);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
