package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import application.dialog.DialogHelper;
import application.util.L;
import application.view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginPresenter implements Initializable {

	@FXML TextField et_username;
	@FXML TextField et_password;
	@FXML Button btn_login;
	@FXML Button btn_reset;
	@FXML AnchorPane ap_login;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML public void login(ActionEvent event) {

		String username = et_username.getText();
		String password = DigestUtils.md5Hex(et_password.getText());
		if(username.equals("admin")){
			L.d("��¼�ɹ���", null);
			// ���� scene�� ����
			Parent parent = new MainView().getView();

			Stage stage = (Stage) ap_login.getScene().getWindow();
			stage.setScene(new Scene(parent));// ����Scene,���򲻻��Զ��޸Ĵ�С��

		}else{
			DialogHelper.alert("��ʾ", "��¼ʧ�ܣ�");
			et_username.setText("");
			et_password.setText("");
		}
	}

	@FXML public void reset(ActionEvent event) {
		et_username.setText("");
		et_password.setText("");
	}


}
