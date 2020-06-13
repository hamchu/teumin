package teumin.server.transaction;

import teumin.entity.*;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.database.Database;
import teumin.server.Transaction;
import teumin.server.account.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class QueryTruck extends Transaction {
    public QueryTruck(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        String category = data.get(1);

        // return
        ArrayList<TruckWithSalesInfo> truckWithSalesInfos = new ArrayList<>();

        // 조건 검사 : 카테고리가 지정 카테고리 리스트에 속하지 않으면 끊기
        boolean flag = false;
        for (String unit : Category.getCategories()) {
            if (unit.equals(category)){
                flag = true;
                break;
            }
        }
        if (!flag) {
            network.close();
            return;
        }

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from truck where proven='1' and category=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, category);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                String name = resultSet.getString("name");
                String introduction = resultSet.getString("introduction");
                String explanation = resultSet.getString("explanation");
                Bytes icon = new Bytes(resultSet.getObject("icon", byte[].class));

                String sql2 = "select * from sales_info where truck_name=?";
                PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                pstmt2.setString(1, name);
                ResultSet resultSet2 = pstmt2.executeQuery();

                boolean isOpen = false;
                LocalTime begin = null;
                LocalTime end = null;
                Address address = null;


                LocalDate nowDate = LocalDate.now();
                LocalTime nowTime = LocalTime.now();
                while (resultSet2.next()) {
                    LocalDate tempDate = resultSet2.getObject("date", LocalDate.class);
                    LocalTime tempBegin = resultSet2.getObject("begin", LocalTime.class);
                    LocalTime tempEnd = resultSet2.getObject("end", LocalTime.class);

                    if (tempDate.equals(nowDate) && nowTime.isAfter(tempBegin) && nowTime.isBefore(tempEnd)) {
                        isOpen = true;
                        begin = tempBegin;
                        end = tempEnd;
                        address = new Address(
                                resultSet2.getString("address"),
                                resultSet2.getDouble("x"),
                                resultSet2.getDouble("y")
                        );

                        break;
                    }
                }

                truckWithSalesInfos.add(new TruckWithSalesInfo(name, introduction, explanation, null, 1, null, icon, isOpen, begin, end, address));
            }
        }

        data = new Data();
        data.add(truckWithSalesInfos);
        network.write(data);
    }
}
