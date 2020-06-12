package teumin.server.transaction;

import teumin.entity.Category;
import teumin.entity.Item;
import teumin.entity.Truck;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.server.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//아이템을 받는다. OK
//영업자인가? OK
//트럭이 영업자 소유인가? OK
//중복되는 이름이 없는가?
//형식은 올바른가? OK

public class RegisterItem extends Transaction {
    public RegisterItem(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        Item item = data.get(1);

        // return
        boolean success = false;

        // 조건 검사 : 영업자 권한 아닐 시 접속 끊기
        if (account.getType() != 1) {
            network.close();
            return;
        }

        // 조건 검사 : 상품 멤버들 범위 검사
        if (!(

                !item.getName().replaceAll(" ", "").replaceAll("\t", "").equals("") &&
                        item.getName().matches("^.{2,16}$") &&
                        item.getIntroduction().matches("^.{2,32}$") &&
                        (item.getExplanation().length() >= 2 && item.getExplanation().length() <= 64)
        )) {
            data = new Data();
            data.add(success);
            network.write(data);
            return;
        }

        // 조건 검사 : 이미지 파일들 null 체크하기
        if (item.getImage().toImage() == null) {
            data = new Data();
            data.add(success);
            network.write(data);
            return;
        }

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from truck where name=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, item.getTruckName());
            ResultSet resultSet = pstmt.executeQuery();

            // 존재하는 트럭인가?
            if (!resultSet.next()) {
                network.close();
                return;
            }

            // 본인 소유인가?
            if (!resultSet.getString("owner_id").equals(account.getId())) {
                network.close();
                return;
            }

            // 중복되는 이름의 상품이 없는가?
            String sql2 = "select * from item where truck_name=? and name=?";
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);
            pstmt2.setString(1, item.getTruckName());
            pstmt2.setString(2, item.getName());
            resultSet = pstmt2.executeQuery();
            if (resultSet.next()) {
                data = new Data();
                data.add(success);
                network.write(data);
                return;
            }

            // 등록
            String sql3 = "insert into " +
                    "item(truck_name, name, price, image, introduction, explanation) " +
                    "values(?,?,?,?,?,?)";
            PreparedStatement pstmt3 = connection.prepareStatement(sql3);
            pstmt3.setString(1, item.getTruckName());
            pstmt3.setString(2, item.getName());
            pstmt3.setInt(3, item.getPrice());
            pstmt3.setObject(4, item.getImage().getBytes());
            pstmt3.setString(5, item.getIntroduction());
            pstmt3.setString(6, item.getExplanation());
            pstmt3.executeUpdate();
            success = true;
        }

        data = new Data();
        data.add(success);
        network.write(data);
    }
}
