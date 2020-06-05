package teumin.client.util.addressQueryWindow;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.Address;

public class AddressQueryWindow {
    private Address address;

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address showAndGet() throws Exception {
        Stage stage = new Stage();
        stage.setTitle("주소 검색");
        stage.getIcons().add(Client.loadImage("teumin.png"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View.fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));
        Controller controller = fxmlLoader.getController();
        controller.setParent(this);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        return address;
    }
}
