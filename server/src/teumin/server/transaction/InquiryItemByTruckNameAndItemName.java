package teumin.server.transaction;

import teumin.entity.Bytes;
import teumin.entity.Item;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.server.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class InquiryItemByTruckNameAndItemName extends Transaction {
    public InquiryItemByTruckNameAndItemName(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        String truckName = data.get(1);
        String itemName = data.get(2);

        // return
        Item item = null;

        // 조건 검사 : 없음

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from item where truck_name=? and name=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, truckName);
            pstmt.setString(2, itemName);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {

                Integer price = resultSet.getInt("price");
                Bytes image = new Bytes(resultSet.getObject("image", byte[].class));
                String introduction = resultSet.getString("introduction");
                String explanation = resultSet.getString("explanation");

                item = new Item(truckName, itemName, price, image, introduction, explanation);
            }
        }

        data = new Data();
        data.add(item);
        network.write(data);
    }
}
