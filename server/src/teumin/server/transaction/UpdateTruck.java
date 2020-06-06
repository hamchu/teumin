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

public class UpdateTruck extends Transaction {
    public UpdateTruck(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        Truck truck = data.get(1);

        // return
        boolean success = false;

        // 조건 검사 : 일반 사용자 불가능
        if (account.getType() == -1) {
            network.close();
            return;
        }

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from truck where name=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, truck.getName());
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                
                // 조건 검사 : 영업자는 본인의 푸드트럭만 수정 가능
                if (account.getType() == 1) {
                    if (!resultSet.getString("owner_id").equals(account.getId())) {
                        network.close();
                        return;
                    }
                }

                String sql2 = "update truck set introduction=?, explanation=?, category=?, icon=?";
                PreparedStatement pstmt2 = connection.prepareStatement(sql2);

                /**
                 *
                 *
                 *
                 *
                 */

            }
        }

        data = new Data();
        data.add(success);
        network.write(data);
    }
}
