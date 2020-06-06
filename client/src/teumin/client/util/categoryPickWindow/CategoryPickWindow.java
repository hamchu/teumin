package teumin.client.util.categoryPickWindow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.Category;

import java.util.ArrayList;

public class CategoryPickWindow extends Client {
    public String category;

    public String showAndGet() {
        Stage stage = new Stage();
        stage.setTitle("카테고리");
        stage.getIcons().add(loadImage("teumin.png"));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20));
        ArrayList<String> categories = Category.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setSpacing(50);
            Text text = new Text(categories.get(i));
            hBox.getChildren().addAll(text);
            hBox.setOnMouseClicked(e -> {
                category = text.getText();
                stage.close();
            });
            vBox.getChildren().add(hBox);
        }

        stage.setScene(new Scene(vBox));
        stage.showAndWait();

        return category;
    }
}
