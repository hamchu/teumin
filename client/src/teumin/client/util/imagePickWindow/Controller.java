package teumin.client.util.imagePickWindow;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.client.util.addressQueryWindow.AddressQueryWindow;
import teumin.entity.Address;
import teumin.network.Data;
import teumin.network.Network;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    @FXML
    private ImageView img;

    private ImagePickWindow imagePickWindow;

    public void setParent(ImagePickWindow imagePickWindow) {
        this.imagePickWindow = imagePickWindow;
    }

    @FXML
    void click_find(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("IMAGE File", "*.bmp", "*.jpg", "*.gif", "*.png", "*.jfif"),
                new FileChooser.ExtensionFilter("BMP File (*.bmp)", "*.bmp"),
                new FileChooser.ExtensionFilter("JPG File (*.jpg)", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF File (*.gif)", "*.gif"),
                new FileChooser.ExtensionFilter("PNG File (*.png)", "*.png"),
                new FileChooser.ExtensionFilter("JPEG File (*.jfif)", "*.jfif")
        );
        img.setImage(SwingFXUtils.toFXImage(ImageIO.read(fileChooser.showOpenDialog((Stage)img.getScene().getWindow())),null));
    }

    @FXML
    void click_ok(MouseEvent event) {
        imagePickWindow.setImage(img.getImage());
        ((Stage)img.getScene().getWindow()).close();
    }

}
