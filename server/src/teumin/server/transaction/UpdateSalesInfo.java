package teumin.server.transaction;

import teumin.entity.SalesInfo;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.server.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateSalesInfo extends Transaction {
    public UpdateSalesInfo(Network network, Account account) { super(network, account); }

    @Override
    public void execute(Data data) throws Exception {
        // param
        SalesInfo salesInfo = data.get(1);

        // return
        boolean success = false;

        // 조건 검사 : 영업자 권한 아닐 시 접속 끊기
        if (account.getType() != 1) {
            network.close();
            return;
        }
        // 조건 검사 : 주소 길이 검사, 입력란 누락 검사
        if (!(
                salesInfo.getAddress() !=null &&
                        salesInfo.getAddressName().matches("^.{2,256}$") &&
                        salesInfo.getDate() !=null &&
                        salesInfo.getBegin() !=null &&
                        salesInfo.getEnd() !=null &&
                        salesInfo.getBegin().compareTo(salesInfo.getEnd())<0
        )) {

            data = new Data();
            data.add(success);
            network.write(data);
            return;
        }

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from truck where name=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, salesInfo.getTruckName());
            ResultSet resultSet = pstmt.executeQuery();

            // 존재하는 트럭인가?
            if (!resultSet.next()) {
                network.close();
                return;
            }

            // 해당 영업일정이 존재하는가?
            String sql2 = "select * from sales_info where truck_name=? and date=?";
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);
            pstmt2.setString(1, salesInfo.getTruckName());
            pstmt2.setObject(2, salesInfo.getDate());

            resultSet = pstmt2.executeQuery();
            if (!resultSet.next()) {
                data = new Data();
                data.add(success);
                network.write(data);
                return;
            }

            // 수정
            String sql3 = "update sales_info set begin=?, end=?, address=?, x=?, y=? where truck_name=? and date=?";
            PreparedStatement pstmt3 = connection.prepareStatement(sql3);
            pstmt3.setObject(1, salesInfo.getBegin());
            pstmt3.setObject(2, salesInfo.getEnd());
            pstmt3.setString(3, salesInfo.getAddressName());
            pstmt3.setDouble(4, salesInfo.getX());
            pstmt3.setDouble(5, salesInfo.getY());
            pstmt3.setString(6, salesInfo.getTruckName());
            pstmt3.setObject(7, salesInfo.getDate());

            pstmt3.executeUpdate();
            success = true;
        }

        data = new Data();
        data.add(success);
        network.write(data);
    }
}