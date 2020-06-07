package teumin.client.controller.admin.recruitManagement;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.entity.Recruit;
import teumin.network.Data;

public class UpdateRecruitController extends Client {
    private int number = -1;
    @FXML
    private TextField text_name;

    @FXML
    private DatePicker recruit_begin;
    @FXML
    private DatePicker recruit_end;

    @FXML
    private DatePicker sales_begin;
    @FXML
    private DatePicker sales_end;

    @FXML
    private TextField text_address;

    @FXML
    private TextArea text_explanation;

    @FXML
    private TextField text_reference_url;

    @FXML
    void click_cancel(MouseEvent event) {
        Stage stage = (Stage) text_name.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(loadFxml("admin/recruitManagement/RecruitMainView.fxml")));
        stage.show();
    }

    @FXML
    public void initialize() throws Exception {
        Data data = network.read();
        Recruit recruit = null;
        if (data.get(0) != null) {
            recruit = (Recruit) data.get(0);
        }
        number = recruit.getNumber();
        text_name.setText(recruit.getName());
        recruit_begin.setValue(recruit.getRecruit_begin());
        recruit_end.setValue(recruit.getRecruit_end());
        sales_begin.setValue(recruit.getSales_begin());
        sales_end.setValue(recruit.getSales_end());
        text_address.setText(recruit.getAddress());
        text_explanation.setText(recruit.getExplanation());
        text_reference_url.setText(recruit.getReference_url());
    }

    @FXML
    void click_update(MouseEvent event) throws Exception {
        Recruit recruit = new Recruit(
                number,
                text_name.getText(),
                recruit_begin.getValue(),
                recruit_end.getValue(),
                sales_begin.getValue(),
                sales_end.getValue(),
                text_address.getText(),
                text_explanation.getText(),
                text_reference_url.getText()
        );

        Data data = new Data();
        data.add("UpdateRecruit");
        data.add(recruit);
        network.write(data);

        data = network.read();
        boolean success = data.get(0);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("수정되었습니다.");
            alert.showAndWait();

            data = new Data();
            data.add("InquiryRecruit");
            data.add(number);
            network.write(data);

            Stage stage = (Stage) text_name.getScene().getWindow();
            stage.close();

            stage.setScene(new Scene(loadFxml("admin/recruitManagement/RecruitDetailView.fxml")));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("형식 오류입니다. 다시 한 번 생각해보세요.");
            alert.showAndWait();
        }
    }
}
