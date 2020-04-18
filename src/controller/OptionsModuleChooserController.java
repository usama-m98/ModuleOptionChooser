package controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Course;
import model.Delivery;
import model.Module;
import model.StudentProfile;
import view.ModulesMenuBar;
import view.ModulesTab;
import view.OptionsModuleChooserRootPane;
import view.OverviewSelection;
import view.ProfileTab;


public class OptionsModuleChooserController {

	//fields to be used throughout the class
	private OptionsModuleChooserRootPane view;
	private StudentProfile model;
	private ProfileTab pt;
	private ModulesTab mt;
	private OverviewSelection os;
	private ModulesMenuBar mb;
	private String result = "";

	public OptionsModuleChooserController(OptionsModuleChooserRootPane view, StudentProfile model) {
		//initialise model and view fields
		this.model = model;
		this.view = view;
		this.pt = view.getProfileTab();
		this.mt = view.getModulesTab();
		this.os = view.getOverviewSelection();
		this.mb = view.getModulesMenuBar();

		//populate combobox in create profile pane, e.g. if profilePane represented your create profile pane you could invoke the line below
		pt.populateComboBoxWithCourses(setupAndRetrieveCourses());
		
		
		//attach event handlers to view using private helper method
		this.attachEventHandlers();	

	}

	private void attachEventHandlers() {
		
		pt.addCreateProfileListener(new CreateProfileHandler());
		mt.addAddBtn1Listener(new AddHandlers());
		mt.addAddBtn2Listener(new AddHandlers());
		mt.addRemoveBtn1Listener(new RemoveHandler());
		mt.addRemoveBtn2Listener(new RemoveHandler());
		mt.addResetBtnListener(new ResetHandler());
		mt.addSubmitBtnListener(new SubmitHandler());
		os.addSaveOverViewListener(new SaveOverViewHandler());
		mb.addLoadHandler(new LoadMenuHandler());
		mb.addSaveHandler(new SaveMenuHandler());
		mb.addExitHandler(e-> System.exit(0));
		mb.addAboutHandler(new AboutMenuHandler());
	}

	
	
	private class CreateProfileHandler implements EventHandler<ActionEvent>{
		
		public void handle(ActionEvent e) {
			model.setCourseOfStudy(pt.getCourse());
			model.setPnumber(pt.getPNumber());
			model.setStudentName(pt.getName());
			model.setEmail(pt.getEmail());
			model.setSubmissionDate(pt.getDate());
			
			Course[] course = setupAndRetrieveCourses();
			
			if(pt.getCourse().getCourseName().equals(course[0].getCourseName())) {
				mt.populateListView(course[0]);
				mt.incrementTerm1Credits(30);
				mt.incrementTerm2Credits(15);
			}else if(pt.getCourse().getCourseName().equals(course[1].getCourseName())) {
				mt.populateListView(course[1]);
				mt.incrementTerm1Credits(30);
				mt.incrementTerm2Credits(30);
			}
			view.changeTab(1);
			
			pt.setDefault();
		}	
	}
	
	private class AddHandlers implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {

			if(event.getSource() == mt.getAddButton1()) {
				if(mt.getTerm1Credits() < 60) {
					Module selectedItem = mt.getUnselectedTerm1ListView().getSelectionModel().getSelectedItem();
					mt.addToSelectedTerm1Modules(selectedItem);
					mt.getUnselectedTerm1Modules().remove(selectedItem);
					mt.incrementTerm1Credits(selectedItem.getCredits());
				}else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("60 Credit Limit");
					alert.setContentText("Total Credits for this term is 60 credits");
					alert.showAndWait();
				}
			}else if(event.getSource() == mt.getAddButton2()) {
				if(mt.getTerm2Credits() < 60) {
					Module selectedItem = mt.getUnselectedTerm2ListView().getSelectionModel().getSelectedItem();
					mt.getSelectedTerm2Modules().add(selectedItem);
					mt.getUnselectedTerm2Modules().remove(selectedItem);
					mt.incrementTerm2Credits(selectedItem.getCredits());
				}else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("60 Credit Limit");
					alert.setContentText("Total Credits for this term is 60 credits");
					alert.showAndWait();
				}
			}
		}
	}
	
	private class RemoveHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			Stream<Module> filterForMandatory = mt.getSelectedTerm1Modules().stream().filter(e -> e.isMandatory() && 
					(e.getRunPlan() == Delivery.TERM_1 || e.getRunPlan() == Delivery.TERM_2));
			
			Stream<Module> filterForMandatory2 = mt.getSelectedTerm2Modules().stream().filter(e -> e.isMandatory() && 
					(e.getRunPlan() == Delivery.TERM_1 || e.getRunPlan() == Delivery.TERM_2));
			
			if(event.getSource() == mt.getRemoveButton1()) {
				Module selectedItem = mt.getSelectedTerm1ListView().getSelectionModel().getSelectedItem();
				if(!filterForMandatory.anyMatch(e -> e.equals(selectedItem))) {
					mt.removeSelectedTerm1Modules(selectedItem);
					mt.getUnselectedTerm1Modules().add(selectedItem);
					mt.decrementTerm1Credits(selectedItem.getCredits());
				}else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Selection Error");
					alert.setContentText("This course is mandatory");
					alert.showAndWait();
				}
			}else if(event.getSource() == mt.getRemoveButton2()) {
				Module selectedItem = mt.getSelectedTerm2ListView().getSelectionModel().getSelectedItem();
				if(!filterForMandatory2.anyMatch(e -> e.equals(selectedItem))) {
					mt.getSelectedTerm2Modules().remove(selectedItem);
					mt.getUnselectedTerm2Modules().add(selectedItem);
					mt.decrementTerm2Credit(selectedItem.getCredits());
				}else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Selection Error");
					alert.setContentText("This course is mandatory");
					alert.showAndWait();
				}
			}
		}
	}
	           
	private class ResetHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			mt.getUnselectedTerm1Modules().clear();
			mt.getUnselectedTerm2Modules().clear();
			mt.getSelectedTerm1Modules().clear();
			mt.getSelectedTerm2Modules().clear();
			mt.getYearLongModule().clear();
			mt.resetCredits();
			
			
			Course[] course = setupAndRetrieveCourses();
			
			if(pt.getCourse().getCourseName().equals(course[0].getCourseName())) {
				mt.populateListView(course[0]);
				mt.incrementTerm1Credits(30);
				mt.incrementTerm2Credits(15);
			}else if(pt.getCourse().getCourseName().equals(course[1].getCourseName())) {
				mt.populateListView(course[1]);
				mt.incrementTerm1Credits(30);
				mt.incrementTerm2Credits(30);
			}
		}
	}
	
	private class SubmitHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			result += "Name: " + model.getStudentName().getFullName() + "\n" +
					"PNo: " + model.getPnumber() + "\n" +
					"Email: " + model.getEmail() + "\n" + 
					"Date: " + model.getSubmissionDate() + "\n" +
					"Course: " + model.getCourseOfStudy() + "\n" + "\nSelected modules:\n" + "========\n";
			
			mt.getYearLongModule().stream().forEach(e -> result += "\nModule code: " + e.getModuleCode() + ", Module name: " + e.getModuleName() +
					"\nCredits: " + e.getCredits() + ", Mandatory on your course? " + ((e.isMandatory() == true) ? "yes" : "no") + ", Delivery: " +
					((e.getRunPlan() == Delivery.YEAR_LONG) ? "Year-long" : "") + "\n" 
					);
			
			mt.getSelectedTerm1Modules().stream().forEach(e -> result += "\nModule code: " + e.getModuleCode() + ", Module name: " + e.getModuleName() +
		"\nCredits: " + e.getCredits() + ", Mandatory on your course? " + ((e.isMandatory() == true) ? "yes" : "no") + ", Delivery: " + 
					((e.getRunPlan() == Delivery.TERM_1)? "Term 1" : "Term 2") + "\n" 
			);
			
			mt.getSelectedTerm2Modules().stream().forEach(e -> result += "\nModule code: " + e.getModuleCode() + ", Module name: " + e.getModuleName() +
					"\nCredits: " + e.getCredits() + ", Mandatory on your course? " + ((e.isMandatory() == true) ? "yes" : "no") + ", Delivery: " + 
					((e.getRunPlan() == Delivery.TERM_2) ? "Term 2" : "Term 1") + "\n" 
						);
			
			os.setOverView(result);
			view.changeTab(2);
		}
	}
	
	private class SaveOverViewHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			PrintWriter printWriter = null;
			try {
				printWriter = new PrintWriter(new File("StudentDetails.txt"));
				printWriter.println(result);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}finally {
				printWriter.close();
			}
		}
		
	}
	
	private class LoadMenuHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("studentProfile.dat"));) {
				
				model =  (StudentProfile) ois.readObject(); 	
	
	        }
	        catch (IOException ioExcep){
	            System.out.println("Error loading");
	        }
			catch (ClassNotFoundException c) {
	            System.out.println("Class Not found");
	        }
		}
	}
	
	private class SaveMenuHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("studentProfile.dat"));) {

				oos.writeObject(model); 
				oos.flush();
				
			}
			catch (IOException ioExcep){
				System.out.println("Error saving");
			}
		}
		
	}
	
	private class AboutMenuHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent arg0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Final-Year Module Chooser v1.0");
			alert.showAndWait();
		}	
	}
	
	private Course[] setupAndRetrieveCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Delivery.TERM_1);
		Module imat3451 = new Module("IMAT3451", "Computing Project", 30, true, Delivery.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, Delivery.TERM_2);	
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, Delivery.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Delivery.TERM_1);
		Module ctec3426 = new Module("CTEC3426", "Telematics", 15, false, Delivery.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Delivery.TERM_1);	
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Delivery.TERM_2);	
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Delivery.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Delivery.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Delivery.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Delivery.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Delivery.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Delivery.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Popular Technology Ethics", 15, false, Delivery.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Delivery.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, Delivery.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, Delivery.TERM_2);

		Course compSci = new Course("Computer Science");
		compSci.addModuleToCourse(imat3423);
		compSci.addModuleToCourse(imat3451);
		compSci.addModuleToCourse(ctec3902_CompSci);
		compSci.addModuleToCourse(ctec3110);
		compSci.addModuleToCourse(ctec3426);
		compSci.addModuleToCourse(ctec3605);
		compSci.addModuleToCourse(ctec3606);
		compSci.addModuleToCourse(ctec3410);
		compSci.addModuleToCourse(ctec3904);
		compSci.addModuleToCourse(ctec3905);
		compSci.addModuleToCourse(ctec3906);
		compSci.addModuleToCourse(imat3410);
		compSci.addModuleToCourse(imat3406);
		compSci.addModuleToCourse(imat3611);
		compSci.addModuleToCourse(imat3613);
		compSci.addModuleToCourse(imat3614);
		compSci.addModuleToCourse(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModuleToCourse(imat3423);
		softEng.addModuleToCourse(imat3451);
		softEng.addModuleToCourse(ctec3902_SoftEng);
		softEng.addModuleToCourse(ctec3110);
		softEng.addModuleToCourse(ctec3426);
		softEng.addModuleToCourse(ctec3605);
		softEng.addModuleToCourse(ctec3606);
		softEng.addModuleToCourse(ctec3410);
		softEng.addModuleToCourse(ctec3904);
		softEng.addModuleToCourse(ctec3905);
		softEng.addModuleToCourse(ctec3906);
		softEng.addModuleToCourse(imat3410);
		softEng.addModuleToCourse(imat3406);
		softEng.addModuleToCourse(imat3611);
		softEng.addModuleToCourse(imat3613);
		softEng.addModuleToCourse(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}
}
