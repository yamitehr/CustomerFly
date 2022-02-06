package boundry;

import java.sql.Date;

import control.LoginControl;
import entity.Customer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PersonalInformation {

	@FXML
	private AnchorPane personalInfo;

	@FXML
	private Button saveCustomer;

	@FXML
	private TextField passportNum;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private TextField email;

	@FXML
	private TextField primaryCitizenship;

	@FXML
	private TextField password;

	@FXML
	private DatePicker dateOfBirth;

	// moving pages
	public void movePage(String toMove) throws Exception {
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/" + toMove + ".fxml"));
		Stage primaryStage = (Stage) personalInfo.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

	public void	backCustomerHomeScreen(Event event) throws Exception {
		movePage("HomeScreenCustomer");
	}
	
	public void saveDetailsChanges(Event event) throws Exception {
		Date date = Date.valueOf(dateOfBirth.getValue());
		Customer updateCustomer = new Customer(passportNum.getText(), firstName.getText(), lastName.getText(), email.getText(), date, primaryCitizenship.getText(), password.getText());
		LoginControl.getInstance().getAllCustumers().put(passportNum.getText(), updateCustomer);
		LoginControl.getInstance().updateCustomer(updateCustomer);
		Alert alert = new Alert(AlertType.INFORMATION,"Your details are updates");
		alert.setHeaderText("You are Succseed to update");
		alert.setTitle("Update Sucssed");
		alert.showAndWait();
	}

	public void initialize() {
		Customer c = LoginControl.getInstance().getAllCustumers().get(NewAccount.user);
		passportNum.setText(c.getPassportID().toString());
		passportNum.setDisable(true);
		firstName.setText(c.getFirstName());
		lastName.setText(c.getLastName());
		dateOfBirth.setValue(c.getBirthDate().toLocalDate());
		email.setText(c.getEmail());
		primaryCitizenship.setText(c.getCitizenship());
		password.setText(c.getPassword());
	}
}
