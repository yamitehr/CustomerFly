package boundry;

import java.sql.Date;

import javafx.scene.control.ProgressBar;
import control.LoginControl;
import entity.Customer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewAccount {
	
	public static NewAccount instance;
	public static NewAccount getInstance() 
	{
		if (instance == null)
			instance = new NewAccount();
		return instance;
	}

	@FXML
    private AnchorPane newAccount;
	
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
	
	@FXML
	public Label alertFill;
	
	@FXML
	public Button register;
	
	public static String user;
	
	@FXML
	public ProgressBar progressBar;
	
	
	public void saveNewAccount(Event event) throws Exception {
		//Checks that all fields are full
		if (passportNum.getText().equals("") || firstName.getText().equals("") || lastName.getText().equals("")
				|| dateOfBirth.getValue() == null || email.getText().equals("") || primaryCitizenship.getText().equals("") || password.getText().equals("")) {
			alertFill.setText("You must fill in all the fields");
			return;
		}
		/*
		if(LoginControl.getInstance().checkPassportNum(LoginControl.getInstance().getAllCustumers().get(passportNum.getText())))
		{
			Alert alert = new Alert(AlertType.ERROR,"Please try another passport number");
			alert.setHeaderText("This user is already exist");
			alert.setTitle("Register Failed");
			alert.showAndWait();
			movePage("LoginScreen");
			return;
		}*/
		Date date = Date.valueOf(dateOfBirth.getValue());
		Customer c = new Customer(passportNum.getText(), firstName.getText(), lastName.getText(), email.getText(),date ,primaryCitizenship.getText(), password.getText());
		user = passportNum.getText();
		LoginControl.getInstance().getAllCustumers().put(user, c);
		//adding the customer to DB//
		if(LoginControl.getInstance().addNewCutsomerToDB(c)) {
			Alert alert = new Alert(AlertType.INFORMATION,"Your user name will be your passport ID");
			alert.setHeaderText("You are Succseed to Register");
			alert.setTitle("Register Sucssed");
			alert.showAndWait();
			movePage("LoginScreen"); //moving back to login
		} else {
			Alert alert = new Alert(AlertType.ERROR,"Please check if this account is not already registered");
			alert.setHeaderText("You Failed to Register");
			alert.setTitle("Register Failed");
			alert.showAndWait();
		}
	}
	
	//moving pages
		public void movePage(String toMove) throws Exception
		{
			Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/"+toMove+".fxml"));
			Stage primaryStage = (Stage) newAccount.getScene().getWindow();
			primaryStage.getScene().setRoot(newRoot);
			primaryStage.show();
		}
		
		public void backButton(MouseEvent event) throws Exception
		{
			movePage("LoginScreen"); //moving back to login
		}
		
		
		public void initialize() {
			//Check the password strength and alerts when you type incorrectly
			password.textProperty().addListener((observable, oldValue, newValue) -> {
				double level = level(newValue);
				progressBar.setProgress(level);

			});
		}
		
		private double level(String p) {
			double res = 0.1;

			if (p.length() < 5) {
				progressBar.setStyle("-fx-accent: orangered  ");

				return res;
			}

			for (int i = 0; i < p.length(); i++) {
				if (p.charAt(i) >= 48 && p.charAt(i) <= 57)// digit
				{
					res += 0.2;
					progressBar.setStyle("-fx-accent: khaki ");
					break;
				}
			}

			for (int i = 0; i < p.length(); i++) {

				if (p.charAt(i) >= 65 && p.charAt(i) <= 90)// upper-case
				{
					progressBar.setStyle("-fx-accent: orange");
					res += 0.2;
					break;
				}
			}

			for (int i = 0; i < p.length(); i++) {

				if (p.charAt(i) >= 97 && p.charAt(i) <= 122)// lower-case
				{
					progressBar.setStyle("-fx-accent: yellow");
					res += 0.2;
					break;
				}
			}

			for (int i = 0; i < p.length(); i++) {

				if (p.charAt(i) >= 33 && p.charAt(i) <= 47 || p.charAt(i) >= 57 && p.charAt(i) <= 64
						|| p.charAt(i) >= 91 && p.charAt(i) <= 95 || p.charAt(i) >= 123 && p.charAt(i) <= 126){// spacial char
					progressBar.setStyle("-fx-accent: green");
					res += 0.3;
					break;
				}
			}
			return res;

		}
		
		
}
