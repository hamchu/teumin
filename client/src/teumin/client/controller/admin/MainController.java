package teumin.client.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import teumin.client.Client;

public class MainController extends Client {

    @FXML
    private AnchorPane root;


    @FXML
    void click_approveTruck(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("admin/ApproveTruckView.fxml")));
        stage.show();
    }

    @FXML
    void click_registerRecruit(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("admin/RegisterRecruitView.fxml")));
        stage.show();
    }

}
