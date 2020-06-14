package teumin.client.controller.seller.manageTruck.manageItem;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.Item;
import teumin.entity.Truck;
import teumin.network.Data;

import java.util.ArrayList;

public class ManageItemsController extends Client {

    @FXML
    private VBox vBox;

    String truckName = null;
    ArrayList<HBox> hBoxes = null;
    ArrayList<Item> items = null;
    Integer targetItemIndex = null;

    @FXML
    void initialize() throws Exception {
        vBox.getChildren().clear();
        hBoxes = new ArrayList<>();
        targetItemIndex = null;
        Data data = network.read();
        truckName = data.get(0);
        items = data.get(1);

        for (int i = 0; i < items.size(); i++) {
            HBox hBox = new HBox();
            hBox.setPadding(new Insets(10));
            hBox.setAlignment(Pos.CENTER_LEFT);
            Text text = new Text(items.get(i).getName());
            Text index = new Text(i + "");
            index.setVisible(false);
            hBox.getChildren().addAll(text, index);
            hBoxes.add(hBox);
            hBox.setOnMouseClicked(e -> {
                for (HBox unit : hBoxes) {
                    unit.setStyle("-fx-background-color: transparent");
                }
                hBox.setStyle("-fx-background-color: white");
                targetItemIndex = Integer.parseInt(index.getText());
            });
            vBox.getChildren().add(hBox);
            vBox.getChildren().add(new Line(0, 0, vBox.getPrefWidth(), 0));
        }
    }

    @FXML
    void click_back(MouseEvent event) {
        ((Stage)vBox.getScene().getWindow()).close();
    }

    @FXML
    void click_register(MouseEvent event) throws Exception {

        Data data = new Data();
        data.add("HandleTruckName");
        data.add(truckName);
        network.write(data);

        Stage stage = new Stage();
        stage.setTitle("푸드트럭 정보 관리");
        stage.getIcons().add(loadImage("teumin.png"));
        stage.setScene(new Scene(loadFxml("seller/manageTruck/manageItem/RegisterItemView.fxml")));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        // 처리 후 기존 화면 최신화
        data = new Data();
        data.add("InquiryItemsByTruckName");
        data.add(truckName);
        network.write(data);
        initialize();
    }

    @FXML
    void click_manage(MouseEvent event) throws Exception {

        if (targetItemIndex == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("항목을 선택해주세요.");
            alert.showAndWait();

            return;
        }

        Data data = new Data();
        data.add("InquiryItemByTruckNameAndItemName");
        data.add(truckName);
        data.add(items.get(targetItemIndex).getName());
        network.write(data);

        Stage stage = new Stage();
        stage.setTitle("푸드트럭 정보 관리");
        stage.getIcons().add(loadImage("teumin.png"));
        stage.setScene(new Scene(loadFxml("seller/manageTruck/manageItem/UpdateItemView.fxml")));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        // 처리 후 기존 화면 최신화
        data = new Data();
        data.add("InquiryItemsByTruckName");
        data.add(truckName);
        network.write(data);
        initialize();

    }

}
