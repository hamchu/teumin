package teumin.server.transaction;

import teumin.entity.Item;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.server.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


//아이템을 받는다.
//영업자인가?
//트럭이 영업자 소유인가?
//해당 아이템 존재하는가?
public class DeleteItem extends Transaction {
    public DeleteItem(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        Item item = data.get(1);

        // return
        boolean success = false;

        // 조건 검사 : 영업자 권한 아닐 시 접속 끊기
        if (account.getType() != 1) {
            network.close();
            return;
        }

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from truck where name=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, item.getTruckName());
            ResultSet resultSet = pstmt.executeQuery();

            // 존재하는 트럭인가?
            if (!resultSet.next()) {
                network.close();
                return;
            }

            // 본인 소유인가?
            if (!resultSet.getString("owner_id").equals(account.getId())) {
                network.close();
                return;
            }

            // 존재하는 상품인가?
            String sql2 = "select * from item where truck_name=? and name=?";
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);
            pstmt2.setString(1, item.getTruckName());
            pstmt2.setString(2, item.getName());
            resultSet = pstmt.executeQuery();
            if (!resultSet.next()) {
                network.close();
                return;
            }

            // 삭제
            String sql3 = "delete from item where truck_name=? and name=?";
            PreparedStatement pstmt3 = connection.prepareStatement(sql3);
            pstmt3.setString(1, item.getTruckName());
            pstmt3.setString(2, item.getName());
            pstmt3.executeUpdate();
            success = true;
        }

        data = new Data();
        data.add(success);
        network.write(data);
    }
}
