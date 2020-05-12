package teumin.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import teumin.client.Client;

import java.io.IOException;

public class MainView extends Client {

    @FXML
    private BorderPane main_bp;
    @FXML
    private AnchorPane main_pane;
    @FXML
    private Button btn_logout;
    @FXML
    private Button btn_food_truck_inquiry;

    @FXML
    void logout(MouseEvent event)
    {
        Stage stage = (Stage) btn_logout.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() throws Exception {
        loadPane("user_side/HomePane");
    }

    @FXML
    void home(MouseEvent event) throws Exception
    {
        loadPane("user_side/HomePane");
    }

    @FXML
    void food_truck_inquiry(MouseEvent event) throws Exception {
        loadPane("user_side/FoodTruckInquiryPane");
    }

    private void loadPane(String page) throws Exception {
        main_pane.getChildren().clear();
        main_pane.getChildren().addAll(loadFxml(page));
    }

}
