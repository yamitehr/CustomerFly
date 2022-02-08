package boundry;

import control.ProductSupplierControl;
import entity.EntertainProduct;
import entity.Flight;
import entity.FlightTicket;
import entity.Order;
import entity.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Suppliers {

	@FXML
    private AnchorPane suppliersScreen;
	
	@FXML
    private ListView<Supplier> suppliersList;
	
	@FXML
    private Button newSupplier;
	
	@FXML
    private Button removeSupplier;
    
    @FXML
    private Button editSupplier;
    
    @FXML
    private Button showProducts;
    
    public static Supplier chooseSupplier = null;
    
    private ProductSupplierControl productSupplierIns = ProductSupplierControl.getInstance();
    
    @FXML
	public void initialize() 
	{
    	ObservableList<Supplier> sup = FXCollections.observableArrayList(productSupplierIns.getSuppliers());
    	suppliersList.setItems(FXCollections.observableArrayList(sup));
	}
    
    public void moveHomeScreen(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/HomeScreenAdmin.fxml"));
		Stage primaryStage = (Stage) suppliersScreen.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
    
    public void addNewSupplier(ActionEvent event) throws Exception{
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/AddSupplier.fxml"));
   		Stage primaryStage = (Stage) suppliersScreen.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
    }
    
    @FXML 
	public void supplierChoose(MouseEvent arg0) throws Exception{
    	chooseSupplier = suppliersList.getSelectionModel().getSelectedItem();
	}
    
    @FXML
	public void moveProductsOfSupplierChoose(ActionEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/ProductsBySupplier.fxml"));
		Scene scene = new Scene(newRoot);
		Stage stage = new Stage();
		stage.setTitle("Products Of Supplier");
		stage.setScene(scene);
		stage.show();
	}
    
    
}
