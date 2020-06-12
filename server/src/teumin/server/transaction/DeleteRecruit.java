package teumin.server.transaction;

import teumin.entity.Item;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.server.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteRecruit extends Transaction {
    public DeleteRecruit(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        Item item = data.get(1);
        // return
        boolean success = false;

        // 조건 검사 : 관리자 권한 아닐 시 접속 끊기
        if (account.getType() != 0) {
            network.close();
            return;
        }

        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "delete from recruit where number=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, (int) data.get(1));
            pstmt.executeUpdate();
            success = true;
            pstmt.close();

            if(success)
            {
                sql = "alter table `recruit` AUTO_INCREMENT=1;set @COUNT = 0;update `recruit` set number = @COUNT:=@COUNT+1;";
                pstmt = connection.prepareStatement(sql);
                pstmt.executeUpdate();
            }
        }

        data = new Data();
        data.add(success);
        network.write(data);
    }
}
