package boundry;

import entity.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class Customers {

	@FXML
    private AnchorPane customersScreen;
	
	@FXML
    private ListView<Customer> customersList;
	
	@FXML
    private Button newCustomer;
	
	@FXML
    private Button removeCustomer;
    
    @FXML
    private Button editCustomer;

}
