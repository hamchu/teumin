package client.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
//import java.awt.event.MouseEvent;
import java.io.IOException;

public class Login {

    @FXML
    private TextField text_id;

    @FXML
    private PasswordField text_password;

    @FXML
    private Text text_register;

    @FXML
    private Button btn_login;

    @FXML
    void btn_login_onClick(MouseEvent event) {



    }

    @FXML
    void text_register_onClick(MouseEvent event) throws Exception
    {
        Parent register = FXMLLoader.load(getClass().getResource("/view/register.fxml"));
        Stage stage = new Stage();
        stage.setTitle("회원가입");
        stage.setScene(new Scene(register));
        stage.show();
    }

}
