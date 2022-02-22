package boundry;

import java.util.Optional;

import control.ProductSupplierControl;
import entity.EntertainProduct;
import entity.Supplier;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.ProductType;

public class UpdateEntertaunProduct {

	@FXML
    private AnchorPane updateProduct;
	
	@FXML
    private TextField productID;

	@FXML
    private TextField name;
    
    @FXML
    private TextField description;
    
    @FXML
    private ComboBox<ProductType> type;
    
    public void moveToList(ActionEvent event) throws Exception{
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/EntertainProducts.fxml"));
   		Stage primaryStage = (Stage) updateProduct.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
    }
    
    
    public void saveDetailsChanges(Event event) throws Exception {
    	int id = Integer.valueOf(productID.getText());
		EntertainProduct updateProduct = new EntertainProduct(id, name.getText(), description.getText(), type.getValue().toString());
		ProductSupplierControl.getInstance().updateProduct(updateProduct);
		Alert alert = new Alert(AlertType.INFORMATION,"Your details are updates");
		alert.setHeaderText("You are Succseed to update");
		alert.setTitle("Update Sucssed");
		alert.showAndWait();
	}
    
    
    @FXML
   	public void initialize() 
   	{
    	productID.setText(String.valueOf(EntertainProducts.chooseProduct.getProductID()));
    	productID.setDisable(true);
    	name.setText(EntertainProducts.chooseProduct.getName());
    	description.setText(EntertainProducts.chooseProduct.getDescription());
    	type.setValue(EntertainProducts.chooseProduct.getType());
   	}
}
