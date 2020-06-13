package teumin.client.controller.generalUser.queryTruck;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.TruckWithSalesInfo;

import java.util.ArrayList;

public class TruckListController extends Client {

    private ArrayList<TruckWithSalesInfo> truckWithSalesInfos;

    ArrayList<VBox> listBoxes = null;
    Integer targetTruckIndex = null;

    @FXML
    private VBox vBox;

    public void initBy(ArrayList<TruckWithSalesInfo> truckWithSalesInfos) {
        this.truckWithSalesInfos = truckWithSalesInfos;
        listBoxes = new ArrayList<>();

        for (int i = 0; i < truckWithSalesInfos.size(); i++) {
            VBox listBox = new VBox();
            listBoxes.add(listBox);
            vBox.getChildren().add(listBox);

            listBox.setPadding(new Insets(10));
            listBox.setSpacing(0);
            listBox.setAlignment(Pos.CENTER_LEFT);

            Text index = new Text(i + ""); index.setVisible(false);
            Text name = new Text(truckWithSalesInfos.get(i).getName());
            name.setStyle("-fx-font-size: 20;");
            Text introduction = new Text(truckWithSalesInfos.get(i).getIntroduction());
            ImageView img_icon = new ImageView(truckWithSalesInfos.get(i).getIcon().toImage());
            img_icon.setFitHeight(50);
            img_icon.setFitWidth(50);
            img_icon.setSmooth(false);
            img_icon.setPreserveRatio(false);
            Text salesInfo = new Text();
            if (truckWithSalesInfos.get(i).isOpen()) {
                salesInfo.setText("현재 영업 중 [" + truckWithSalesInfos.get(i).getAddress().getName() + " (" + truckWithSalesInfos.get(i).getBegin() + " ~ " +  truckWithSalesInfos.get(i).getEnd() + ")]");
            }
            else {
                salesInfo.setText("현재 영업 X");
            }
            VBox topSub = new VBox();
            topSub.setPadding(new Insets(5));
            topSub.setSpacing(5);
            topSub.getChildren().addAll(name, introduction);

            HBox topMain = new HBox();
            topMain.setPadding(new Insets(10));
            topMain.setSpacing(5);
            topMain.getChildren().addAll(img_icon, topSub, index);

            HBox bottomMain = new HBox();
            bottomMain.setPadding(new Insets(5));
            bottomMain.setSpacing(5);
            bottomMain.getChildren().addAll(salesInfo);

            listBox.getChildren().addAll(topMain, bottomMain);

            listBox.setOnMouseClicked(e -> {
                for (VBox unit : listBoxes) {
                    unit.setStyle("-fx-background-color: transparent");
                }
                listBox.setStyle("-fx-background-color: white");

                targetTruckIndex = Integer.parseInt(index.getText());
            });
        }

    }

    @FXML
    void click_back(MouseEvent event) {
        ((Stage)vBox.getScene().getWindow()).close();
    }

    @FXML
    void click_inquiry(MouseEvent event) {

        if (targetTruckIndex == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("항목을 선택해주세요.");
            alert.showAndWait();

            return;
        }


        System.out.println(truckWithSalesInfos.get(targetTruckIndex).getName());
        /**
         *
         *
         * ....
         *
         *
         */

    }

}

