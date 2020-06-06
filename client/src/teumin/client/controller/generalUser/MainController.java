package teumin.client.controller.generalUser;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import teumin.client.Client;
import teumin.client.util.addressQueryWindow.AddressQueryWindow;
import teumin.client.util.categoryPickWindow.CategoryPickWindow;
import teumin.entity.Address;
import teumin.entity.Truck;
import teumin.network.Data;

public class MainController extends Client {

    @FXML
    private TextField text_addressName;

    @FXML
    private TextField text_category;

    private Address address;
    private String category;

    @FXML
    void click_getAddress(MouseEvent event) throws Exception {

        Address temp = new AddressQueryWindow().showAndGet();
        if (temp != null) {
            address = temp;
            text_addressName.setText(address.getName());
        }

    }

    @FXML
    void click_getCategory(MouseEvent event) {

        String temp = new CategoryPickWindow().showAndGet();
        if (temp != null) {
            category = temp;
            text_category.setText(category);
        }

    }

    @FXML
    void click_queryTrucks(MouseEvent event) {

        if (address == null || category == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("주소와 카테고리를 선택해주세요.");
            alert.showAndWait();

            return;

        }

        /**
         *
         *
         *
         *
         *
         *
         */


    }

}
