package view;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Course;
import model.Delivery;
import model.Module;

public class ModulesTab extends HBox {

	private ListView<Module> unselectedLV1, unselectedLV2, selectedLV1, selectedLV2, selectedYearLong;
	private Button addBtn1, removeBtn1, addBtn2, removeBtn2, resetBtn, submitBtn;
	private TextField totalCreditsTerm1, totalCreditsTerm2;
	private ObservableList<Module> unselectedTerm1Modules, unselectedTerm2Modules, yearlongModules, selectedTerm1Modules, selectedTerm2Modules;
	private int term1Credits = 0; 
	private int term2Credits = 0;
	
	public ModulesTab() {
		
		unselectedTerm1Modules = FXCollections.observableArrayList();
		unselectedTerm2Modules = FXCollections.observableArrayList();
		selectedTerm1Modules = FXCollections.observableArrayList();
		selectedTerm2Modules = FXCollections.observableArrayList();
		yearlongModules = FXCollections.observableArrayList();
	
		//Container 1 contains the first rows of listviews
		VBox container1 = new VBox();
		container1.setPadding(new Insets(10));
		
		//unselected term1 modules
		VBox vbox1 = new VBox();
		vbox1.setPadding(new Insets(10));
		unselectedLV1 = new ListView<>(unselectedTerm1Modules);
		unselectedLV1.setPrefSize(350, 125);
		vbox1.getChildren().addAll(new Label("Unselected Term 1 modules"), unselectedLV1);
		VBox.setVgrow(unselectedLV1, Priority.ALWAYS);
		
		HBox hbox1 = new HBox();
		hbox1.setSpacing(15);
		hbox1.setAlignment(Pos.CENTER);
		hbox1.setPadding(new Insets(10));
		
		addBtn1 = new Button("Add");
		addBtn1.setPrefWidth(80);
		removeBtn1 = new Button("Remove");
		removeBtn1.setPrefWidth(80);
		
		hbox1.getChildren().addAll(new Label("Term 1"), addBtn1, removeBtn1);
		
		//unselected term 2 modules
		VBox vbox2 = new VBox();
		vbox2.setPadding(new Insets(10));
		unselectedLV2 = new ListView<>(unselectedTerm2Modules);
		unselectedLV2.setPrefSize(350, 125);
		vbox2.getChildren().addAll(new Label("Unselected Term 2 modules"), unselectedLV2);
		VBox.setVgrow(unselectedLV2, Priority.ALWAYS);
		
		HBox hbox2 = new HBox();
		hbox2.setSpacing(15);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.setPadding(new Insets(10));
		
		addBtn2 = new Button("Add");
		addBtn2.setPrefWidth(80);
		removeBtn2 = new Button("Remove");
		removeBtn2.setPrefWidth(80);
		
		hbox2.getChildren().addAll(new Label("Term 2"), addBtn2, removeBtn2);
		
		//Term 1 credits
		HBox creditHbox1 = new HBox();
		creditHbox1.setSpacing(15);
		creditHbox1.setAlignment(Pos.CENTER_RIGHT);
		creditHbox1.setPadding(new Insets(10,25,10,10));
		
		totalCreditsTerm1 = new TextField(term1Credits + "");
		totalCreditsTerm1.setPrefWidth(90);
		
		creditHbox1.getChildren().addAll(new Label("Current term 1 credits: "), totalCreditsTerm1);
		
		//resets button
		HBox resetHbox = new HBox();
		resetHbox.setAlignment(Pos.CENTER_RIGHT);
		resetHbox.setPadding(new Insets(10));
		
		resetBtn = new Button("Reset");
		resetBtn.setPrefWidth(90);
		resetHbox.getChildren().add(resetBtn);
		
		container1.getChildren().addAll(vbox1, hbox1, vbox2, hbox2, creditHbox1, resetHbox);
		VBox.setVgrow(vbox1, Priority.ALWAYS);
		VBox.setVgrow(vbox2, Priority.ALWAYS);
		
		
		//container2 contains the second row of listviews
		VBox container2 = new VBox();
		container2.setPadding(new Insets(10));
		
		//Yearlong Modules
		VBox vbox3 = new VBox();
		vbox3.setPadding(new Insets(10));
		selectedYearLong = new ListView<>(yearlongModules);
		selectedYearLong.setPrefSize(350, 50);
		vbox3.getChildren().addAll(new Label("Selected Year Long modules"), selectedYearLong);
		VBox.setVgrow(selectedYearLong, Priority.ALWAYS);
		
		//Selected Term 1 modules
		VBox vbox4 = new VBox();
		vbox4.setPadding(new Insets(10));
		selectedLV1 = new ListView<>(selectedTerm1Modules);
		selectedLV1.setPrefSize(350, 125);
		vbox4.getChildren().addAll(new Label("Selected Term 1 modules"), selectedLV1);
		VBox.setVgrow(selectedLV1, Priority.ALWAYS);
		
		//Selected Term 2 modules 
		VBox vbox5 = new VBox();
		vbox5.setPadding(new Insets(10));
		selectedLV2 = new ListView<>(selectedTerm2Modules);
		selectedLV2.setPrefSize(350, 125);
		vbox5.getChildren().addAll(new Label("Selected Term 2 modules"), selectedLV2);
		VBox.setVgrow(selectedLV2, Priority.ALWAYS);
		
		HBox creditHbox2 = new HBox();
		creditHbox2.setSpacing(15);
		creditHbox2.setPadding(new Insets(10,10,10,25));
		creditHbox2.setAlignment(Pos.CENTER_LEFT);
		
		totalCreditsTerm2 = new TextField(term2Credits + "");
		totalCreditsTerm2.setPrefWidth(80);
		
		creditHbox2.getChildren().addAll(new Label("Current term 2 credits: "), totalCreditsTerm2);
		
		HBox submitHbox = new HBox();
		submitHbox.setAlignment(Pos.CENTER_LEFT);
		submitHbox.setPadding(new Insets(10));
		
		submitBtn = new Button("Submit");
		submitBtn.setPrefWidth(90);
		
		submitHbox.getChildren().add(submitBtn);
		
		container2.getChildren().addAll(vbox3, vbox4, vbox5, creditHbox2, submitHbox);
		VBox.setVgrow(vbox3, Priority.ALWAYS);
		VBox.setVgrow(vbox4, Priority.ALWAYS);
		VBox.setVgrow(vbox5, Priority.ALWAYS);
		
		//adding containers to the pane
		this.getChildren().addAll(container1, container2);
		HBox.setHgrow(container1, Priority.ALWAYS);
		HBox.setHgrow(container2, Priority.ALWAYS);
	}
	
	//populates listview
	public void populateListView(Course courses) {
		courses.getAllModulesOnCourse().stream().filter(e -> e.isMandatory() && e.getRunPlan() == Delivery.TERM_1).forEach(e -> selectedTerm1Modules.add(e));
		courses.getAllModulesOnCourse().stream().filter(e -> !e.isMandatory() && e.getRunPlan() == Delivery.TERM_1).forEach(e -> unselectedTerm1Modules.add(e));
		courses.getAllModulesOnCourse().stream().filter(e -> e.isMandatory() && e.getRunPlan() == Delivery.TERM_2).forEach(e -> selectedTerm2Modules.add(e));
		courses.getAllModulesOnCourse().stream().filter(e -> !e.isMandatory() && e.getRunPlan() == Delivery.TERM_2).forEach(e -> unselectedTerm2Modules.add(e));
		courses.getAllModulesOnCourse().stream().filter(e -> e.isMandatory() && e.getRunPlan() == Delivery.YEAR_LONG).forEach(e -> yearlongModules.add(e));
	}
	
	public ObservableList<Module> getUnselectedTerm1Modules(){
		return unselectedTerm1Modules;
	}
	
	public void addToSelectedTerm1Modules(Module module) {
		selectedTerm1Modules.add(module);
	}
	
	public ListView<Module> getUnselectedTerm1ListView(){
		return unselectedLV1;
	}
	
	public ObservableList<Module> getUnselectedTerm2Modules(){
		return unselectedTerm2Modules;
	}
	
	public ListView<Module> getUnselectedTerm2ListView(){
		return unselectedLV2;
	}
	
	public ObservableList<Module> getSelectedTerm1Modules(){
		return selectedTerm1Modules;
	}
	
	public void removeSelectedTerm1Modules(Module module) {
		selectedTerm1Modules.remove(module);
	}
	
	public ListView<Module> getSelectedTerm1ListView(){
		return selectedLV1;
	}
	
	public ObservableList<Module> getSelectedTerm2Modules(){
		return selectedTerm2Modules;
	}
	
	public ListView<Module> getSelectedTerm2ListView(){
		return selectedLV2;
	}
	
	public ObservableList<Module> getYearLongModule(){
		return yearlongModules;
	}
	
	public Button getAddButton1() {
		return addBtn1;
	}
	
	public Button getAddButton2() {
		return addBtn2;
	}
	
	public Button getRemoveButton1() {
		return removeBtn1;
	}
	
	public Button getRemoveButton2() {
		return removeBtn2;
	}
	
	//Button listener methods
	public void addAddBtn1Listener(EventHandler<ActionEvent> handler) {
		addBtn1.setOnAction(handler);
	}
	
	public void addAddBtn2Listener(EventHandler<ActionEvent> handler) {
		addBtn2.setOnAction(handler);
	}
	
	public void addRemoveBtn1Listener(EventHandler<ActionEvent> handler) {
		removeBtn1.setOnAction(handler);
	}
	
	public void addRemoveBtn2Listener(EventHandler<ActionEvent> handler) {
		removeBtn2.setOnAction(handler);
	}
	
	public void addResetBtnListener(EventHandler<ActionEvent> handler) {
		resetBtn.setOnAction(handler);
	}
	
	public void addSubmitBtnListener(EventHandler<ActionEvent> handler) {
		submitBtn.setOnAction(handler);
	}
	
	public void incrementTerm1Credits(int credits) {
		term1Credits += credits;
		totalCreditsTerm1.setText(term1Credits + "");
	}
	
	public void incrementTerm2Credits(int credits) {
		term2Credits += credits;
		totalCreditsTerm2.setText(term2Credits + "");
	}
	
	public void decrementTerm1Credits(int credits) {
		term1Credits -= credits;
		totalCreditsTerm1.setText(term1Credits + "");
	}
	
	public void decrementTerm2Credit(int credits) {
			term2Credits -= credits;
			totalCreditsTerm2.setText(term2Credits + "");
	}
	
	public int getTerm1Credits() {
		return term1Credits;
	}
	
	public int getTerm2Credits() {
		return term2Credits;
	}
	
	public void resetCredits() {
		term1Credits = 0;
		term2Credits = 0;
		
		totalCreditsTerm1.setText(term1Credits + "");
		totalCreditsTerm2.setText(term2Credits + "");
	}
	
}
