package teumin.server.transaction;

import teumin.entity.Bytes;
import teumin.entity.Item;
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

public class InquiryItemsByTruckName extends Transaction {
    public InquiryItemsByTruckName(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        String truckName = data.get(1);

        // return
        ArrayList<Item> items = new ArrayList<>();

        // 조건 검사 : 없음

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select name from item where truck_name=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, truckName);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                String name = resultSet.getString("name");
                Integer price = resultSet.getInt("price");
                Bytes image = new Bytes(resultSet.getObject("image", byte[].class));
                String introduction = resultSet.getString("introduction");
                String explanation = resultSet.getString("explanation");

                items.add(new Item(truckName, name, price, image, introduction, explanation));
            }
        }

        data = new Data();
        data.add(items);
        network.write(data);
    }
}
