package boundry;

import entity.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Orders {

	@FXML
    private AnchorPane ordersScreen;
	
	@FXML
    private ListView<Order> ordersList;
	
	@FXML
    private Button newOrder;
	
	@FXML
    private Button removeOrder;
    
    @FXML
    private Button editOrder;
    
    @FXML
    private Button showFlightTickets;
    
    public void moveHomeScreen(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/HomeScreenAdmin.fxml"));
		Stage primaryStage = (Stage) ordersScreen.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
    
    public void addNewOrder(ActionEvent event) throws Exception{
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/AddOrder.fxml"));
   		Stage primaryStage = (Stage) ordersScreen.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
    }
}
