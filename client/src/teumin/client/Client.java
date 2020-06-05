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

    public static Network getNetwork() {
        return network;
    }

    protected static Parent loadFxml(String name) {
        try {
            return FXMLLoader.load(Client.class.getResource("/teumin/client/controller/" + name));
        } catch (Exception ignore) {
        }

        return null;
    }

    protected static Image loadImage(String name) {
        try {
            return new Image("/icon/" + name);
        } catch (Exception ignore) {
        }

        return null;
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
        stage.setScene(new Scene(loadFxml("EntryView.fxml")));
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        network.close();
    }
}
