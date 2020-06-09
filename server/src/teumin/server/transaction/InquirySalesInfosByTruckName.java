package teumin.server.transaction;

import teumin.entity.*;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.server.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class InquirySalesInfosByTruckName extends Transaction {
    public InquirySalesInfosByTruckName(Network network, Account account) { super(network, account); }

    @Override
    public void execute(Data data) throws Exception {
        // param
        String truckName = data.get(1);

        // return
        ArrayList<SalesInfo> salesInfo_list = new ArrayList<>();

        // 조건 검사 : 없음

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from sales_info where truck_name=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, truckName);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                LocalDate date = resultSet.getObject("date", LocalDate.class);
                LocalTime begin = resultSet.getObject("begin", LocalTime.class);
                LocalTime end = resultSet.getObject("end", LocalTime.class);
                String addressName = resultSet.getString("address");
                Double x = resultSet.getDouble("x");
                Double y = resultSet.getDouble("y");

                salesInfo_list.add(new SalesInfo(truckName, date, begin, end, new Address(addressName,x,y)));
            }
        }

        data = new Data();
        data.add(salesInfo_list);
        network.write(data);
    }
}
