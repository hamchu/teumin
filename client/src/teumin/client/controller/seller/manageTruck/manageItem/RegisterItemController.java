package teumin.client.controller.seller.manageTruck.manageItem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.client.util.imagePickWindow.ImagePickWindow;
import teumin.entity.Bytes;
import teumin.entity.Item;
import teumin.network.Data;

public class RegisterItemController extends Client {

    @FXML
    private TextField text_name;

    @FXML
    private TextField text_price;

    @FXML
    private TextField text_introduction;

    @FXML
    private ImageView imgView;

    @FXML
    private TextArea text_explanation;

    String truckName = null;

    @FXML
    void initialize() throws Exception {
        Data data = network.read();
        truckName = data.get(0);
    }

    @FXML
    void click_cancel(MouseEvent event) {
        ((Stage)text_name.getScene().getWindow()).close();
    }

    @FXML
    void click_imgSellect(MouseEvent event) {
        try {
            Image image = new ImagePickWindow().showAndGet();
            if (image != null) {
                imgView.setImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    void click_register(MouseEvent event) throws Exception {
        Item item = null;
        try {
            item = new Item(truckName, text_name.getText(), Integer.parseInt(text_price.getText()), new Bytes(imgView.getImage()), text_introduction.getText(), text_explanation.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("이름 중복 또는 형식 오류입니다.");
            alert.showAndWait();
            return;
        }
        //

        Data data = new Data();
        data.add("RegisterItem");
        data.add(item);
        network.write(data);

        data = network.read();
        boolean success = data.get(0);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("상품 등록이 완료되었습니다.");
            alert.showAndWait();

            Stage stage = (Stage)text_name.getScene().getWindow();
            stage.close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("이름 중복 또는 형식 오류입니다.");
            alert.showAndWait();
        }

    }

}
