package teumin.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import teumin.client.controller.Controller;
import teumin.network.Network;

import java.net.Socket;

public class Client extends Application {
    private Network network;

    @Override
    public void start(Stage primaryStage) throws Exception {
        network = new Network(new Socket("127.0.0.1", 8000));
        primaryStage.setTitle("트럭의민족");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
        ((Controller)loader.getController()).setNetwork(network);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
