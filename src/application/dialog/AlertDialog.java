package application.dialog;

import java.util.HashMap;

import application.Constants;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertDialog {
	private Parent root;

	private String view;
	private String title;
	private Stage ownerStage;
	private Stage stage;
	private HashMap<String,OnClickListener> listeners = new HashMap<>();

	public AlertDialog(Builder builder) {
		this.view = builder.view;
		this.title = builder.title;
		this.ownerStage = builder.ownerStage;
		this.stage = builder.stage;
		this.listeners = builder.listeners;

		initAlertDialog();
	}

	private void initAlertDialog() {
		try {
			root = LayoutInflater.inflate(view, Parent.class);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(ownerStage);

			// init listener
			for(String key : listeners.keySet()){
				Node node = root.lookup(key);
				if(node != null){
					node.setOnMouseClicked(e->{
						OnClickListener listener = listeners.get(key);
						listener.onClick(stage);
					});

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public <T>T findView(String view, Class<T> class1){
		T t = (T) root.lookup(view);
		return t;
	}

	public Builder newBuilder(){
		return new Builder(this);
	}

	public void show(){
			Scene scene = new Scene(root);
            stage.setTitle(Constants.APP_NAME + " " + title);
            stage.getIcons().add(Constants.APP_LOGO);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
	}

	public void close(){
		stage.close();
		listeners.clear();
	}

	public static final class Builder {
		 private String view;
		 private String title;
		 private Stage ownerStage;
		 private Stage stage;
		 private HashMap<String,OnClickListener> listeners;

		 public Builder(){
			 view = "empty";
			 title = Constants.APP_NAME;
			 stage = new Stage();// 需要重新创建一个
			 listeners = new HashMap<>();
		 }

		 public Builder(AlertDialog alertDialog) {
			 this.view = alertDialog.view;
		}

		public Builder view(String viewName){
			 this.view = viewName;
			 return this;
		 }

		public Builder title(String title){
			this.title = title;
			return this;
		}

		public Builder ownerStage(Stage ownerStage){
			this.ownerStage = ownerStage;
			return this;
		}

		 public AlertDialog build(){
			 return new AlertDialog(this);
		 }

		public Builder click(String clickNode, OnClickListener onClickListener) {
			listeners.put(clickNode, onClickListener);
			return this;
		}

	 }
}
