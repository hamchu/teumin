package teumin.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teumin.network.Network;

import java.net.Socket;

public class Client extends Application {
    protected static Network network;

    protected static Parent loadFxml(String name) throws Exception {
        return FXMLLoader.load(Client.class.getResource("/teumin/client/controllers/" + name));
    }

    protected static Image loadImage(String name) throws Exception {
        return new Image("/icon/" + name);
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
        stage.getIcons().add(loadImage("teumin.png"));
        stage.setScene(new Scene(loadFxml("Login.fxml")));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        network.close();
    }
}
