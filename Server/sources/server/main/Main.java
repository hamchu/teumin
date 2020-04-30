package server.main;

import java.sql.*;

public class Main {
    public static void main(String[] args)
    {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/temp?useSSL=false", "root", "apmsetup");
            System.out.println("정상적으로 연결되었습니다.");
        } catch(SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
            e.printStackTrace();
        }

        try {
            if(conn != null)
                conn.close();
        } catch (SQLException e) {}
    }
}
