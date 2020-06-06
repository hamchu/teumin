package teumin.client.controller.admin.approveTruck;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.Truck;
import teumin.network.Data;

import java.util.Date;

public class VerifyTruckController extends Client {

    @FXML
    private TextField text_name;

    @FXML
    private TextField text_introduction;

    @FXML
    private TextArea text_explanation;

    @FXML
    private ImageView img_icon;

    @FXML
    private TextField text_category;

    @FXML
    private ImageView img_evidence;

    @FXML
    void initialize() throws Exception {
        Data data = network.read();
        Truck truck = data.get(0);

        text_name.setText(truck.getName());
        text_introduction.setText(truck.getIntroduction());
        text_explanation.setText(truck.getExplanation());
        text_category.setText(truck.getCategory());
        img_icon.setImage(truck.getIcon().toImage());
        img_evidence.setImage(truck.getEvidence().toImage());
    }

    @FXML
    void click_accept(MouseEvent event) throws Exception {

        Data data = new Data();
        data.add("UpdateTruckProven");
        data.add(text_name.getText());
        data.add(1);
        network.write(data);

        data = network.read();
        boolean success = data.get(0);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("해당 푸드트럭을 승인하였습니다.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("재시도 바랍니다.");
            alert.showAndWait();
        }

        Stage stage = (Stage)text_name.getScene().getWindow();
        stage.close();
    }

    @FXML
    void click_refuse(MouseEvent event) throws Exception {

        Data data = new Data();
        data.add("UpdateTruckProven");
        data.add(text_name.getText());
        data.add(-1);
        network.write(data);

        data = network.read();
        boolean success = data.get(0);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("해당 푸드트럭을 거부하였습니다.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("재시도 바랍니다.");
            alert.showAndWait();
        }

        Stage stage = (Stage)text_name.getScene().getWindow();
        stage.close();
    }

    @FXML
    void click_cancel(MouseEvent event) {

        Stage stage = (Stage) text_name.getScene().getWindow();
        stage.close();

    }

}
