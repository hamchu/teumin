package teumin.client.controller.generalUser;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.client.controller.generalUser.queryTruck.TruckListController;
import teumin.client.util.addressQueryWindow.AddressQueryWindow;
import teumin.client.util.categoryPickWindow.CategoryPickWindow;
import teumin.entity.Address;
import teumin.entity.TruckWithSalesInfo;
import teumin.network.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        sortTruckWithSalesInfo(truckWithSalesInfos);

        Stage stage = new Stage();
        stage.setTitle("트럭의민족");
        stage.getIcons().add(loadImage("teumin.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("queryTruck/TruckListView.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        {
            TruckListController truckListController = loader.getController();
            truckListController.initBy(truckWithSalesInfos);
        }
        stage.show();

    }

    void sortTruckWithSalesInfo(ArrayList<TruckWithSalesInfo> truckWithSalesInfos) {

        /**
         *
         *
         * truckWithSalesInfos 녀석들 영업 중인가 아닌가로 정렬
         * 영업 중인 놈들은 거리 순으로 정렬
         *
         *
         */

        ArrayList<TruckWithSalesInfo> OpenTrucks = new ArrayList<>();
        ArrayList<TruckWithSalesInfo> notOpenTrucks = new ArrayList<>();
        for (TruckWithSalesInfo target : truckWithSalesInfos) {
            if (target.isOpen()) {
                OpenTrucks.add(target);
            } else {
                notOpenTrucks.add(target);
            }
        }
        Collections.sort(OpenTrucks, new Comparator<TruckWithSalesInfo>() {
            @Override
            public int compare(TruckWithSalesInfo A, TruckWithSalesInfo B)
            {
                double flag = Address.getDistance(A.getAddress(), address) - Address.getDistance(B.getAddress(), address);
                if (flag > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        truckWithSalesInfos.clear();
        truckWithSalesInfos.addAll(OpenTrucks);
        truckWithSalesInfos.addAll(notOpenTrucks);
    }

    @FXML
    void click_exit(MouseEvent event) {

        Stage stage = (Stage)text_addressName.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("EntryView.fxml")));
        stage.show();

    }

}
