package teumin.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.network.Data;

public class RegisterAccountController extends Client {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField text_id;

    @FXML
    private PasswordField text_password;

    @FXML
    private TextField text_name;

    @FXML
    private Button btn_register;

    @FXML
    private Button btn_cancel;

    @FXML
    void btn_cancel_onClick(MouseEvent event) {
        Stage stage = (Stage)root.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btn_register_onClick(MouseEvent event) throws Exception {
        String id = text_id.getText();
        String password = text_password.getText();
        String name = text_name.getText();

        Data data = new Data();
        data.add("RegisterAccount");
        data.add(id);
        data.add(password);
        data.add(name);
        network.write(data);

        data = network.read();
        boolean success = data.<Boolean>get(0);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("회원가입이 완료되었습니다.");
            alert.showAndWait();

            Stage stage = (Stage)root.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("아이디 중복 또는 형식 오류입니다.");
            alert.showAndWait();

            text_id.setText("");
            text_password.setText("");
            text_name.setText("");
        }
    }

}
