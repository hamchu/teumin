package teumin.server.transaction;

import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.server.account.Accounts;
import teumin.server.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LogoutAccount extends Transaction {

    public LogoutAccount(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param : 없음

        // return
        boolean success = false;

        // 조건 검사 : 로그인 아닌 상태면 끊기
        if (account.getId() == null) {
            network.close();
            return;
        }

        // Accounts 처리
        ArrayList<Account> accounts = Accounts.getAccountList();
        synchronized (accounts) {
            account.setId(null);
            account.setType(-1);
            success = true;
        }

        data = new Data();
        data.add(success);
        network.write(data);
    }
}
