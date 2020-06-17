package teumin.server.transaction;

import teumin.server.account.Accounts;
import teumin.server.database.Database;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.network.Data;
import teumin.network.Network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LoginAccount extends Transaction {
    public LoginAccount(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        String id = data.<String>get(1);
        String password = data.<String>get(2);

        // return
        boolean success = false;
        String name = "";
        int type = -1;

        // 조건 검사 : 이미 로그인한 상태면 접속 끊기
        if (account.getId() != null) {
            network.close();
            return;
        }

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select name, type from account where id=? and password=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                success = true;
                name = resultSet.getString("name");
                type = resultSet.getInt("type");
            }
        }
        
        // 중복 접속 확인
        ArrayList<Account> accounts = Accounts.getAccountList();
        synchronized (accounts) {
            for (Account account : accounts) {
                if (account.getId() == null) continue;
                if (account.getId().equals(id)) {
                    success = false;
                    break;
                }
            }

            if (success) {
                account.setId(id);
                account.setType(type);
            }
        }

        data = new Data();
        data.add(success);
        data.add(name);
        data.add(type);
        network.write(data);
    }
}
