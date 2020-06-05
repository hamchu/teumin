package teumin.server;

import teumin.server.account.Account;
import teumin.network.Network;
import teumin.server.account.Accounts;
import teumin.server.database.Database;

import java.net.ServerSocket;

/**
 * 메인 클래스
 */
public class Server {
    private ServerSocket serverSocket;

    public Server(int port) throws Exception {
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        while (true) {
            try {
                Network network = new Network(serverSocket.accept());
                Account account = Accounts.getNewAccount();

                new Coclient(network, account).start();
            } catch (Exception ignore) {
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Database.initialize(
                "jdbc:mysql://127.0.0.1:3306/트민?useSSL=false&characterEncoding=utf8", // 신동헌
                //"jdbc:mysql://124.63.153.55:3306/트민?useSSL=false&characterEncoding=utf8", // 이희수
                "root",
                "apmsetup"
        );

        Accounts.initialize();

        new Server(8000).run();
    }
}
