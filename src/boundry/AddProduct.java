package boundry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.ProductType;

public class AddProduct {

	@FXML
    private AnchorPane addProduct;
	
	@FXML
    private TextField productID;

	@FXML
    private TextField productName;
    
    @FXML
    private TextField description;
    
    @FXML
    private ComboBox<ProductType> type;
    
    @FXML
    private Button saveSupplier;
    
    public void moveToList(ActionEvent event) throws Exception{
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/EntertainProducts.fxml"));
   		Stage primaryStage = (Stage) addProduct.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
    }
}
