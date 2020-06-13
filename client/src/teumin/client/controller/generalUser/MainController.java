package teumin.client.controller.generalUser;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.client.util.addressQueryWindow.AddressQueryWindow;
import teumin.client.util.categoryPickWindow.CategoryPickWindow;
import teumin.entity.Address;
import teumin.entity.Truck;
import teumin.entity.TruckWithSalesInfo;
import teumin.network.Data;

import java.util.ArrayList;

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
    void click_queryTrucks(MouseEvent event) throws Exception {

        if (address == null || category == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("주소와 카테고리를 선택해주세요.");
            alert.showAndWait();

            return;

        }

        Data data = new Data();
        data.add("QueryTruck");
        data.add(category);
        network.write(data);

        data = network.read();
        ArrayList<TruckWithSalesInfo> truckWithSalesInfos =  data.get(0);

        /**
         *
         *
         * truckWithSalesInfos 녀석들 영업 중인가 아닌가로 정렬
         * 영업 중인 놈들은 거리 순으로 정렬
         *
         * 그 결과 데이터를 ListView로 전달하여 적절하게 표시하고 클릭 이벤트 적용
         *
         *
         */
    }

    @FXML
    void click_exit(MouseEvent event) {

        Stage stage = (Stage)text_addressName.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("EntryView.fxml")));
        stage.show();

    }

}
