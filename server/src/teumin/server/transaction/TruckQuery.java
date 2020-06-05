package teumin.server.transaction;

import teumin.entity.Address;
import teumin.entity.Truck;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.database.Database;
import teumin.server.Transaction;
import teumin.server.account.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class TruckQuery extends Transaction {
    public TruckQuery(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        Address address = data.<Address>get(1);
        String category = data.<String>get(2);

        // return
        ArrayList<Truck> trucks = new ArrayList<>();

        // 조건 검사 : 없음

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from truck where proven='1', category=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, category);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String sql2 = "select * from sales_info where truck_name=?";
                PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                pstmt2.setString(1, resultSet.getString("name"));
                ResultSet resultSet2 = pstmt2.executeQuery();
                boolean isTarget = false;
                while (resultSet2.next()) {
                    Date date = ((Date)resultSet2.getObject("date"));
                    /**
                     *
                     *
                     * 하던중..............................
                     *
                     *
                     */
                }
            }
        }

        data = new Data();
        data.add(trucks);
        network.write(data);
    }
}
