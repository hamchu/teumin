package teumin.server.transaction;

import teumin.network.Data;
import teumin.network.DataType;
import teumin.network.Network;
import teumin.server.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends Transaction {
    public Login(Network network, Statement statement) {
        super(network, statement);
    }

    @Override
    public void execute(Data data) {
        try {
            String sqlstr = "select password, nickname from user where id='" + data.<String>get(0) + "'";
            ResultSet resultSet = statement.executeQuery(sqlstr);
            boolean success = false;
            String name = null;
            while (resultSet.next()) {
                String password = resultSet.getString(1);
                if (password.equals(data.<String>get(1))) {
                    success = true;
                    name = resultSet.getString(2);
                    break;
                }
            }
            resultSet.close();
            data = new Data(DataType.LOGIN_RESPOND);
            data.add(success);
            data.add(name);
            network.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
