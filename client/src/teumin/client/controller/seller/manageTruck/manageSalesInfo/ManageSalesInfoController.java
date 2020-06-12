package teumin.client.controller.seller.manageTruck.manageSalesInfo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.SalesInfo;
import teumin.network.Data;

public class ManageSalesInfoController extends Client {
    ObservableList<SalesInfo> list;
    @FXML
    private TableView<SalesInfo> sales_info_list;

    @FXML
    public void initialize() throws Exception {
        Data data = network.read();
    }

    @FXML
    void click_back(MouseEvent event) { ((Stage)sales_info_list.getScene().getWindow()).close(); }

    @FXML
    void click_register(MouseEvent event) {

    }

    @FXML
    void click_update(MouseEvent event) {

    }

    @FXML
    void click_delete(MouseEvent event) {

    }
}
