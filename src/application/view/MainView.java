package application.view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import application.dialog.LayoutInflater;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainView implements View {
	private Map<String,Parent> viewMap = new HashMap<>();

	private final ImageView rootIcon = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/blue/tree_root.png")));
	private final ImageView oneIcon = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/blue/tree_article.png")));
	private final ImageView twoIcon = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/blue/tree_category.png")));
	private final ImageView threeIcon = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("images/blue/tree_setting.png")));

	@Override
	public Parent getView() {
			Parent parent = LayoutInflater.inflate("activity_main", Parent.class);
			parent.getStylesheets().add("css/main.css");

			AnchorPane main_left = (AnchorPane) parent.lookup("#main_left");
			AnchorPane main_center = (AnchorPane) parent.lookup("#main_center");

			// left
			TreeView treeView = new TreeView();
			// TreeView������Ҳ�Ƿ�Node���ͣ����Բ�����SceneBuilder��ͼ��
		    TreeItem<String> treeItemRoot = new TreeItem<String>("�����˵�",rootIcon);

			TreeItem<String> item_1 = new TreeItem<String>("���¹���",oneIcon);
			TreeItem<String> item_2 = new TreeItem<String>("������",twoIcon);
			TreeItem<String> item_3 = new TreeItem<String>("ϵͳ����",threeIcon);
		    treeItemRoot.getChildren().addAll(Arrays.asList(item_1,item_2,item_3));

		    TreeItem<String> item_3_1 = new TreeItem<String>("xx");
		    TreeItem<String> item_3_2 = new TreeItem<String>("xx");
		    TreeItem<String> item_3_3 = new TreeItem<String>("�˳�");
		    treeItemRoot.getChildren().get(2).getChildren().addAll(Arrays.asList(item_3_1,item_3_2,item_3_3));

		    treeItemRoot.setExpanded(true);

	        treeView.setShowRoot(true);
	        treeView.setRoot(treeItemRoot);
	        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {

				@Override
				public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue,
						TreeItem<String> newValue) {
					switch (newValue.getValue()) {
					case "���¹���":
						listArticle(main_center);
						break;
					case "������":
						listCategory(main_center);
						break;
					case "ϵͳ����":

						break;
					case "�˳�":

						break;
					default:
						break;
					}
				}

			});
	        main_left.getChildren().add(treeView);

			// right
	        HBox lb_center = LayoutInflater.inflate("include_center", HBox.class);
	        main_center.getChildren().add(lb_center);
	        return parent;
	}

	protected void listCategory(AnchorPane main_center) {
		main_center.getChildren().clear();
		Parent parent = null;
		String key = "include_center_category";
		if(viewMap.containsKey(key)){
			parent = viewMap.get(key);
		}else{
			parent = LayoutInflater.inflate(key, Parent.class);
			viewMap.put(key, parent);
		}
		main_center.getChildren().add(parent);
	}

	protected void listArticle(AnchorPane main_center) {
		main_center.getChildren().clear();
		Parent parent = null;
		String key = "include_center_article";
		if(viewMap.containsKey(key)){
			parent = viewMap.get(key);
		}else{
			parent = LayoutInflater.inflate(key, Parent.class);
			viewMap.put(key, parent);
		}
		ListView listView = (ListView) parent.lookup("#lv_article");
		listView.setPrefWidth(200);
		main_center.getChildren().add(parent);
	}

}
