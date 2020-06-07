package teumin.client.controller.seller.inquiryRecruit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.Recruit;
import teumin.network.Data;

public class InquiryRecruitController extends Client {
    ObservableList<Recruit> list;
    @FXML
    private TableView<Recruit> recruit_list;
    @FXML
    private TableColumn column_recruit_begin;
    @FXML
    private TableColumn column_recruit_end;
    @FXML
    private TableColumn column_name;
    @FXML
    private TableColumn column_no;

    @FXML
    public void initialize() throws Exception {
        list = FXCollections.observableArrayList();
        column_no.setCellValueFactory(new PropertyValueFactory<>("number"));
        column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_recruit_begin.setCellValueFactory(new PropertyValueFactory<>("recruit_begin"));
        column_recruit_end.setCellValueFactory(new PropertyValueFactory<>("recruit_end"));

        Data data = new Data();
        data.add("InquiryAllRecruit");
        network.write(data);

        Recruit recruit = null;
        data = network.read();
        while (data.get(0) != null) {
            recruit = (Recruit) data.get(0);
            list.add(recruit);
            data = network.read();
        }

        recruit_list.setItems(list);
    }

    @FXML
    void goBack(MouseEvent event) throws Exception {
        Stage stage = (Stage) recruit_list.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("seller/MainView.fxml")));
        stage.show();
    }

    @FXML
    public void showDetail(MouseEvent event) throws Exception {
        if (event.getClickCount() > 1) {
            if (recruit_list.getSelectionModel().isEmpty()) { return; }
            Data data = new Data();
            data.add("InquiryRecruit");
            data.add(recruit_list.getSelectionModel().getSelectedItem().getNumber());
            network.write(data);

            Stage stage = (Stage) recruit_list.getScene().getWindow();
            stage.close();

            stage.setScene(new Scene(loadFxml("seller/inquiryRecruit/RecruitDetailView.fxml")));
            stage.show();
        }
    }
}
