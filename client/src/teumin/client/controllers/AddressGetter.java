package teumin.client.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddressGetter {
    private static Address showQueryView() throws IOException {
        Address address = null;

        Stage stage = new Stage();
        stage.getIcons().add(new Image("/icon/teumin.png"));
        stage.setTitle("주소 검색");

        FXMLLoader fxmlLoader = new FXMLLoader(AddressGetter.class.getResource("AddressQueryView.fxml"));
        fxmlLoader.getController();

        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        return address;
    }
}
