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
import java.util.ArrayList;

public class InquiryTrucksNotApproved extends Transaction {
    public InquiryTrucksNotApproved(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param : 없음

        // return
        ArrayList<Truck> trucks = new ArrayList<>();

        // 조건 검사 : 관리자가 아니면 불가능
        if (account.getType() != 0) {
            network.close();
            return;
        }

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select name from truck where proven=0";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                String name = resultSet.getString("name");

                trucks.add(new Truck(name, null, null, null, 0, null, null));

            }
        }

        data = new Data();
        data.add(trucks);
        network.write(data);
    }
}
