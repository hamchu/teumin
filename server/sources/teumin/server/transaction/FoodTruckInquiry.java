package teumin.server.transaction;

import teumin.network.Data;
import teumin.network.DataType;
import teumin.network.Network;
import teumin.server.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;

public class FoodTruckInquiry extends Transaction {
    public FoodTruckInquiry(Network network, Statement statement) {
        super(network, statement);
    }

    @Override
    public void execute(Data data) {
        try {
            String sqlstr = "select * from food_truck where category='" + data.<String>get(0) + "'";
            ResultSet resultSet = statement.executeQuery(sqlstr);
            boolean null_point = false;

            while(resultSet.next())
            {
                data = new Data(DataType.FOODTRUCK_INQUIRY_RESPOND);
                data.add(null_point);
                data.add(resultSet.getString("food_truck_name"));
                network.write(data);
            }
            resultSet.close();
            data = new Data(DataType.FOODTRUCK_INQUIRY_RESPOND);
            null_point = true;
            data.add(null_point);
            network.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
