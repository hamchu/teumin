package teumin.server;

import teumin.server.account.Account;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.account.Accounts;

import java.lang.reflect.Constructor;

public class Coclient extends Thread {
    private Network network;
    private Account account;

    public Coclient(Network network, Account account) {
        this.network = network;
        this.account = account;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Data data = network.read();
                Class[] args = new Class[] {Network.class, Account.class};
                Constructor constructor = Class.forName("teumin.server.transaction." + data.<String>get(0)).getConstructor(args);
                Transaction tracsaction = (Transaction)constructor.newInstance(network, account);
                tracsaction.execute(data);
            }
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

        try {
            network.close();
        } catch (Exception ignore) {
        }

        Accounts.remove(account);
    }
}
