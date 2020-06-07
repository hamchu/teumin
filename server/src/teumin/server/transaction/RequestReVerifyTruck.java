package teumin.server.transaction;

import teumin.entity.Bytes;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.server.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RequestReVerifyTruck extends Transaction {
    public RequestReVerifyTruck(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        String name = data.get(1);
        Bytes evidence = data.get(2);

        // return
        boolean success = false;

        // 조건 검사 : 영업자 외 불가능
        if (account.getType() != 1) {
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
            
            // 조건 검사 : 존재하지 않는 푸드트럭 이름, 불량 클라이언트
            if (!resultSet.next()) {
                network.close();
                return;
            }
            else {
                // 조건 검사 : 본인 소유만 신청 가능, 아닐 시 불량 클라이언트
                if (!resultSet.getString("owner_id").equals(account.getId())) {
                    network.close();
                    return;
                }
                // 조건 검사 : 거절된 상태만 신청 가능, 아닐 시 불량 클라이언트
                if (resultSet.getInt("proven") != -1) {
                    network.close();
                    return;
                }

                String sql2 = "update truck set proven=?, evidence=? where name=?";
                PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                pstmt2.setInt(1, 0);
                pstmt2.setObject(2, evidence.getBytes());
                pstmt2.setString(3, name);
                pstmt2.executeUpdate();

                success = true;
            }
        }

        data = new Data();
        data.add(success);
        network.write(data);
    }
}
