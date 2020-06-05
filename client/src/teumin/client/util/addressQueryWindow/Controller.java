package teumin.client.util.addressQueryWindow;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.Address;
import teumin.network.Data;
import teumin.network.Network;

import java.util.ArrayList;

public class Controller {

    @FXML
    private TextField text_query;

    @FXML
    private VBox vBox;

    private AddressQueryWindow addressQueryWindow;

    private ArrayList<Address> addresses;

    public void setParent(AddressQueryWindow addressQueryWindow) {
        this.addressQueryWindow = addressQueryWindow;
    }

    @FXML
    void click_query(MouseEvent event) throws Exception {
        Network network = Client.getNetwork();
        Data data = new Data();
        data.add("AddressQuery");
        data.add(text_query.getText());
        network.write(data);

        data = network.read();
        addresses = data.<ArrayList<Address>>get(0);

        vBox.getChildren().clear();
        for (int i = 0; i < addresses.size(); i++) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setSpacing(50);
            Text text = new Text(addresses.get(i).getName());
            Text index = new Text(i + "");
            index.setVisible(false);
            hBox.getChildren().addAll(text);
            hBox.setOnMouseClicked(e -> {
                addressQueryWindow.setAddress(addresses.get(Integer.parseInt(index.getText())));
                ((Stage)vBox.getScene().getWindow()).close();
            });
            vBox.getChildren().add(hBox);
        }
    }

}
