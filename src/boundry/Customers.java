package boundry;

import entity.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    
    public void moveHomeScreen(ActionEvent event) throws Exception
   	{
   		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/HomeScreenAdmin.fxml"));
   		Stage primaryStage = (Stage) customersScreen.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
   	}
    
    public void addNewCustomer(ActionEvent event) throws Exception{
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/AddCustomer.fxml"));
   		Stage primaryStage = (Stage) customersScreen.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
    }

}
