package teumin.client.controller.generalUser.queryTruck;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import teumin.client.Client;
import teumin.entity.Item;
import teumin.network.Data;

public class ItemDetailController extends Client {

    private Item item;

    public void initBy(String truckName, String itemName) throws Exception {
        Data data = new Data();
        data.add("InquiryItemByTruckNameAndItemName");
        data.add(truckName);
        data.add(itemName);
        network.write(data);
        data = network.read();
        item = data.get(0);



        // 구성요소 초기화
        imgView.setImage(item.getImage().toImage());
        text_name.setText(item.getName());
        text_price.setText(item.getPrice() + "원");
        text_explanation.setText(item.getExplanation());
    }

    @FXML
    private ImageView imgView;

    @FXML
    private Text text_name;

    @FXML
    private TextArea text_explanation;

    @FXML
    private Text text_price;
}
