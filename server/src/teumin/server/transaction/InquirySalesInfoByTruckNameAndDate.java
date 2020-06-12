package teumin.server.transaction;

import teumin.entity.Address;
import teumin.entity.SalesInfo;
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

public class InquirySalesInfoByTruckNameAndDate extends Transaction {
    public InquirySalesInfoByTruckNameAndDate(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        String truckName = data.get(1);
        LocalDate date = (LocalDate) data.get(2);

        // return
        SalesInfo salesInfo = null;

        // 조건 검사 : 없음

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from sales_info where truck_name=? and date=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, truckName);
            pstmt.setObject(2, date);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                LocalTime begin = resultSet.getObject("begin", LocalTime.class);
                LocalTime end = resultSet.getObject("end", LocalTime.class);
                String addressName = resultSet.getString("address");
                Double x = resultSet.getDouble("x");
                Double y = resultSet.getDouble("y");

                salesInfo = new SalesInfo(truckName, date, begin, end, new Address(addressName,x,y));
            }
        }

        data = new Data();
        data.add(salesInfo);
        network.write(data);
    }
}
