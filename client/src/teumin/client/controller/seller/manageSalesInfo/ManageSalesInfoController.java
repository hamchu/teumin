package teumin.client.controller.seller.manageSalesInfo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.Address;
import teumin.entity.Recruit;
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
