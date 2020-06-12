package teumin.client.controller.seller.manageTruck.manageSalesInfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.SalesInfo;
import teumin.network.Data;

import java.util.ArrayList;
import java.util.Optional;

public class ManageSalesInfoController extends Client {
    ObservableList<SalesInfo> list;
    ArrayList<SalesInfo> salesInfoList;
    String truckName;
    @FXML
    private TableView<SalesInfo> table_sales_info;
    @FXML
    private TableColumn column_date;
    @FXML
    private TableColumn column_begin;
    @FXML
    private TableColumn column_end;
    @FXML
    private TableColumn column_address;

    @FXML
    public void initialize() throws Exception {
        list = FXCollections.observableArrayList();
        Data data = network.read();
        truckName = data.get(0);
        salesInfoList = data.get(1);
        column_date.setCellValueFactory(new PropertyValueFactory<>("date")) ;
        column_begin.setCellValueFactory(new PropertyValueFactory<>("begin"));
        column_end.setCellValueFactory(new PropertyValueFactory<>("end"));
        column_address.setCellValueFactory(new PropertyValueFactory<>("addressName"));

        for(SalesInfo salesInfo : salesInfoList) { list.add(salesInfo); }
        table_sales_info.setItems(list);
    }

    @FXML
    void click_back(MouseEvent event) { ((Stage)table_sales_info.getScene().getWindow()).close(); }

    @FXML
    void click_register(MouseEvent event) throws Exception {
        Data data = new Data();
        data.add("SyncDate");
        data.add(truckName);
        network.write(data);

        Stage stage = new Stage();
        stage.setTitle("영업일정 등록");
        stage.getIcons().add(loadImage("teumin.png"));
        stage.setScene(new Scene(loadFxml("seller/manageTruck/manageSalesInfo/RegisterSalesInfoView.fxml")));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        // 처리 후 기존 화면 최신화
        data = new Data();
        data.add("InquirySalesInfosByTruckName");
        data.add(truckName);
        network.write(data);
        initialize();
    }

    @FXML
    void click_update(MouseEvent event) throws Exception {
        if(table_sales_info.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("수정할 영업일정을 선택해주세요.");
            alert.showAndWait();
            return;
        }

        Data data = new Data();
        data.add("InquirySalesInfoByTruckNameAndDate");
        data.add(truckName);
        data.add(table_sales_info.getSelectionModel().getSelectedItem().getDate());
        network.write(data);

        Stage stage = new Stage();
        stage.setTitle("영업일정 수정");
        stage.getIcons().add(loadImage("teumin.png"));
        stage.setScene(new Scene(loadFxml("seller/manageTruck/manageSalesInfo/UpdateSalesInfoView.fxml")));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        // 처리 후 기존 화면 최신화
        data = new Data();
        data.add("InquirySalesInfosByTruckName");
        data.add(truckName);
        network.write(data);
        initialize();
    }

    @FXML
    void click_delete(MouseEvent event) throws Exception {
        if(!table_sales_info.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("삭제 확인");
            alert.setContentText("선택한 영업일정을 삭제하시겠습니까?");
            Optional<ButtonType> choose = alert.showAndWait();

            if(choose.get().equals(ButtonType.OK)){
                Data data = new Data();
                data.add("DeleteSalesInfo");
                data.add(table_sales_info.getSelectionModel().getSelectedItem());
                network.write(data);

                data = network.read();
                boolean success = data.get(0);

                if (success) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("알림");
                    alert.setHeaderText(null);
                    alert.setContentText("영업일정이 삭제되었습니다.");
                    alert.showAndWait();
                    // 처리 후 기존 화면 최신화
                    data = new Data();
                    data.add("InquirySalesInfosByTruckName");
                    data.add(truckName);
                    network.write(data);
                    initialize();
                } else {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("알림");
                    alert.setHeaderText(null);
                    alert.setContentText("해당하는 영업일정이 존재하지 않습니다.");
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("삭제할 영업일정을 선택해주세요.");
            alert.showAndWait();
        }
    }
}