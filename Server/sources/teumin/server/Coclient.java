package teumin.server;

import teumin.network.Data;
import teumin.network.DataType;
import teumin.network.Network;

import java.sql.ResultSet;
import java.sql.Statement;

public class Coclient implements Runnable {
    private Network network;
    private Statement statement;

    public Coclient(Network network, Statement statement) {
        this.network = network;
        this.statement = statement;
    }

    public void close() throws Exception {
        network.close();
        statement.close();
    }

    @Override
    public void run() {
        try {
            boolean state = true;

            while (state) {
                Data data = network.read();

                switch (data.getDataType())
                {
                    case LOGIN_REQUEST:
                    {
                        String sqlstr = "select password from user where id='" + (String)data.getObject(0) + "'";
                        ResultSet resultSet = statement.executeQuery(sqlstr);
                        boolean flag = false;
                        while (resultSet.next()) {
                            String password = resultSet.getString(1);
                            if (password.equals((String)data.getObject(1)))
                                flag = true;
                        }
                        resultSet.close();
                        data = new Data(DataType.LOGIN_RESPOND);
                        data.addObject(flag);
                        network.write(data);
                    }
                        break;
                    default:
                        state = false;
                        break;
                }
            }

            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
