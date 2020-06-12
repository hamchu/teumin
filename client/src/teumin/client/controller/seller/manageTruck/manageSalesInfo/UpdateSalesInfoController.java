package teumin.client.controller.seller.manageTruck.manageSalesInfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.client.util.addressQueryWindow.AddressQueryWindow;
import teumin.entity.Address;
import teumin.entity.SalesInfo;
import teumin.network.Data;

import java.time.LocalTime;

public class UpdateSalesInfoController extends Client {
    SalesInfo salesInfo;
    Address address = null;
    @FXML
    private DatePicker date;
    @FXML
    private TextField text_address;
    @FXML
    private TextField text_detail_address;
    @FXML
    private ComboBox<Integer> end_minute;
    @FXML
    private ComboBox<Integer> begin_minute;
    @FXML
    private ComboBox<Integer> end_hour;
    @FXML
    private ComboBox<Integer> begin_hour;

    @FXML
    public void initialize() throws Exception {
        Data data = network.read();
        salesInfo = data.get(0);

        ObservableList<Integer> hour_list = FXCollections.observableArrayList();
        for(int i = 0; i<24;i++) { hour_list.add(i); }
        begin_hour.setItems(hour_list);
        end_hour.setItems(hour_list);
        begin_hour.getSelectionModel().select(salesInfo.getBegin().getHour());
        end_hour.getSelectionModel().select(salesInfo.getEnd().getHour());
        ObservableList<Integer> minute_list = FXCollections.observableArrayList();
        for(int i = 0; i<60;i++) { minute_list.add(i); }
        begin_minute.setItems(minute_list);
        end_minute.setItems(minute_list);

        begin_minute.getSelectionModel().select(salesInfo.getBegin().getMinute());
        end_minute.getSelectionModel().select(salesInfo.getEnd().getMinute());

        date.setValue(salesInfo.getDate());
        address = salesInfo.getAddress();
        int index = salesInfo.getAddressName().indexOf(",");

        text_detail_address.setText(salesInfo.getAddressName().substring(index+1));
        address.setName(salesInfo.getAddressName().substring(0, index));
        text_address.setText(address.getName());
    }

    @FXML
    void click_queryAddress(MouseEvent event) throws Exception {
        AddressQueryWindow util = new AddressQueryWindow();
        address = new AddressQueryWindow().showAndGet();
        if(address != null) text_address.setText(address.getName());
        else text_address.setText("");
    }

    @FXML
    void click_update(MouseEvent event) throws Exception {
        if (address != null && text_detail_address.getLength() > 0) {
            address.setName(address.getName() + ", " + text_detail_address.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("형식 오류입니다.");
            alert.showAndWait();
            return;
        }
        LocalTime begin =
                LocalTime.parse(String.format("%02d", begin_hour.getValue())+":"+String.format("%02d", begin_minute.getValue()));
        LocalTime end =
                LocalTime.parse(String.format("%02d", end_hour.getValue())+":"+String.format("%02d", end_minute.getValue()));

        SalesInfo updatedSalesInfo = new SalesInfo(salesInfo.getTruckName(), salesInfo.getDate(), begin, end, address);

        Data data = new Data();
        data.add("UpdateSalesInfo");
        data.add(updatedSalesInfo);
        network.write(data);

        data = network.read();
        boolean success = data.get(0);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("영업일정이 수정되었습니다.");
            alert.showAndWait();
            ((Stage) date.getScene().getWindow()).close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("형식 오류입니다.");
            alert.showAndWait();
        }
    }

    @FXML
    void click_cancel(MouseEvent event) { ((Stage)date.getScene().getWindow()).close(); }
}