package teumin.server;

import teumin.server.account.Account;
import teumin.network.Data;
import teumin.network.Network;

public abstract class Transaction {
    protected Network network;
    protected Account account;

    public Transaction(Network network, Account account) {
        this.network = network;
        this.account = account;
    }

    public abstract void execute(Data data) throws Exception;
}
