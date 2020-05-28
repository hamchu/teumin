package teumin.server.transaction;

import teumin.network.Data;
import teumin.network.DataType;
import teumin.network.Network;
import teumin.server.Coclient;
import teumin.server.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;

public class Register extends Transaction {
    public Register(Network network, Statement statement) {
        super(network, statement);
    }

    @Override
    public void execute(Data data)
    {
        try {
            String sqlstr = "select * from user where id='" + data.<String>get(0) + "'";
            ResultSet resultSet = statement.executeQuery(sqlstr);
            boolean success = false;
            if (!resultSet.next()) {
                success = true;

                sqlstr = "insert into 영엽자 values('" + data.<String>get(0) + "', '" + data.<String>get(1) + "', '" + data.<String>get(2) + "', '" + -1 + "')";
                statement.executeUpdate(sqlstr);
            }
            resultSet.close();
            data = new Data(DataType.REGISTER_RESPOND);
            data.add(success);
            network.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
