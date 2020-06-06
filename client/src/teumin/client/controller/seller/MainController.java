package teumin.client.controller.seller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.network.Data;

public class MainController extends Client {

    @FXML
    AnchorPane root;

    @FXML
    void click_registerTruck(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("seller/registerTruck/RegisterTruckView.fxml")));
        stage.show();
    }

    @FXML
    void click_inquiryRecruit(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("seller/inquiryRecruit/InquiryRecruitView.fxml")));
        stage.show();
    }

    @FXML
    void click_manageTruck(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("seller/manageTruck/ManageTruckView.fxml")));
        stage.show();
    }

    @FXML
    void click_logout(MouseEvent event) throws Exception {
        Data data = new Data();
        data.add("LogoutAccount");
        network.write(data);
        data = network.read();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("알림");
        alert.setHeaderText(null);
        alert.setContentText("정상적으로 로그아웃 되었습니다.");
        alert.showAndWait();

        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
        stage.setScene(new Scene(loadFxml("LoginAccountView.fxml")));
        stage.show();
    }

}
