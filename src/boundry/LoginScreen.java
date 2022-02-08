package boundry;

import java.util.ArrayList;
import java.util.HashMap;

import control.LoginControl;
import entity.Customer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginScreen {

	@FXML
	private Label loginFailed;
	
	@FXML
	private TextField txtUserName;
	
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	private Button login, newAccount;
	
	@FXML
	private AnchorPane loginScene;
	
	//for moving window
		public void movePage(String toMove) throws Exception
		{
			Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/"+toMove+".fxml"));
			Stage primaryStage = (Stage) loginScene.getScene().getWindow();
			primaryStage.getScene().setRoot(newRoot);
			primaryStage.show();
		}
		
	public void Login(Event event) throws Exception {
		try {
			if ((txtUserName.getText().equals("admin") && txtPassword.getText().equals("admin"))){ //check if the details is true
				movePage("HomeScreenAdmin");
				return;
			}
			
			if(isCanLogin())
			{
				movePage("HomeScreenCustomer");
				return;
				
			}
			else if(!isCanLogin())
			{
				Alert alert = new Alert(AlertType.ERROR,"One or More Fields are incoorect");
				alert.setHeaderText("Login Error");
				alert.setTitle("Login Error");
				alert.showAndWait();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//this method return true if can login, else false
		public boolean isCanLogin()
		{
			NewAccount.user = txtUserName.getText();
			Customer c= new Customer(txtUserName.getText(), txtPassword.getText());
			HashMap<String, Customer> allCustumers = LoginControl.getInstance().getAllCustumers();
			for(Customer cus : allCustumers.values())
			{
				if(cus.equals(c)) //if userName and password exist
				{
					LoginControl.getInstance().setLoginCustumer(cus); //save the login member
					return true;
				}
			}

			return false;
		}
	
	public void newAccount (Event event) throws Exception{
		movePage("NewAccount");
	}
}
