package teumin.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    void text_register_onClick(MouseEvent event) throws Exception
    {
        Parent register = FXMLLoader.load(getClass().getResource("/view/RegisterView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("회원가입");
        stage.setScene(new Scene(register));
        stage.show();
    }

    @FXML
    void btn_login_onClick(MouseEvent event) {
        login(
                text_id.getText(),
                text_password.getText()
        );
    }

    private void login(String id, String password) {
        try {
            Data data = null;

            data = new Data(DataType.LOGIN_REQUEST);
            data.addObject(id);
            data.addObject(password);
            network.write(data);

            data = network.read();
            if (data.getDataType() == DataType.LOGIN_RESPOND)
                System.out.println(data.getObject(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
