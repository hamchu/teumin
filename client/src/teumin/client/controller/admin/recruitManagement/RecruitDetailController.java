package teumin.client.controller.admin.recruitManagement;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.Recruit;
import teumin.network.Data;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class RecruitDetailController extends Client {
    private int number = -1;
    @FXML
    private Label name;

    @FXML
    private Label sales_end;
    @FXML
    private Label sales_begin;

    @FXML
    private Label recruit_begin;
    @FXML
    private Label recruit_end;

    @FXML
    private Label address;

    @FXML
    private TextArea explanation;

    @FXML
    private Label reference_url;
    @FXML
    public void initialize() throws Exception {
        Data data = network.read();
        Recruit recruit = null;
        if(data.get(0) != null)
        {
            recruit = (Recruit) data.get(0);
        }
        number = recruit.getNumber();
        name.setText(recruit.getName());
        recruit_begin.setText(recruit.getRecruit_begin().toString());
        recruit_end.setText(recruit.getRecruit_end().toString());
        sales_begin.setText(recruit.getSales_begin().toString());
        sales_end.setText(recruit.getSales_end().toString());
        address.setText(recruit.getAddress());
        explanation.setText(recruit.getExplanation());
        reference_url.setText(recruit.getReference_url());
    }
    @FXML
    void click_cancel(MouseEvent event) {
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("admin/recruitManagement/RecruitMainView.fxml")));
        stage.show();
    }

    @FXML
    void click_update(MouseEvent event) throws Exception {
        Data data = new Data();
        data.add("InquiryRecruit");
        data.add(number);
        network.write(data);

        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("admin/recruitManagement/UpdateRecruitView.fxml")));
        stage.show();
    }

    @FXML
    void click_delete(MouseEvent event) throws Exception{
        Data data = new Data();
        data.add("DeleteRecruit");
        data.add(number);
        network.write(data);

        data = network.read();
        boolean success = data.get(0);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("해당 모집공고가 삭제되었습니다.");
            alert.showAndWait();
            Stage stage = (Stage) name.getScene().getWindow();
            stage.close();

            stage.setScene(new Scene(loadFxml("admin/recruitManagement/RecruitMainView.fxml")));
            stage.show();
        } else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("뭔가 잘못되었습니다.");
            alert.showAndWait();
        }
    }

    @FXML
    void connect_URL(MouseEvent event) throws URISyntaxException, IOException {
        String URL = reference_url.getText();
        Desktop.getDesktop().browse(new URI(URL));
    }

}
