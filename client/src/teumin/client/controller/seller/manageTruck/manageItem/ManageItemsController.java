package teumin.client.controller.seller.manageTruck.manageItem;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.Item;
import teumin.entity.Truck;
import teumin.network.Data;

import java.util.ArrayList;

public class ManageItemsController extends Client {

    @FXML
    private VBox vBox;

    ArrayList<HBox> hBoxes = new ArrayList<>();
    ArrayList<Item> items = new ArrayList<>();
    Integer targetItemIndex = null;

    @FXML
    void initialize() throws Exception {
        Data data = network.read();
        items = data.get(0);

        for (int i = 0; i < items.size(); i++) {
            HBox hBox = new HBox();
            hBox.setPadding(new Insets(10));
            hBox.setAlignment(Pos.CENTER_LEFT);
            Text text = new Text(items.get(i).getName());
            Text index = new Text(i + "");
            hBox.getChildren().addAll(text);
            hBoxes.add(hBox);
            hBox.setOnMouseClicked(e -> {
                for (HBox unit : hBoxes) {
                    unit.setStyle("-fx-background-color: transparent");
                }
                hBox.setStyle("-fx-background-color: white");
                targetItemIndex = Integer.parseInt(index.getText());
            });
            vBox.getChildren().add(hBox);
        }
    }

    @FXML
    void click_back(MouseEvent event) {
        ((Stage)vBox.getScene().getWindow()).close();
    }

    @FXML
    void click_manage(MouseEvent event) {

    }

    @FXML
    void click_register(MouseEvent event) {

    }

}
