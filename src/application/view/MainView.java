package application.view;

import java.util.Arrays;

import application.dialog.LayoutInflater;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public class MainView implements View {

	@Override
	public Parent getView() {
			Parent parent = LayoutInflater.inflate("activity_main", Parent.class);
			AnchorPane main_left = (AnchorPane) parent.lookup("#main_left");

			TreeView treeView = new TreeView();
			// TreeView������Ҳ�Ƿ�Node���ͣ����Բ�����SceneBuilder��ͼ��
		    TreeItem<String> treeItemRoot = new TreeItem<String>("Root node");
		    treeItemRoot.getChildren().addAll(Arrays.asList(
	                new TreeItem<String>("��1��"),
	                new TreeItem<String>("��2��"),
	                new TreeItem<String>("��3��")));

		    treeItemRoot.getChildren().get(2).getChildren().addAll(Arrays.asList(
	                new TreeItem<String>("��3��-1"),
	                new TreeItem<String>("��3��-1"),
	                new TreeItem<String>("��3��-1")
	                ));

		    treeItemRoot.setExpanded(true);

	        treeView.setShowRoot(true);
	        treeView.setRoot(treeItemRoot);

	        main_left.getChildren().add(treeView);
	        return parent;
	}

}
