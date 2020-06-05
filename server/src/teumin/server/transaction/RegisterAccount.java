package teumin.server.transaction;

import teumin.network.Data;
import teumin.network.Network;
import teumin.server.database.Database;
import teumin.server.Transaction;
import teumin.server.account.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterAccount extends Transaction {
    public RegisterAccount(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        String id = data.<String>get(1);
        String password = data.<String>get(2);
        String name = data.<String>get(3);

        // return
        boolean success = false;

        // 조건 검사 : 이미 로그인 상태면 접속 끊기
        if (account.getId() != null) {
            network.close();

            return;
        }

        // 조건 검사 : 아이디는 영어로 시작하는 4~16 비밀번호는 4~32 이름은 한글 2~16
        if (!(
        id.matches("^[a-zA-Z]{1}[a-zA-Z0-9]{3,15}$") &&
        password.matches("^[a-zA-Z0-9]{4,32}$") &&
        name.matches("^[가-힣]{2,16}$")
        )) {
            data = new Data();
            data.add(success);
            network.write(data);

            return;
        }

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from account where id=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (!resultSet.next()) {
                success = true;

                String sql2 = "insert into account(id, password, name, type) values(?,?,?,'1')";
                PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                pstmt2.setString(1, id);
                pstmt2.setString(2, password);
                pstmt2.setString(3, name);
                pstmt2.executeUpdate();
            }
        }

        data = new Data();
        data.add(success);
        network.write(data);
    }
}
