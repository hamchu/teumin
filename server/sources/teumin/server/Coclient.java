package teumin.server;

import teumin.network.Data;
import teumin.network.DataType;
import teumin.network.Network;
import teumin.server.transaction.FoodTruckInquiry;
import teumin.server.transaction.Login;
import teumin.server.transaction.Register;

import java.sql.ResultSet;
import java.sql.Statement;

public class Coclient implements Runnable {
    protected Network network;
    protected Statement statement;
    private Transaction current_transaction;

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

                switch (data.getDataType()) // case 쌓이면 가독성 내려감. 나중에 구조 적절히 수정함.
                {
                    case LOGIN_REQUEST:
                        current_transaction = new Login(network,statement);
                        current_transaction.execute(data);
                        break;
                    case REGISTER_REQUEST:  // TODO: { 아이디, 비밀번호, 닉네임 } 최소 조건 검사
                        current_transaction = new Register(network,statement);
                        current_transaction.execute(data);
                        break;
                    case FOODTRUCK_INQUIRY_REQUEST:
                        current_transaction = new FoodTruckInquiry(network,statement);
                        current_transaction.execute(data);
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