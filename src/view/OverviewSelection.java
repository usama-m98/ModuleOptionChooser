package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class OverviewSelection extends VBox {

	private TextArea overview;
	private Button saveBtn;
	
	public OverviewSelection() {
		
		HBox hbox1 = new HBox();
		hbox1.setPadding(new Insets(20));
		hbox1.setAlignment(Pos.CENTER);
		overview = new TextArea();
		overview.setPrefSize(500, 280);
		overview.setPadding(new Insets(10));

		hbox1.getChildren().add(overview);
		HBox.setHgrow(overview, Priority.ALWAYS);
		
		
		HBox hbox2 = new HBox();
		hbox2.setAlignment(Pos.CENTER);
		hbox2.setPadding(new Insets(20));
		
		saveBtn = new Button("Save Overview");
		
		hbox2.getChildren().add(saveBtn);
		
		this.getChildren().addAll(hbox1, hbox2);
		VBox.setVgrow(hbox1, Priority.ALWAYS);
	}
	
	public void setOverView(String info) {
		overview.setText(info);
	}
	
	public void addSaveOverViewListener(EventHandler<ActionEvent> handler) {
		saveBtn.setOnAction(handler);
	}
}
