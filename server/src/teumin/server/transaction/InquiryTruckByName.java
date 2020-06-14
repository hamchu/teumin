package teumin.server.transaction;

import teumin.entity.Bytes;
import teumin.entity.Truck;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.server.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class InquiryTruckByName extends Transaction {
    public InquiryTruckByName(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        String name = data.get(1);

        // return
        Truck truck = null;

        // 조건 검사 : 없음

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from truck where name=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {

                String introduction = resultSet.getString("introduction");
                String explanation = resultSet.getString("explanation");
                String category = resultSet.getString("category");
                Bytes icon = new Bytes(resultSet.getObject("icon", byte[].class));

                truck = new Truck(name, introduction, explanation, category, 1, null, icon);
            }
        }

        data = new Data();
        data.add(truck);
        network.write(data);
    }
}
