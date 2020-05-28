package teumin.server;

import teumin.network.Data;
import teumin.network.Network;

import java.sql.Statement;

public abstract class Transaction extends Coclient{
    public Transaction(Network network, Statement statement) {
        super(network, statement);
    }

    public abstract void execute(Data data);
}
