package application;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

import application.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.jimmc.jshortcut.JShellLink;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(new LoginView().getView());
			scene.getStylesheets().add("css/application.css");

			primaryStage.setTitle(Constants.APP_NAME);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(Constants.APP_LOGO);
			primaryStage.show();

			createShortcut();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/** ������ݷ�ʽ */
	private void createShortcut() {
		 // ��ȡϵͳ����·��
        String desktop = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
        // ����ִ���ļ�·��
        String appPath = System.getProperty("user.dir");
        File exePath = new File(appPath).getParentFile();

        JShellLink link = new JShellLink();
        link.setFolder(desktop);
        link.setName("Notebook.exe");
        link.setPath(exePath.getAbsolutePath() + File.separator + "Notebook.exe");
        // link.setArguments("form");
        link.save();
        System.out.println("======== create success ========");
	}

	/** ��ȡһ����ݷ�ʽ��ʵ��ַ */
    public static String getShortCutRealPath(String fileFolderPath) {
        // ���ݿ�ݷ�ʽ��·�����ļ�����,��ȡԴ�ļ��е�ַ
        fileFolderPath.replaceAll("/", "\\");
        String folder = fileFolderPath.substring(0, fileFolderPath.lastIndexOf("\\"));
        String name = fileFolderPath.substring(fileFolderPath.lastIndexOf("\\") + 1, fileFolderPath.length());
        JShellLink link = new JShellLink(folder, name);
        link.load();
        return link.getPath();
    }

	public static void main(String[] args) {
		launch(args);
	}
}
