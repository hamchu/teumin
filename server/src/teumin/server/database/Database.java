package teumin.server.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 데이터베이스 싱글톤
 */
public class Database {
    private static Connection connection;

    public static void initialize(String url, String user, String password) throws Exception {
        connection = DriverManager.getConnection(url, user, password);
    }

    public static Connection getConnection() {
        return connection;
    }
}
