package teumin.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import teumin.client.controller.Controller;
import teumin.network.*;

public class FoodTruckInquiryController extends Controller {

    ObservableList list = FXCollections.observableArrayList();

    @FXML
    private ListView<String> korean_truck_list;

    @FXML
    private ListView<String> chinese_truck_list;

    @FXML
    private ListView<String> western_truck_list;

    @FXML
    private Button btn_test;

    @FXML
    void test(MouseEvent event) throws Exception {
    }

    @FXML
    void test_onClicked(MouseEvent event) throws Exception {
        loadFoodTruckData(korean_truck_list, "한식");
        loadFoodTruckData(chinese_truck_list, "중식");
        loadFoodTruckData(western_truck_list, "양식");
    }
    @FXML
    void clear_onClicked(MouseEvent event) throws Exception {
        korean_truck_list.getItems().clear();
        chinese_truck_list.getItems().clear();
        western_truck_list.getItems().clear();
    }

    private void loadFoodTruckData(ListView<String> food_truck_list, String category) throws Exception {
        list.removeAll(list);
        food_truck_list.getItems().clear();
        Data data = null;
        data = new Data(DataType.FOOD_TRUCK_INQUIRY_REQUEST);
        data.addObject(category);
        network.write(data);

        data = network.read();
        while(!(boolean)data.getObject(0))
        {
            list.add((String)data.getObject(1));
            data = network.read();
        }

        food_truck_list.getItems().addAll(list);
    }
}

