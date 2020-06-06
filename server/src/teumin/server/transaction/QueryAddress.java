package teumin.server.transaction;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import teumin.entity.Address;
import teumin.network.Data;
import teumin.network.Network;
import teumin.server.account.Accounts;
import teumin.server.Transaction;
import teumin.server.account.Account;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class QueryAddress extends Transaction {
    public QueryAddress(Network network, Account account) {
        super(network, account);
    }

    @Override
    public void execute(Data data) throws Exception {
        // param
        String query = data.<String>get(1);

        // return
        ArrayList<Address> addresses = new ArrayList<>();

        // 조건 검사 : 없음

        // 레스터 연동
        String json = "";
        synchronized (Accounts.getAccountList()) { // 동기화 객체 임시로 갖다 씀......
            query = URLEncoder.encode(query, "UTF-8");
            HttpsURLConnection con = (HttpsURLConnection) new URL("https://dapi.kakao.com/v2/local/search/address.json?query=" + query).openConnection();
            con.setRequestProperty("Authorization", "KakaoAK 4a31dfe8a976703e32c05ed7e96d41e3");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String line;
            while((line = in.readLine()) != null) {
                json += line;
            }
            in.close();
            con.disconnect();
        }

        // 결과 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        JSONArray jsonArray = (JSONArray) jsonObject.get("documents");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject personObject = (JSONObject) jsonArray.get(i);
            String name = (String) personObject.get("address_name");
            double x = Double.parseDouble((String) personObject.get("x"));
            double y = Double.parseDouble((String) personObject.get("y"));
            addresses.add(new Address(name, x, y));
        }

        data = new Data();
        data.add(addresses);
        network.write(data);
    }
}
