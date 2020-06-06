package teumin.client.controller.generalUser;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import teumin.client.Client;
import teumin.client.util.addressQueryWindow.AddressQueryWindow;
import teumin.entity.Address;
import teumin.entity.Truck;
import teumin.network.Data;

public class MainController extends Client {

    @FXML
    void click_testAddressQueryWindow(MouseEvent event) throws Exception {
        Address address = new AddressQueryWindow().showAndGet();
    }

}
