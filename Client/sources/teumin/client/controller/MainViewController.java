package teumin.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainViewController extends Controller{

    @FXML
    private BorderPane main_bp;
    @FXML
    private AnchorPane main_pane;
    @FXML
    private Button btn_logout;
    @FXML
    private Button btn_food_truck_inquiry;

    @FXML
    void home(MouseEvent event) throws Exception
    {
        main_bp.setCenter(main_pane);
    }

    @FXML
    void logout(MouseEvent event)
    {
        Stage stage = (Stage) btn_logout.getScene().getWindow();
        stage.close();
    }

    @FXML
    void food_truck_inquiry(MouseEvent event) throws IOException {
        loadPane("FoodTruckInquiryPane");
    }

    private void loadPane(String page) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user_side/" + page + ".fxml"));
        Parent sub = loader.load();
        main_bp.setCenter(sub);
        ((Controller)loader.getController()).setNetwork(network);
    }

}
