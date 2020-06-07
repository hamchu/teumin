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

/**
 *
 * 테스트 not yet
 *
 */
public class RegisterTruck extends Transaction {
    public RegisterTruck(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        Truck truck = data.get(1);

        // return
        boolean success = false;

        // 조건 검사 : 영업자 권한 아닐 시 접속 끊기
        if (account.getType() != 1) {
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

        // 조건 검사 : 이미지 파일들 null 체크하기
        if (truck.getIcon().toImage() == null || truck.getEvidence().toImage() == null) {
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
            if (!resultSet.next()) {

                String sql2 = "insert into " +
                        "truck(owner_id, name, introduction, explanation, category, evidence, proven, icon) " +
                        "values(?,?,?,?,?,?,'0',?)";
                PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                pstmt2.setString(1, account.getId());
                pstmt2.setString(2, truck.getName());
                pstmt2.setString(3, truck.getIntroduction());
                pstmt2.setString(4, truck.getExplanation());
                pstmt2.setString(5, truck.getCategory());
                pstmt2.setObject(6, truck.getEvidence().getBytes());
                pstmt2.setObject(7, truck.getIcon().getBytes());
                pstmt2.executeUpdate();

                success = true;
            }
        }
        
        data = new Data();
        data.add(success);
        network.write(data);
    }
}
