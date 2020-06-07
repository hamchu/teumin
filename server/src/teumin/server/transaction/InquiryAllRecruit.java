package teumin.server.transaction;

import teumin.entity.Recruit;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.Transaction;
import teumin.server.account.Account;
import teumin.server.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class InquiryAllRecruit extends Transaction {
    public InquiryAllRecruit(Network network, Account account) { super(network, account); }

    @Override
    public void execute(Data data) throws Exception {

        // 조건 검사 : 관리자, 영업자 권한 아닐 시 접속 끊기
        if (account.getType() != 0) {
            if (account.getType() != 1)
            {
                network.close();
                return;
            }
        }

        // DB 연동
        Connection connection = Database.getConnection();
        synchronized (connection) {
            String sql = "select * from recruit order by number desc";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            Recruit recruit = null;
            while (resultSet.next()) {
                int number = resultSet.getInt("number");
                String name = resultSet.getString("name");
                LocalDate recruit_begin = resultSet.getObject("recruit_begin", LocalDate.class);
                LocalDate recruit_end = resultSet.getObject("recruit_end", LocalDate.class);
                LocalDate sales_begin = resultSet.getObject("sales_begin", LocalDate.class);
                LocalDate sales_end = resultSet.getObject("sales_end", LocalDate.class);
                String address = resultSet.getString("address");
                String explanation = resultSet.getString("explanation");
                String reference_url = resultSet.getString("reference_url");

                recruit = new Recruit(number, name, recruit_begin, recruit_end, sales_begin, sales_end, address, explanation, reference_url);

                data = new Data();
                data.add(recruit);
                network.write(data);
            }
            LocalDate now_date = LocalDate.now();
            data = new Data();
            data.add(now_date);
            network.write(data);
        }
    }
}
