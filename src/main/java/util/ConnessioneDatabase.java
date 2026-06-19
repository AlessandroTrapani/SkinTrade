package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/skintrade";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL non trovato.");
            e.printStackTrace();
        }
    }

    public static Connection getConnessione() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}