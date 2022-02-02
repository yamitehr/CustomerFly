package boundry;

import entity.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    
  
}
