package boundry;

import entity.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddCustomer {

	@FXML
    private AnchorPane addCustomer;
	
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
    private DatePicker dateOfBirth;
	
	
	 public void moveToList(ActionEvent event) throws Exception{
	    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/Customers.fxml"));
	   		Stage primaryStage = (Stage) addCustomer.getScene().getWindow();
	   		primaryStage.getScene().setRoot(newRoot);
	   		primaryStage.show();
	 }
    
  
}
