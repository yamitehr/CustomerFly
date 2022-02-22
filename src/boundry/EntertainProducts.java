package boundry;

import control.ProductSupplierControl;
import entity.Customer;
import entity.EntertainProduct;
import entity.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.ProductType;

public class EntertainProducts {

	@FXML
    private AnchorPane entertainProductsScreen;
	
	@FXML
    private ListView<EntertainProduct> productsList;
	
	@FXML
    private Button newProduct;
	
	@FXML
    private Button removeProduct;
	
	public static EntertainProduct chooseProduct = null;
    
    @FXML
    private Button editProduct;
    private ProductSupplierControl productSupplierIns = ProductSupplierControl.getInstance();
    
    @FXML
	public void productChoose(MouseEvent arg0) throws Exception {
    	chooseProduct = productsList.getSelectionModel().getSelectedItem();
	}
    
    @FXML
 	public void initialize() 
 	{
    	ObservableList<EntertainProduct> products = FXCollections.observableArrayList(productSupplierIns.getProducts());
    	productsList.setItems(FXCollections.observableArrayList(products));
 	}
    
    public void moveHomeScreen(ActionEvent event) throws Exception
   	{
   		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/HomeScreenAdmin.fxml"));
   		Stage primaryStage = (Stage) entertainProductsScreen.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
   	}
    
    public void editProductChoose(ActionEvent event) throws Exception {
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/UpdateEntertainProduct.fxml"));
		Stage primaryStage = (Stage) entertainProductsScreen.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
    
    public void addNewProduct(ActionEvent event) throws Exception{
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/AddProduct.fxml"));
   		Stage primaryStage = (Stage) entertainProductsScreen.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
    }
}
