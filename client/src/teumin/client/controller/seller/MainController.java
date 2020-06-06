package teumin.client.controller.seller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import teumin.client.Client;

public class MainController extends Client {

    @FXML
    AnchorPane root;

    @FXML
    void click_registerTruck(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("seller/RegisterTruckView.fxml")));
        stage.show();
    }

    @FXML
    void click_inquiryRecruit(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("seller/InquiryRecruitView.fxml")));
        stage.show();
    }

    @FXML
    void click_manageTruck(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("seller/ManageTruckView.fxml")));
        stage.show();
    }

}
