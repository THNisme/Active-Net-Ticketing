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
    private Connection conn;

    private DBContext() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbURL = "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=SWP391;"
                    + "user=sa;"
                    + "password=123456;"
                    + "encrypt=true;trustServerCertificate=true;";
            conn = DriverManager.getConnection(dbURL);
            DatabaseMetaData dm = conn.getMetaData();
            System.out.println("");
            System.out.println("---------DBContext information---------");
            System.out.println("Connected to " + dm.getDatabaseProductName());
            System.out.println("Version DBContext: singleton");
            System.out.println("Product version: "
                    + dm.getDatabaseProductVersion());
            System.out.println("Driver name: " + dm.getDriverName());
            System.out.println("Driver version: " + dm.getDriverVersion());
            System.out.println("----------------------------------------");
            System.out.println("");
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
        return conn;
    }
}
