package teumin.client.controller.seller.manageTruck.manageTruckInfo;

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
import teumin.client.util.categoryPickWindow.CategoryPickWindow;
import teumin.client.util.imagePickWindow.ImagePickWindow;
import teumin.entity.Bytes;
import teumin.entity.Truck;
import teumin.network.Data;

public class ManageTruckInfoController extends Client {
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
    void initialize() throws Exception {
        Data data = network.read();
        Truck truck = data.get(0);

        text_name.setText(truck.getName());
        text_introduction.setText(truck.getIntroduction());
        text_explanation.setText(truck.getExplanation());
        img_icon.setImage(truck.getIcon().toImage());
        text_category.setText(truck.getCategory());
    }

    @FXML
    void click_cancel(MouseEvent event) throws Exception {
        ((Stage)text_name.getScene().getWindow()).close();

        Data data = new Data();
        data.add("InquiryTruckByNameToManage");
        data.add(text_name.getText());
        network.write(data);
    }

    @FXML
    void click_complete(MouseEvent event) throws Exception {
        Truck truck = new Truck(
                text_name.getText(),
                text_introduction.getText(),
                text_explanation.getText(),
                text_category.getText(),
                0,
                null,
                new Bytes(img_icon.getImage())
        );

        Data data = new Data();
        data.add("UpdateTruck");
        data.add(truck);
        network.write(data);

        data = network.read();
        boolean success = data.get(0);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("푸드트럭 정보 적용이 완료되었습니다.");
            alert.showAndWait();

            ((Stage)text_name.getScene().getWindow()).close();

            data = new Data();
            data.add("InquiryTruckByNameToManage");
            data.add(text_name.getText());
            network.write(data);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("형식 오류입니다.");
            alert.showAndWait();
        }
    }

    @FXML
    void click_selectCategory(MouseEvent event) {
        String category = new CategoryPickWindow().showAndGet();
        if (category != null) text_category.setText(category);
    }

    @FXML
    void click_selectIcon(MouseEvent event) {
        try {
            Image image = new ImagePickWindow().showAndGet();
            if (image != null) {
                img_icon.setImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
