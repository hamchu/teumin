package teumin.client.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import teumin.client.Client;
import teumin.network.Data;

public class LoginAccountController extends Client {

    @FXML
    private Pane root;

    @FXML
    private TextField text_id;

    @FXML
    private PasswordField text_password;

    @FXML
    void click_login(MouseEvent event) throws Exception {
        String id = text_id.getText();
        String password = text_password.getText();

        Data data = new Data();
        data.add("LoginAccount");
        data.add(id);
        data.add(password);
        network.write(data);

        data = network.read();
        boolean success = data.<Boolean>get(0);
        String name = data.<String>get(1);
        int type = data.<Integer>get(2);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("로그인에 성공하였습니다.\n" + name + " 님 환영합니다.");
            alert.showAndWait();

            Stage stage = (Stage)root.getScene().getWindow();
            stage.close();

            switch (type) {
                case 0: // 관리자
                    stage.setScene(new Scene(loadFxml("admin/MainView.fxml")));
                    break;
                case 1: // 영업자
                    stage.setScene(new Scene(loadFxml("seller/MainView.fxml")));
                    break;
            }

            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("알림");
            alert.setHeaderText(null);
            alert.setContentText("로그인에 실패하였습니다.\n아이디와 비밀번호를 다시 확인해주세요.");
            alert.showAndWait();

            text_password.setText("");
        }
    }

    @FXML
    void click_register(MouseEvent event) {
        Stage stage = new Stage();
        stage.setTitle("회원가입");
        stage.getIcons().add(loadImage("teumin.png"));
        stage.setScene(new Scene(loadFxml("RegisterAccountView.fxml")));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

}
