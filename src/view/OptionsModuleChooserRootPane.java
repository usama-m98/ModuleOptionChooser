package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

//You may change this class to extend another type if you wish
public class OptionsModuleChooserRootPane extends VBox {

	private ProfileTab pt;
	private ModulesTab mt;
	private OverviewSelection os;
	private ModulesMenuBar mb;
	private TabPane tabPane;
	
	public OptionsModuleChooserRootPane() {
		this.pt = new ProfileTab();
		this.mt = new ModulesTab();
		this.os = new OverviewSelection();
		this.mb = new ModulesMenuBar();
		
		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		Tab pTab = new Tab("Create Profile", this.pt);
		Tab mTab = new Tab("Select Modules", this.mt);
		Tab oTab = new Tab("Overview Selection", this.os);
		
		tabPane.getTabs().addAll(pTab, mTab, oTab);
		this.getChildren().addAll(this.mb, tabPane);
		VBox.setVgrow(tabPane, Priority.ALWAYS);
		
	}
	
	public ProfileTab getProfileTab() {
		return pt;
	}
	
	public ModulesTab getModulesTab() {
		return mt;
	}
	
	public OverviewSelection getOverviewSelection() {
		return os;
	}
	
	public ModulesMenuBar getModulesMenuBar() {
		return mb;
	}
	
	public void changeTab(int index) {
		this.tabPane.getSelectionModel().select(index);
	}
}
