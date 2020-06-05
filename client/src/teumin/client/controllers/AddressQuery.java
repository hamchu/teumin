package teumin.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddressQuery {

    @FXML
    private TextField textField;

    @FXML
    private Button button;

    @FXML
    private VBox vBox;

    @FXML
    void query(MouseEvent event) {
        add("AddressQuery");
        add(textField.getText());

        ArrayList<Address> addresses = new ArrayList<>();

        for (int i = 0; i < addresses.size(); i++) {
            HBox hBox = new HBox();
            hBox.setOnMouseClicked(e -> {
                address = addresses.get(i);
                ((Stage) button.getScene().getWindow()).close();
            });

            vBox.getChildren().add(hBox);
        }
    }

}
