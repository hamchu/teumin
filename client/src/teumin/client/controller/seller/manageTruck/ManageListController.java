package teumin.client.controller.seller.manageTruck;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.Truck;
import teumin.network.Data;

public class ManageListController extends Client {

    @FXML
    private Text text_name;

    @FXML
    private ImageView img_icon;

    @FXML
    private Text text_proven;

    @FXML
    private Text text_introduction;

    @FXML
    private Button btn_requestReVerify;

    @FXML
    void initialize() throws Exception {

        Data data = network.read();
        Truck truck = data.get(0);

        text_name.setText(truck.getName());
        text_introduction.setText(truck.getIntroduction());
        switch (truck.getProven()){
            case 1:
                text_proven.setFill(Paint.valueOf("#00EE00"));
                text_proven.setText("승인 완료");
                btn_requestReVerify.setDisable(true);
                break;

            case 0:
                text_proven.setFill(Paint.valueOf("#0000EE"));
                text_proven.setText("심사 중");
                btn_requestReVerify.setDisable(true);
                break;

            case -1:
                text_proven.setFill(Paint.valueOf("#EE0000"));
                text_proven.setText("승인 거절");
                break;
        }
        img_icon.setImage(truck.getIcon().toImage());

    }

    @FXML
    void click_exit(MouseEvent event) {
        Stage stage = (Stage)text_name.getScene().getWindow();
        stage.close();
        stage.setScene(new Scene(loadFxml("seller/manageTruck/ManageTruckView.fxml")));
        stage.show();
    }

    @FXML
    void click_manageItem(MouseEvent event) {

    }

    @FXML
    void click_manageTruck(MouseEvent event) {

    }

    @FXML
    void click_requestReVerify(MouseEvent event) throws Exception {
        Data data = new Data();
        data.add("RequestReVerifyTruck");
        data.add(text_name.getText());
        network.write(data);

        data = network.read();
        boolean success = data.get(0);
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("재심사 요청이 완료되었습니다.");
            alert.showAndWait();

            text_proven.setFill(Paint.valueOf("#0000EE"));
            text_proven.setText("심사 중");
            btn_requestReVerify.setDisable(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("알 수 없는 오류입니다.");
            alert.showAndWait();
        }
    }

}
