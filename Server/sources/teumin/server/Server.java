package teumin.server;

import teumin.network.Network;

import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Server {
    private ServerSocket serverSocket;
    private Connection connection;

    private Server() throws Exception {
        serverSocket = new ServerSocket(8000);
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/temp?useSSL=false", "root", "apmsetup");
    }

    private Coclient accept() throws Exception {
        Network network = new Network(serverSocket.accept());
        Statement statement = connection.createStatement();
        return new Coclient(network, statement);
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();

            while (true) {
                Coclient coclient = null;

                try {
                    coclient = server.accept();
                    new Thread(coclient).start();
                } catch (Exception e) {
                    e.printStackTrace();

                    if (coclient != null)
                        coclient.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
