package application.fragment;

import java.util.Map;

import application.annotation.AnnotationHandler;
import application.annotation.Mode;
import application.annotation.ThreadMode;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;

public class DeployFragment extends Fragment {
	private Button btn_deploy;
	private ProgressIndicator progressbar;

	@Override
	public void onCreate(Map<String, String> bundle) {

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public String getLayout() {
		return "fragment_deploy";
	}

	@Override
	public void initData(Parent node, Map<String, String> bundle) {
		btn_deploy = (Button) node.lookup("#btn_deploy");
		progressbar = (ProgressIndicator) node.lookup("#progressbar");

		btn_deploy.setOnAction(e->{
			progressbar.isIndeterminate();// һ ��������ʾ�����ڷ�ȷ��ģʽ,����progressbar����һ����ֵ�и�Сbug������¡�
			progressbar.setVisible(true);
			progressbar.setProgress(-1f);
			progressbar.setProgress(0.5f);
			progressbar.setProgress(-1f);
			btn_deploy.setDisable(true);// �����ظ����

			AnnotationHandler.sendMessage("work",null);
		});

		AnnotationHandler.register(this);

	}

	@ThreadMode(mode = Mode.ASYNC,tag = "work")
	public void deploy(String param){
		// do work
		AnnotationHandler.sendMessage("done",null);
	}

	@ThreadMode(mode = Mode.MAIN,tag = "done")
	public void deploySuccess(String param){
		progressbar.setProgress(1f);// ���
		btn_deploy.setDisable(false);
	}

}
