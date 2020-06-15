package teumin.server.transaction;

import teumin.entity.Recruit;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.server.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterRecruit extends Transaction {
    public RegisterRecruit(Network network, Account account) { super(network, account); }

    @Override
    public void execute(Data data) throws Exception {
        // param
        Recruit recruit = data.get(1);

        // return
        boolean success = false;

        // 조건 검사 : 관리자 권한 아닐 시 접속 끊기
        if (account.getType() != 0) {
            network.close();
            return;
        }

        //조건 검사 : 각 타입별 형식 및 이름은 2~16자, 주소는 2~64자, 설명은 2~64자, url은 3~160자, 날짜 순서 올바른지 여부
        if (!(
                !recruit.getName().replaceAll(" ", "").replaceAll("\t", "").equals("") &&
                        recruit.getName().matches("^.{2,32}$") &&
                        recruit.getAddress().matches("^.{2,128}$") &&
                        (recruit.getExplanation().length() >= 2 && recruit.getExplanation().length() <= 256) &&
                        recruit.getReference_url().matches("^.{3,256}$") &&
                        recruit.getRecruit_begin() != null &&
                        recruit.getRecruit_end() != null &&
                        recruit.getSales_begin() != null &&
                        recruit.getSales_end() != null &&
                        (recruit.getSales_begin().compareTo(recruit.getSales_end())<=0) &&
                        (recruit.getRecruit_begin().compareTo(recruit.getRecruit_end())<=0)
        )) {
            data = new Data();
            data.add(success);
            network.write(data);
            return;
        }

        Connection connection = Database.getConnection();
        synchronized (connection) {
                String sql = "insert into " +
                        "recruit(number, name, recruit_begin, recruit_end, sales_begin, sales_end, " +
                        "address, explanation, reference_url) " +
                        "values(null,?,?,?,?,?,?,?,?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, recruit.getName());
                pstmt.setObject(2, recruit.getRecruit_begin());
                pstmt.setObject(3, recruit.getRecruit_end());
                pstmt.setObject(4, recruit.getSales_begin());
                pstmt.setObject(5, recruit.getSales_end());
                pstmt.setString(6, recruit.getAddress());
                pstmt.setString(7, recruit.getExplanation());
                pstmt.setString(8,recruit.getReference_url());
                pstmt.executeUpdate();
                success = true;
                pstmt.close();
                if(success)
                {
                    sql = "alter table `recruit` AUTO_INCREMENT=1;set @COUNT = 0;update `recruit` set number = @COUNT:=@COUNT+1;";
                    pstmt = connection.prepareStatement(sql);
                    pstmt.executeUpdate();
                }
        }

        data = new Data();
        data.add(success);
        network.write(data);
    }
}
