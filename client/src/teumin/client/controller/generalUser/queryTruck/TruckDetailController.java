package teumin.client.controller.generalUser.queryTruck;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
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

public class TruckDetailController extends Client {

    private Truck truck;
    private ArrayList<Item> items;

    public void initBy(String truckName) throws Exception {
        Data data = new Data();
        data.add("InquiryTruckByName");
        data.add(truckName);
        network.write(data);
        data = network.read();
        truck = data.get(0);

        data = new Data();
        data.add("InquiryItemsByTruckName");
        data.add(truckName);
        network.write(data);
        data = network.read();
        items = data.get(1);

        initComponents();
    }

    private void showItemDetailWindow(String truckName, String itemName) throws Exception {
        Stage stage = new Stage();
        stage.setTitle(truckName + " - " + itemName);
        stage.getIcons().add(loadImage("teumin.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemDetailView.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        {
            ItemDetailController itemDetailController = loader.getController();
            itemDetailController.initBy(truckName, itemName);
        }
        stage.show();
    }

    private void initComponents() {
        img_icon.setImage(truck.getIcon().toImage());
        text_name.setText(truck.getName());
        text_introduction.setText(truck.getIntroduction());
        text_explanation.setText(truck.getExplanation());

        /**
         *
         * VBox
         *
         */
        listBoxes = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            VBox listBox = new VBox();
            listBoxes.add(listBox);
            vBox.getChildren().add(listBox);
            vBox.getChildren().add(new Line(0, 0, vBox.getPrefWidth(), 0));

            listBox.setPadding(new Insets(10));
            listBox.setSpacing(0);
            listBox.setAlignment(Pos.CENTER_LEFT);

            Text index = new Text(i + ""); index.setVisible(false);
            Text name = new Text(items.get(i).getName() + " ( " + items.get(i).getPrice() + "원 )");
            name.setStyle("-fx-font-size: 20;");
            Text introduction = new Text(items.get(i).getIntroduction());
            ImageView img_icon = new ImageView(items.get(i).getImage().toImage());
            img_icon.setFitHeight(50);
            img_icon.setFitWidth(50);
            img_icon.setSmooth(false);
            img_icon.setPreserveRatio(false);
            VBox topSub = new VBox();
            topSub.setPadding(new Insets(5));
            topSub.setSpacing(5);
            topSub.getChildren().addAll(name, introduction);

            HBox topMain = new HBox();
            topMain.setPadding(new Insets(10));
            topMain.setSpacing(5);
            topMain.getChildren().addAll(img_icon, topSub, index);

            listBox.getChildren().addAll(topMain);

            listBox.setOnMouseClicked(e -> {
                try {
                    showItemDetailWindow(truck.getName(), items.get(Integer.parseInt(index.getText())).getName());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        }
    }

    ArrayList<VBox> listBoxes = null;

    @FXML
    private Text text_name;

    @FXML
    private ImageView img_icon;

    @FXML
    private Text text_introduction;

    @FXML
    private TextArea text_explanation;

    @FXML
    private VBox vBox;



}
