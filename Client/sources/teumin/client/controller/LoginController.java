package teumin.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import teumin.network.Data;
import teumin.network.DataType;

public class LoginController extends Controller {

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegisterView.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
        ((Controller)loader.getController()).setNetwork(network);
    }

    @FXML
    void btn_login_onClick(MouseEvent event) throws Exception {
        login(
                text_id.getText(),
                text_password.getText()
        );
    }

    void login(String id, String password) throws Exception {
        Data data = null;

        data = new Data(DataType.LOGIN_REQUEST);
        data.addObject(id);
        data.addObject(password);
        network.write(data);

        data = network.read();
        if (data.getDataType() != DataType.LOGIN_RESPOND)
            throw new Exception("알 수 없는 응답");

        if ((boolean) data.getObject(0)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("로그인에 성공하였습니다.\n" + (String) data.getObject(1) + " 님 환영합니다.");
            alert.showAndWait();

            // TODO
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("로그인에 실패하였습니다.\n아이디와 비밀번호를 다시 확인해주세요.");
            alert.showAndWait();

            text_password.setText(""); // password 입력란 초기화
        }
    }

}
