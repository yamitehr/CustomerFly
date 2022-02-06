package boundry;

import entity.Customer;
import entity.EntertainProduct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EntertainProducts {

	@FXML
    private AnchorPane entertainProductsScreen;
	
	@FXML
    private ListView<EntertainProduct> productsList;
	
	@FXML
    private Button newProduct;
	
	@FXML
    private Button removeProduct;
    
    @FXML
    private Button editProduct;
    
    public void moveHomeScreen(ActionEvent event) throws Exception
   	{
   		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/HomeScreenAdmin.fxml"));
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
