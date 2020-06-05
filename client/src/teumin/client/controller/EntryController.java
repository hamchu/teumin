package teumin.client.controller;


import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teumin.client.Client;

public class EntryController extends Client {

    @FXML
    VBox root;

    @FXML
    void click_generalUser(MouseEvent event) {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("generalUser/MainView.fxml")));
        stage.show();
    }

    @FXML
    void click_seller(MouseEvent event) {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("LoginAccountView.fxml")));
        stage.show();
    }

}
