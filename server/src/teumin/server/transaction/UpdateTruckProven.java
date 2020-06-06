package teumin.server.transaction;

import teumin.entity.Truck;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.server.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateTruckProven extends Transaction {
    public UpdateTruckProven(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        String name = data.get(1);
        int proven = data.get(2);

        // return
        boolean success = false;

        // 조건 검사 : 관리자 외 불가능
        if (account.getType() != 0) {
            network.close();
            return;
        }

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from truck where name=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {

                String sql2 = "update truck set proven=? where name=?";
                PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                pstmt2.setInt(1, proven);
                pstmt2.setString(2, name);
                pstmt2.executeUpdate();

                success = true;

            }
        }

        data = new Data();
        data.add(success);
        network.write(data);
    }
}
