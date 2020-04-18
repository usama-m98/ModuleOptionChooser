package view;


import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Course;
import model.Name;


public class ProfileTab extends VBox {

	private TextField pNumber, firstName, surname, email;
	private ComboBox<Course> cboCourse;
	private Button submitBtn;
	private DatePicker submissionDate;

	public ProfileTab() {

		HBox hbox = new HBox();
		hbox.setSpacing(15);

		//labels
		Label inputCourse = new Label("Select course:");
		Label inputPNumber = new Label("Input P number:");
		Label inputFirstName = new Label("Input first name:");
		Label inputSurname = new Label("Input surname:");
		Label inputEmail = new Label("Input email:");
		Label inputDate = new Label("Input date:");

		VBox vbox1 = new VBox();
		vbox1.setSpacing(20);
		vbox1.setPadding(new Insets(10));
		vbox1.setAlignment(Pos.BASELINE_RIGHT);
		vbox1.getChildren().addAll(inputCourse, inputPNumber, inputFirstName, inputSurname,
				inputEmail, inputDate);

		cboCourse = new ComboBox<Course>();

		//TextFields
		pNumber = new TextField();
		firstName = new TextField();
		surname = new TextField();
		email = new TextField();

		submissionDate = new DatePicker();
		submitBtn = new Button("Create Profile");
		

		VBox vbox2 = new VBox();
		vbox2.setSpacing(10);
		vbox2.setPadding(new Insets(10));
		vbox2.getChildren().addAll(cboCourse, pNumber, firstName, surname, email, submissionDate, submitBtn);

		hbox.getChildren().addAll(vbox1, vbox2);
		hbox.setAlignment(Pos.CENTER);

		this.getChildren().add(hbox);
		this.setAlignment(Pos.CENTER);
	}
	

	public void populateComboBoxWithCourses(Course[] courses) {
		cboCourse.getItems().addAll(courses);
		cboCourse.getSelectionModel().select(0);
	}

	public Course getCourse() {
		return cboCourse.getSelectionModel().getSelectedItem();
	}

	public String getPNumber() {
		return pNumber.getText();
	}

	public Name getName() {
		String fn = firstName.getText().substring(0, 1).toUpperCase() + firstName.getText().substring(1, firstName.getText().length());
		String sn = surname.getText().substring(0, 1).toUpperCase() + surname.getText().substring(1, surname.getText().length());
		return new Name(fn, sn);
	}

	public String getEmail() {
		return email.getText();
	}

	public LocalDate getDate() {
		return submissionDate.getValue();
	}

	public void addCreateProfileListener(EventHandler<ActionEvent> handler) {
		submitBtn.setOnAction(handler);
	}
	
	public void setDefault() {
		cboCourse.getSelectionModel().select(0);
		pNumber.setText("");
		firstName.setText("");
		surname.setText("");
		email.setText("");
		submissionDate.setValue(LocalDate.now());
	}
}
