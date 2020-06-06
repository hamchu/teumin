package teumin.client.util.imagePickWindow;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teumin.client.Client;

public class ImagePickWindow {
    private Image image;

    public void setImage(Image image) {
        this.image = image;
    }

    public Image showAndGet() throws Exception {
        Stage stage = new Stage();
        stage.setTitle("이미지 선택");
        stage.getIcons().add(Client.loadImage("teumin.png"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View.fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));
        Controller controller = fxmlLoader.getController();
        controller.setParent(this);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        return image;
    }
}
