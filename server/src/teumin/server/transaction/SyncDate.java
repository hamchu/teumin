package teumin.server.transaction;

import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;

import java.time.LocalDate;

public class SyncDate extends Transaction {
    public SyncDate(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        String truckName = data.get(1);
        LocalDate date = LocalDate.now();

        // return : String truckName

        // 조건 검사 : 없음

        data = new Data();
        data.add(truckName);
        data.add(date);
        network.write(data);
    }
}