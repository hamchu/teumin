package teumin.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.network.Data;
import teumin.network.DataType;

public class Login extends Client {

    @FXML
    private TextField text_id;

    @FXML
    private PasswordField text_password;

    @FXML
    private Text text_register;

    @FXML
    private Button btn_login;

    @FXML
    void text_register_onClick(MouseEvent event) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("회원가입");
        stage.setScene(new Scene(loadFxml("Register")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show(); // TODO test
    }

    @FXML
    void btn_login_onClick(MouseEvent event) throws Exception {
        boolean login_success = login(
                text_id.getText(),
                text_password.getText()
        );
        if(login_success)
        {
            Stage stage = (Stage) btn_login.getScene().getWindow(); //get a handle
            stage.close();

            stage.setScene(new Scene(loadFxml("MainView")));
            stage.show();
            //접속한 id 정보 넘기기..
        }
    }

    boolean login(String id, String password) throws Exception {
        Data data = null;

        data = new Data(DataType.LOGIN_REQUEST);
        data.add(id);
        data.add(password);
        network.write(data);

        data = network.read();
        if (data.getDataType() != DataType.LOGIN_RESPOND)
            throw new Exception("알 수 없는 응답");

        if (data.<Boolean>get(0)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("로그인에 성공하였습니다.\n" + data.<String>get(1) + " 님 환영합니다.");
            alert.showAndWait();

            return true;
            // TODO
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("로그인에 실패하였습니다.\n아이디와 비밀번호를 다시 확인해주세요.");
            alert.showAndWait();

            text_password.setText(""); // password 입력란 초기화
            return false;
        }
    }

}
