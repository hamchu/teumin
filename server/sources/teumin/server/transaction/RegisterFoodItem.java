package teumin.server.transaction;

import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;

import java.sql.Statement;

public class RegisterFoodItem extends Transaction {

    public RegisterFoodItem(Network network, Statement statement) {
        super(network, statement);
    }

    @Override
    public void execute(Data data) {

    }
}
