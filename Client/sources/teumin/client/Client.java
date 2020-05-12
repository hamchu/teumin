package teumin.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import teumin.network.Network;

import java.net.Socket;

public class Client extends Application {
    protected static Network network;

    protected static Parent loadFxml(String name) throws Exception {
        return FXMLLoader.load(Client.class.getResource("/teumin/client/controllers/" + name + ".fxml"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        network = new Network(new Socket("127.0.0.1", 8000));
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("트럭의민족");
        stage.setScene(new Scene(loadFxml("Login")));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        network.close();
    }
}
