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

public class HandleTruckName extends Transaction {
    public HandleTruckName(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        String truckName = data.get(1);

        // return : String truckName

        // 조건 검사 : 없음

        data = new Data();
        data.add(truckName);
        network.write(data);
    }
}
