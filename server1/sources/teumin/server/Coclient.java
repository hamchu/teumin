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

                switch (data.getDataType()) // case 쌓이면 가독성 내려감. 나중에 구조 적절히 수정함.
                {
                    case LOGIN_REQUEST: {
                        String sqlstr = "select password, nickname from user where id='" + data.<String>get(0) + "'";
                        ResultSet resultSet = statement.executeQuery(sqlstr);
                        boolean success = false;
                        String nickname = null;
                        while (resultSet.next()) {
                            String password = resultSet.getString(1);
                            if (password.equals(data.<String>get(1))) {
                                success = true;
                                nickname = resultSet.getString(2);
                                break;
                            }
                        }
                        resultSet.close();
                        data = new Data(DataType.LOGIN_RESPOND);
                        data.add(success);
                        data.add(nickname);
                        network.write(data);
                    }
                    break;

                    case REGISTER_REQUEST: { // TODO: { 아이디, 비밀번호, 닉네임 } 최소 조건 검사
                        // 동기화 문제..
                        // 지저분한 코드 문제..
                        String sqlstr = "select * from user where id='" + data.<String>get(0) + "'";
                        ResultSet resultSet = statement.executeQuery(sqlstr);
                        boolean success = false;
                        if (!resultSet.next())
                        {
                            success = true;

                            sqlstr = "insert into user values('" + data.<String>get(0) + "', '" + data.<String>get(1) + "', '" + data.<String>get(2) + "')";
                            statement.executeUpdate(sqlstr);
                        }
                        resultSet.close();
                        data = new Data(DataType.REGISTER_RESPOND);
                        data.add(success);
                        network.write(data);
                    }
                    break;

                    case FOOD_TRUCK_INQUIRY_REQUEST: { // test해볼려고 일단 대충 박아둠.. sorry..
                        String sqlstr = "select * from food_truck where category='" + data.<String>get(0) + "'";
                        ResultSet resultSet = statement.executeQuery(sqlstr);
                        boolean null_point = false;

                        while(resultSet.next())
                        {
                            data = new Data(DataType.FOOD_TRUCK_INQUIRY_RESPOND);
                            data.add(null_point);
                            data.add(resultSet.getString("food_truck_name"));
                            network.write(data);
                        }
                        resultSet.close();
                        data = new Data(DataType.FOOD_TRUCK_INQUIRY_RESPOND);
                        null_point = true;
                        data.add(null_point);
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
