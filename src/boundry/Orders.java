package boundry;

import control.FlightsControl;
import control.ProductSupplierControl;
import entity.Order;
import entity.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Orders {

	@FXML
    private AnchorPane ordersScreen;
	
	@FXML
    private ListView<Order> ordersList;
	
    
    @FXML
    private Button showFlightTickets;
    
    public static Order chooseOrder = null;
    
    @FXML 
   	public void orderChoose(MouseEvent arg0) throws Exception{
    	chooseOrder = ordersList.getSelectionModel().getSelectedItem();
   	}
    
    @FXML
	public void initialize() 
	{
    	ObservableList<Order> orders = FXCollections.observableArrayList(FlightsControl.getInstance().getOrders());
    	ordersList.setItems(FXCollections.observableArrayList(orders));
	}
    
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
    
    @FXML
   	public void moveTicketsOfOrderChoose(ActionEvent event) throws Exception
   	{
   		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/FlightTicketsOfOrder.fxml"));
   		Scene scene = new Scene(newRoot);
   		Stage stage = new Stage();
   		stage.setTitle("Flight Tickets Of Order");
   		stage.setScene(scene);
   		stage.show();
   	}
}
