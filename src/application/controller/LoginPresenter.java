package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import application.dialog.DialogHelper;
import application.util.L;
import application.view.MainView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
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
			MainView mainView = new MainView();

			Parent parent = mainView .getView();

			Stage stage = (Stage) ap_login.getScene().getWindow();
			stage.setScene(new Scene(parent,1000,600));// ����Scene,���򲻻��Զ��޸Ĵ�С��
			stage.setFullScreen(false);
			// �����м�λ��

			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();

			double minX = bounds.getMinX();// 0
			double minY = bounds.getMinY();// 0
			double maxX = bounds.getMaxX();// 1366.0
			double maxY = bounds.getMaxY();// 728.0

			double layoutX = (maxX - stage.getWidth())/2;
			double layoutY = (maxY - stage.getHeight())/2;

			stage.setX(layoutX);
			stage.setY(layoutY);
			stage.setOnCloseRequest(e->{
				Platform.exit();// ������̳߳�,����ر��̳߳�,�������ʹ����Platform.exit()Ҳ�����Զ��˳�
			});
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
