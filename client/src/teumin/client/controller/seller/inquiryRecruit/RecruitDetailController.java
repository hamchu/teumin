package teumin.client.controller.seller.inquiryRecruit;

import javafx.fxml.FXML;
import javafx.scene.Scene;
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

        stage.setScene(new Scene(loadFxml("seller/inquiryRecruit/InquiryRecruitView.fxml")));
        stage.show();
    }
    @FXML
    void connect_URL(MouseEvent event) throws URISyntaxException, IOException {
        String URL = reference_url.getText();
        Desktop.getDesktop().browse(new URI(URL));
    }

}
