package teumin.server.transaction;

import teumin.entity.Category;
import teumin.entity.Truck;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.server.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UpdateTruck extends Transaction {
    public UpdateTruck(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        Truck truck = data.get(1);

        // return
        boolean success = false;

        // 조건 검사 : 일반 사용자 불가능
        if (account.getType() == -1) {
            network.close();
            return;
        }

        // 조건 검사 : 트럭 멤버들 범위 검사
        if (!(

                !truck.getName().replaceAll(" ", "").replaceAll("\t", "").equals("") &&
                        truck.getName().matches("^.{2,16}$") &&
                        truck.getIntroduction().matches("^.{2,32}$") &&
                        (truck.getExplanation().length() >= 2 && truck.getExplanation().length() <= 64) &&
                        truck.getCategory() != null
        )) {
            data = new Data();
            data.add(success);
            network.write(data);
            return;
        }

        // 조건 검사 : 아이콘 null 체크하기
        if (truck.getIcon().toImage() == null) {
            data = new Data();
            data.add(success);
            network.write(data);

            return;
        }

        // 조건 검사 : 카테고리가 지정 카테고리 리스트에 속하지 않으면 끊기
        boolean flag = false;
        for (String category : Category.getCategories()) {
            if (category.equals(truck.getCategory())){
                flag = true;
                break;
            }
        }
        if (!flag) {
            network.close();
            return;
        }

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from truck where name=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, truck.getName());
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                
                // 조건 검사 : 영업자는 본인의 푸드트럭만 수정 가능
                if (account.getType() == 1) {
                    if (!resultSet.getString("owner_id").equals(account.getId())) {
                        network.close();
                        return;
                    }
                }

                String sql2 = "update truck set introduction=?, explanation=?, category=?, icon=? where name=?";
                PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                pstmt2.setString(1, truck.getIntroduction());
                pstmt2.setString(2, truck.getExplanation());
                pstmt2.setString(3, truck.getCategory());
                pstmt2.setObject(4, truck.getIcon().getBytes());
                pstmt2.setString(5, truck.getName());
                pstmt2.executeUpdate();

                success = true;

            }
        }

        data = new Data();
        data.add(success);
        network.write(data);
    }
}
