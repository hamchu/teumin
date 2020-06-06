package teumin.client.controller.seller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.Truck;
import teumin.network.Data;

public class RegisterTruckController extends Client {
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
    void click_cancel(MouseEvent event) {
        Stage stage = (Stage) text_name.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("seller/MainView.fxml")));
        stage.show();
    }

    @FXML
    void click_register(MouseEvent event) throws Exception {
        Truck truck = new Truck(
                text_name.getText(),
                text_introduction.getText(),
                text_explanation.getText(),
                text_category.getText(),
                img_evidence.getImage(),
                img_icon.getImage()
                );

        if (truck.getEvidence() == null) truck.setEvidence(new Image("/icon/teumin.png"));
        if (truck.getIcon() == null) truck.setIcon(new Image("/icon/teumin.png"));

        Data data = new Data();
        data.add("RegisterTruck");
        data.add(truck);
        network.write(data);

        data = network.read();
        boolean success = data.get(0);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("푸드트럭 등록이 완료되었습니다.\n관리자의 승인 후 일반사용자에게 접근됩니다!");
            alert.showAndWait();

            Stage stage = (Stage)text_name.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("이름 중복 또는 형식 오류입니다.");
            alert.showAndWait();
        }
    }

    @FXML
    void click_selectCategory(MouseEvent event) {

    }

    @FXML
    void click_selectEvidence(MouseEvent event) {

    }

    @FXML
    void click_selectIcon(MouseEvent event) {

    }
}
