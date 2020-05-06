package teumin.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RegisterController extends Controller {

    @FXML
    private TextField text_id;

    @FXML
    private PasswordField text_password;

    @FXML
    private TextField text_nickname;

    @FXML
    private Button btn_register;

    @FXML
    private Button btn_cancel;

    @FXML
    void btn_register_onClick(MouseEvent event)
    {
        if(isIdExistent(text_id.getText()))
        {
            System.out.println(text_id.getText());
            System.out.println(text_password.getText());
            System.out.println(text_nickname.getText());

            //dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("회원가입이 완료되었습니다.");

            alert.showAndWait();

            Stage stage = (Stage) btn_register.getScene().getWindow();  //get a handle
            stage.close();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("해당하는 id가 이미 존재합니다.");

            alert.showAndWait();

            text_id.setText(""); // id 입력란 초기화
        }
    }

    @FXML
    void btn_cancel_onClick(MouseEvent event)
    {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();  //get a handle
        stage.close();
    }

    boolean isIdExistent(String id) // id 중복확인.
    {
        return true;
    }

}
